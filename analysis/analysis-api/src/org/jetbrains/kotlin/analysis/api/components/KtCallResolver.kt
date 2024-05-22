/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.components

import org.jetbrains.kotlin.analysis.api.KaAnalysisApiInternals
import org.jetbrains.kotlin.analysis.api.calls.KaCallCandidateInfo
import org.jetbrains.kotlin.analysis.api.calls.KaCallInfo
import org.jetbrains.kotlin.analysis.api.lifetime.withValidityAssertion
import org.jetbrains.kotlin.psi.KtElement

@KaAnalysisApiInternals
public abstract class KaResolver : KaSessionComponent() {
    public abstract fun resolveCall(psi: KtElement): KaCallInfo?
    public abstract fun collectCallCandidates(psi: KtElement): List<KaCallCandidateInfo>
}

@OptIn(KaAnalysisApiInternals::class)
public typealias KtCallResolver = KaResolver

@OptIn(KaAnalysisApiInternals::class)
public interface KaResolverMixIn : KaSessionMixIn {

    public fun KtElement.resolveCall(): KaCallInfo? = withValidityAssertion {
        analysisSession.resolver.resolveCall(this)
    }

    /**
     * Returns all the candidates considered during [overload resolution](https://kotlinlang.org/spec/overload-resolution.html) for the call
     * corresponding to this [KtElement].
     *
     * [resolveCall] only returns the final result of overload resolution, i.e., the selected callable after considering candidate
     * applicability and choosing the most specific candidate.
     */
    public fun KtElement.collectCallCandidates(): List<KaCallCandidateInfo> = withValidityAssertion {
        analysisSession.resolver.collectCallCandidates(this)
    }
}

public typealias KtCallResolverMixIn = KaResolverMixIn