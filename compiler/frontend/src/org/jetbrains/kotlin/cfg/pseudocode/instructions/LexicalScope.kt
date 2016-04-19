/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.cfg.pseudocode.instructions

import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtElement

class LexicalScope(private val parentScope: LexicalScope?, val block: KtElement) {
    val depth: Int = (parentScope?.depth ?: 0) + 1

    val lexicalScopeForContainingDeclaration: LexicalScope? by lazy {
        var scope: LexicalScope? = this
        while (scope != null) {
            if (scope.block is KtDeclaration) {
                break
            }
            scope = scope.parentScope
        }
        scope
    }
}
