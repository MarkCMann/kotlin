/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.standalone.fir.test.cases.generated.cases.components.diagnosticProvider;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.analysis.api.standalone.fir.test.configurators.AnalysisApiFirStandaloneModeTestConfiguratorFactory;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestConfiguratorFactoryData;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestConfigurator;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.TestModuleKind;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.FrontendKind;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisSessionMode;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiMode;
import org.jetbrains.kotlin.analysis.api.impl.base.test.cases.components.diagnosticProvider.AbstractCollectDiagnosticsTest;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics")
@TestDataPath("$PROJECT_ROOT")
public class FirStandaloneNormalAnalysisSourceModuleCollectDiagnosticsTestGenerated extends AbstractCollectDiagnosticsTest {
    @NotNull
    @Override
    public AnalysisApiTestConfigurator getConfigurator() {
        return AnalysisApiFirStandaloneModeTestConfiguratorFactory.INSTANCE.createConfigurator(
            new AnalysisApiTestConfiguratorFactoryData(
                FrontendKind.Fir,
                TestModuleKind.Source,
                AnalysisSessionMode.Normal,
                AnalysisApiMode.Standalone
            )
        );
    }

    @Test
    public void testAllFilesPresentInDiagnostics() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics"), Pattern.compile("^(.+)\\.kt$"), null, true);
    }

    @Test
    @TestMetadata("annotationWithEnumFromDuplicatedLibrary.kt")
    public void testAnnotationWithEnumFromDuplicatedLibrary() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/annotationWithEnumFromDuplicatedLibrary.kt");
    }

    @Test
    @TestMetadata("declarationErrors.kt")
    public void testDeclarationErrors() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/declarationErrors.kt");
    }

    @Test
    @TestMetadata("delegationToLibraryInterface.kt")
    public void testDelegationToLibraryInterface() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/delegationToLibraryInterface.kt");
    }

    @Test
    @TestMetadata("duplicatedCallableWithImplicitType.kt")
    public void testDuplicatedCallableWithImplicitType() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/duplicatedCallableWithImplicitType.kt");
    }

    @Test
    @TestMetadata("errorsInFunctionalInterfacesInstances.kt")
    public void testErrorsInFunctionalInterfacesInstances() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/errorsInFunctionalInterfacesInstances.kt");
    }

    @Test
    @TestMetadata("incompleteDelegation.kt")
    public void testIncompleteDelegation() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/incompleteDelegation.kt");
    }

    @Test
    @TestMetadata("incompleteFor.kt")
    public void testIncompleteFor() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/incompleteFor.kt");
    }

    @Test
    @TestMetadata("overrideProtectedClassReturnFromLibrary.kt")
    public void testOverrideProtectedClassReturnFromLibrary() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/overrideProtectedClassReturnFromLibrary.kt");
    }

    @Test
    @TestMetadata("resolutionErrors.kt")
    public void testResolutionErrors() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/resolutionErrors.kt");
    }

    @Test
    @TestMetadata("typeMismatches.kt")
    public void testTypeMismatches() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/typeMismatches.kt");
    }

    @Test
    @TestMetadata("unresolved.kt")
    public void testUnresolved() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/unresolved.kt");
    }

    @Test
    @TestMetadata("unresolvedAnnotationsOnPropertyFromParameter.kt")
    public void testUnresolvedAnnotationsOnPropertyFromParameter() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/unresolvedAnnotationsOnPropertyFromParameter.kt");
    }

    @Test
    @TestMetadata("unresolvedReferenceInsideSuperConstructorCall.kt")
    public void testUnresolvedReferenceInsideSuperConstructorCall() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/unresolvedReferenceInsideSuperConstructorCall.kt");
    }

    @Test
    @TestMetadata("unresolvedReferenceInsideSuperConstructorCallWithLocalFunction.kt")
    public void testUnresolvedReferenceInsideSuperConstructorCallWithLocalFunction() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/unresolvedReferenceInsideSuperConstructorCallWithLocalFunction.kt");
    }

    @Test
    @TestMetadata("unresolvedReferenceInsideSuperConstructorCallWithPrimaryConstructor.kt")
    public void testUnresolvedReferenceInsideSuperConstructorCallWithPrimaryConstructor() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/unresolvedReferenceInsideSuperConstructorCallWithPrimaryConstructor.kt");
    }

    @Test
    @TestMetadata("unresolvedReferenceInsideSuperConstructorCallWithSecondaryConstructor.kt")
    public void testUnresolvedReferenceInsideSuperConstructorCallWithSecondaryConstructor() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/unresolvedReferenceInsideSuperConstructorCallWithSecondaryConstructor.kt");
    }

    @Test
    @TestMetadata("unresolvedReferenceInsideSuperPrimaryConstructorCallWithLocalFunction.kt")
    public void testUnresolvedReferenceInsideSuperPrimaryConstructorCallWithLocalFunction() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/unresolvedReferenceInsideSuperPrimaryConstructorCallWithLocalFunction.kt");
    }

    @Test
    @TestMetadata("unresolvedSuperConstructorCall.kt")
    public void testUnresolvedSuperConstructorCall() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/unresolvedSuperConstructorCall.kt");
    }

    @Test
    @TestMetadata("unusedDestructuring.kt")
    public void testUnusedDestructuring() throws Exception {
        runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/unusedDestructuring.kt");
    }

    @Nested
    @TestMetadata("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/suppression")
    @TestDataPath("$PROJECT_ROOT")
    public class Suppression {
        @Test
        public void testAllFilesPresentInSuppression() throws Exception {
            KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/suppression"), Pattern.compile("^(.+)\\.kt$"), null, true);
        }

        @Test
        @TestMetadata("conflictingOverloadsAtTopLevel.kt")
        public void testConflictingOverloadsAtTopLevel() throws Exception {
            runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/suppression/conflictingOverloadsAtTopLevel.kt");
        }

        @Test
        @TestMetadata("conflictingOverloadsAtTopLevel2.kt")
        public void testConflictingOverloadsAtTopLevel2() throws Exception {
            runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/suppression/conflictingOverloadsAtTopLevel2.kt");
        }

        @Test
        @TestMetadata("conflictingOverloadsAtTopLevelWithFileSuppression.kt")
        public void testConflictingOverloadsAtTopLevelWithFileSuppression() throws Exception {
            runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/suppression/conflictingOverloadsAtTopLevelWithFileSuppression.kt");
        }

        @Test
        @TestMetadata("conflictingOverloadsInClass.kt")
        public void testConflictingOverloadsInClass() throws Exception {
            runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/suppression/conflictingOverloadsInClass.kt");
        }

        @Test
        @TestMetadata("conflictingOverloadsInNestedClass.kt")
        public void testConflictingOverloadsInNestedClass() throws Exception {
            runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/suppression/conflictingOverloadsInNestedClass.kt");
        }

        @Test
        @TestMetadata("deprecationAtTopLevel.kt")
        public void testDeprecationAtTopLevel() throws Exception {
            runTest("analysis/analysis-api/testData/components/diagnosticsProvider/diagnostics/suppression/deprecationAtTopLevel.kt");
        }
    }
}
