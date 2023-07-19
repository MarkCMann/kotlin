/*
 * Copyright 2010-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

#include "GCSchedulerImpl.hpp"

#include "CallsChecker.hpp"
#include "GlobalData.hpp"
#include "Memory.h"
#include "Logging.hpp"
#include "Porting.h"

using namespace kotlin;

template <typename Clock, typename F>
using GCSchedulerDataAdaptive = kotlin::gcScheduler::internal::GCSchedulerDataAdaptive<Clock, F>;

template <typename F>
auto make_unique_GCSchedulerDataAdaptive(kotlin::gcScheduler::GCSchedulerConfig& config, F&& scheduleGC) noexcept {
    return std_support::make_unique<GCSchedulerDataAdaptive<steady_clock, F>>(
        config,
        std::forward<F>(scheduleGC)
    );
}

gcScheduler::GCScheduler::GCScheduler() noexcept :
    gcData_(make_unique_GCSchedulerDataAdaptive(config_, []() noexcept {
        // This call acquires a lock, but the lock are always short-lived,
        // so we ignore thread state switching to avoid recursive safe points.
        CallsCheckerIgnoreGuard guard;
        mm::GlobalData::Instance().gc().Schedule();
    })) {}

ALWAYS_INLINE void gcScheduler::GCScheduler::safePoint() noexcept {}
