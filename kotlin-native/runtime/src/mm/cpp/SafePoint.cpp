/*
 * Copyright 2010-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

#include "SafePoint.hpp"

#include <atomic>

#include "GCScheduler.hpp"
#include "KAssert.h"
#include "ThreadData.hpp"
#include "ThreadState.hpp"

using namespace kotlin;

namespace {

[[clang::no_destroy]] std::mutex safePointActionMutex;
int64_t activeCount = 0;
std::atomic<bool> safePointsActivated = false;

void safePointActionImpl(mm::ThreadData& threadData) noexcept {
    static thread_local bool recursion = false;
    RuntimeAssert(!recursion, "Recursive safepoint");
    AutoReset guard(&recursion, true);

    mm::GlobalData::Instance().gcScheduler().safePoint();
    threadData.gc().safePoint();
    threadData.suspensionData().suspendIfRequested();
}

ALWAYS_INLINE void slowPathImpl(mm::ThreadData& threadData) noexcept {
    // reread flag to avoid register pollution outside the function
    if (safePointsActivated.load(std::memory_order_seq_cst)) {
        safePointActionImpl(threadData);
    }
}

__attribute__((cold)) NO_INLINE void slowPath() noexcept {
    slowPathImpl(*mm::ThreadRegistry::Instance().CurrentThreadData());
}

__attribute__((cold)) NO_INLINE void slowPath(mm::ThreadData& threadData) noexcept {
    slowPathImpl(threadData);
}

void incrementActiveCount() noexcept {
    std::unique_lock guard{safePointActionMutex};
    ++activeCount;
    RuntimeAssert(activeCount >= 1, "Unexpected activeCount: %" PRId64, activeCount);
    if (activeCount == 1) {
        auto prev = safePointsActivated.exchange(true, std::memory_order_seq_cst);
        RuntimeAssert(!prev, "Safe points must not have been activated.");
    }
}

void decrementActiveCount() noexcept {
    std::unique_lock guard{safePointActionMutex};
    --activeCount;
    RuntimeAssert(activeCount >= 0, "Unexpected activeCount: %" PRId64, activeCount);
    if (activeCount == 0) {
        auto prev = safePointsActivated.exchange(false, std::memory_order_seq_cst);
        RuntimeAssert(prev, "Safe points must have been activated previously.");
    }
}

} // namespace

mm::SafePointActivator::SafePointActivator() noexcept {
    incrementActiveCount();
}

mm::SafePointActivator::~SafePointActivator() {
    decrementActiveCount();
}

ALWAYS_INLINE void mm::safePoint() noexcept {
    AssertThreadState(ThreadState::kRunnable);
    auto safePointsActivatedRelaxed = safePointsActivated.load(std::memory_order_relaxed);
    if (__builtin_expect(safePointsActivatedRelaxed, false)) {
        slowPath();
    }
}

ALWAYS_INLINE void mm::safePoint(mm::ThreadData& threadData) noexcept {
    AssertThreadState(&threadData, ThreadState::kRunnable);
    auto safePointsActivatedRelaxed = safePointsActivated.load(std::memory_order_relaxed);
    if (__builtin_expect(safePointsActivatedRelaxed, false)) {
        slowPath(threadData);
    }
}
