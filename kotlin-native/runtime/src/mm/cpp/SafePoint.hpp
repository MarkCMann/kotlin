/*
 * Copyright 2010-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

#pragma once

#include <utility>

#include "Utils.hpp"

namespace kotlin::mm {

class ThreadData;

class SafePointActivator : private Pinned {
public:
    SafePointActivator() noexcept;
    ~SafePointActivator();
};

void safePoint() noexcept;
void safePoint(ThreadData& threadData) noexcept;

} // namespace kotlin::mm
