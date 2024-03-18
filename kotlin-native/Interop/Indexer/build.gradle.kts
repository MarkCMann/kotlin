/*
 * Copyright 2010-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

import org.jetbrains.kotlin.tools.lib
import org.jetbrains.kotlin.tools.solib
import org.jetbrains.kotlin.tools.ToolExecutionTask
import org.jetbrains.kotlin.*
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import org.jetbrains.kotlin.konan.target.*

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("kotlin.native.build-tools-conventions")
    id("native-interop-plugin")
    id("native")
    id("native-dependencies")
}

val libclangextProject = project(":kotlin-native:libclangext")
val libclangextTask = libclangextProject.path + ":build"
val libclangextDir = libclangextProject.layout.buildDirectory.get().asFile
val libclangextIsEnabled = libclangextProject.findProperty("isEnabled")!! as Boolean


val libclang =
    if (HostManager.hostIsMingw) {
        "lib/libclang.lib"
    } else {
        "lib/${System.mapLibraryName("clang")}"
    }

val cflags = mutableListOf( "-I${nativeDependencies.llvmPath}/include",
        "-I${project(":kotlin-native:libclangext").projectDir.absolutePath}/src/main/include",
        "-fvisibility=hidden", "-O3", "-flto=thin",
                            *platformManager.hostPlatform.clangForJni.hostCompilerArgsForJni)

// val ldflags = mutableListOf("${nativeDependencies.llvmPath}/lib/libclang.dylib", "-L${libclangextDir.absolutePath}", "-lclangext")
val ldflags = mutableListOf("-L${libclangextDir.absolutePath}", "-lclangext")

if (libclangextIsEnabled) {
    assert(HostManager.hostIsMac)
    // Let some symbols be undefined to avoid linking unnecessary parts.
    val unnecessarySymbols = setOf(
            "__ZN4llvm7remarks11parseFormatENS_9StringRefE",
            "__ZN4llvm7remarks22createRemarkSerializerENS0_6FormatENS0_14SerializerModeERNS_11raw_ostreamE",
            "__ZN4llvm7remarks14YAMLSerializerC1ERNS_11raw_ostreamENS0_14UseStringTableE",
            "__ZN4llvm3omp22getOpenMPDirectiveNameENS0_9DirectiveE",
            "__ZN4llvm7remarks14RemarkStreamer13matchesFilterENS_9StringRefE",
            "__ZN4llvm7remarks14RemarkStreamer9setFilterENS_9StringRefE",
            "__ZN4llvm7remarks14RemarkStreamerC1ENSt3__110unique_ptrINS0_16RemarkSerializerENS2_14default_deleteIS4_EEEENS_8OptionalINS_9StringRefEEE",
            "__ZN4llvm3omp19getOpenMPClauseNameENS0_6ClauseE",
            "__ZN4llvm3omp28getOpenMPContextTraitSetNameENS0_8TraitSetE",
            "__ZN4llvm3omp31isValidTraitSelectorForTraitSetENS0_13TraitSelectorENS0_8TraitSetERbS3_",
            "__ZN4llvm3omp31isValidTraitSelectorForTraitSetENS0_13TraitSelectorENS0_8TraitSetERbS3_",
            "__ZN4llvm3omp33getOpenMPContextTraitPropertyNameENS0_13TraitPropertyE",
            "__ZN4llvm3omp33getOpenMPContextTraitSelectorNameENS0_13TraitSelectorE",
            "__ZN4llvm3omp35getOpenMPContextTraitSetForPropertyENS0_13TraitPropertyE",
            "__ZN4llvm3omp33getOpenMPContextTraitPropertyKindENS0_8TraitSetENS_9StringRefE"
    )
    ldflags.addAll(
            listOf("-Wl,--no-demangle", "-Wl,-search_paths_first", "-Wl,-headerpad_max_install_names", "-Wl,-U,_futimens") +
                    unnecessarySymbols.map { "-Wl,-U,$it" }
    )

    val llvmLibs = listOf(
            "clangAST", "clangASTMatchers", "clangAnalysis", "clangBasic", "clangDriver", "clangEdit",
            "clangFrontend", "clangFrontendTool", "clangLex", "clangParse", "clangSema",
            "clangRewrite", "clangRewriteFrontend", "clangStaticAnalyzerFrontend",
            "clangStaticAnalyzerCheckers", "clangStaticAnalyzerCore", "clangSerialization",
            "clangToolingCore",
            "clangTooling", "clangFormat", "LLVMTarget", "LLVMMC", "LLVMLinker", "LLVMTransformUtils",
            "LLVMBitWriter", "LLVMBitReader", "LLVMAnalysis", "LLVMProfileData", "LLVMCore",
            "LLVMSupport", "LLVMBinaryFormat", "LLVMDemangle",
            


            "LLVMAArch64AsmParser",
"LLVMAArch64CodeGen",
"LLVMAArch64Desc",
"LLVMAArch64Disassembler",
"LLVMAArch64Info",
"LLVMAArch64Utils",
"LLVMAMDGPUAsmParser",
"LLVMAMDGPUCodeGen",
"LLVMAMDGPUDesc",
"LLVMAMDGPUDisassembler",
"LLVMAMDGPUInfo",
"LLVMAMDGPUTargetMCA",
"LLVMAMDGPUUtils",
"LLVMARMAsmParser",
"LLVMARMCodeGen",
"LLVMARMDesc",
"LLVMARMDisassembler",
"LLVMARMInfo",
"LLVMARMUtils",
"LLVMAVRAsmParser",
"LLVMAVRCodeGen",
"LLVMAVRDesc",
"LLVMAVRDisassembler",
"LLVMAVRInfo",
"LLVMAggressiveInstCombine",
"LLVMAnalysis",
"LLVMAsmParser",
"LLVMAsmPrinter",
"LLVMBPFAsmParser",
"LLVMBPFCodeGen",
"LLVMBPFDesc",
"LLVMBPFDisassembler",
"LLVMBPFInfo",
"LLVMBinaryFormat",
"LLVMBitReader",
"LLVMBitWriter",
"LLVMBitstreamReader",
"LLVMCAS",
"LLVMCFGuard",
"LLVMCFIVerify",
"LLVMCodeGen",
"LLVMCore",
"LLVMCoroutines",
"LLVMCoverage",
"LLVMDWARFLinker",
"LLVMDWP",
"LLVMDebugInfoCodeView",
"LLVMDebugInfoDWARF",
"LLVMDebugInfoGSYM",
"LLVMDebugInfoMSF",
"LLVMDebugInfoPDB",
"LLVMDebuginfod",
"LLVMDemangle",
"LLVMDiff",
"LLVMDlltoolDriver",
"LLVMExecutionEngine",
"LLVMExegesis",
"LLVMExegesisAArch64",
"LLVMExegesisMips",
"LLVMExegesisPowerPC",
"LLVMExegesisX86",
"LLVMExtensions",
"LLVMFileCheck",
"LLVMFrontendOpenACC",
"LLVMFrontendOpenMP",
"LLVMFuzzMutate",
"LLVMGlobalISel",
"LLVMHexagonAsmParser",
"LLVMHexagonCodeGen",
"LLVMHexagonDesc",
"LLVMHexagonDisassembler",
"LLVMHexagonInfo",
"LLVMIRReader",
"LLVMInstCombine",
"LLVMInstrumentation",
"LLVMInterfaceStub",
"LLVMInterpreter",
"LLVMJITLink",
"LLVMLTO",
"LLVMLanaiAsmParser",
"LLVMLanaiCodeGen",
"LLVMLanaiDesc",
"LLVMLanaiDisassembler",
"LLVMLanaiInfo",
"LLVMLibDriver",
"LLVMLineEditor",
"LLVMLinker",
"LLVMMC",
"LLVMMCA",
"LLVMMCDisassembler",
"LLVMMCJIT",
"LLVMMCParser",
"LLVMMIRParser",
"LLVMMSP430AsmParser",
"LLVMMSP430CodeGen",
"LLVMMSP430Desc",
"LLVMMSP430Disassembler",
"LLVMMSP430Info",
"LLVMMipsAsmParser",
"LLVMMipsCodeGen",
"LLVMMipsDesc",
"LLVMMipsDisassembler",
"LLVMMipsInfo",
"LLVMNVPTXCodeGen",
"LLVMNVPTXDesc",
"LLVMNVPTXInfo",
"LLVMObjCARCOpts",
"LLVMObjCopy",
"LLVMObject",
"LLVMObjectYAML",
"LLVMOption",
"LLVMOrcJIT",
"LLVMOrcShared",
"LLVMOrcTargetProcess",
"LLVMPasses",
"LLVMPowerPCAsmParser",
"LLVMPowerPCCodeGen",
"LLVMPowerPCDesc",
"LLVMPowerPCDisassembler",
"LLVMPowerPCInfo",
"LLVMProfileData",
"LLVMRISCVAsmParser",
"LLVMRISCVCodeGen",
"LLVMRISCVDesc",
"LLVMRISCVDisassembler",
"LLVMRISCVInfo",
"LLVMRemarks",
"LLVMRemoteCachingService",
"LLVMRemoteNullService",
"LLVMRuntimeDyld",
"LLVMScalarOpts",
"LLVMSelectionDAG",
"LLVMSparcAsmParser",
"LLVMSparcCodeGen",
"LLVMSparcDesc",
"LLVMSparcDisassembler",
"LLVMSparcInfo",
"LLVMSupport",
"LLVMSymbolize",
"LLVMSystemZAsmParser",
"LLVMSystemZCodeGen",
"LLVMSystemZDesc",
"LLVMSystemZDisassembler",
"LLVMSystemZInfo",
"LLVMTableGen",
"LLVMTableGenGlobalISel",
"LLVMTarget",
"LLVMTextAPI",
"LLVMTransformUtils",
"LLVMVEAsmParser",
"LLVMVECodeGen",
"LLVMVEDesc",
"LLVMVEDisassembler",
"LLVMVEInfo",
"LLVMVectorize",
"LLVMWebAssemblyAsmParser",
"LLVMWebAssemblyCodeGen",
"LLVMWebAssemblyDesc",
"LLVMWebAssemblyDisassembler",
"LLVMWebAssemblyInfo",
"LLVMWebAssemblyUtils",
"LLVMWindowsDriver",
"LLVMWindowsManifest",
"LLVMX86AsmParser",
"LLVMX86CodeGen",
"LLVMX86Desc",
"LLVMX86Disassembler",
"LLVMX86Info",
"LLVMX86TargetMCA",
"LLVMXCoreCodeGen",
"LLVMXCoreDesc",
"LLVMXCoreDisassembler",
"LLVMXCoreInfo",
"LLVMXRay",
"LLVMipo",
"c++",
"c++abi",
"c++experimental",
"clang",
"clangAPINotes",
"clangARCMigrate",
"clangAST",
"clangASTMatchers",
"clangAnalysis",
"clangAnalysisFlowSensitive",
"clangAnalysisFlowSensitiveModels",
"clangBasic",
"clangCAS",
"clangCodeGen",
"clangCrossTU",
"clangDependencyScanning",
"clangDirectoryWatcher",
"clangDriver",
"clangDynamicASTMatchers",
"clangEdit",
"clangExtractAPI",
"clangFormat",
"clangFrontend",
"clangFrontendTool",
"clangHandleCXX",
"clangHandleLLVM",
"clangIndex",
"clangIndexDataStore",
"clangIndexSerialization",
"clangInterpreter",
"clangLex",
"clangParse",
"clangRewrite",
"clangRewriteFrontend",
"clangSema",
"clangSerialization",
"clangStaticAnalyzerCheckers",
"clangStaticAnalyzerCore",
"clangStaticAnalyzerFrontend",
"clangSupport",
"clangTooling",
"clangToolingASTDiff",
"clangToolingCore",
"clangToolingInclusions",
"clangToolingRefactor",
"clangToolingRefactoring",
"clangToolingSyntax",
"clangTransformer",
"lldCOFF",
"lldCommon",
"lldELF",
"lldMachO",
"lldMinGW",
"lldWasm",
    ).map { "${nativeDependencies.llvmPath}/lib/lib${it}.a" }

    ldflags.addAll(llvmLibs)
    ldflags.addAll(listOf("-lpthread", "-lz", "-lm", "-lcurses"))
    ldflags.add("-Wl,-exported_symbols_list,clang.list")
    //ldflags.add("-Wl,-dead_strip")
    //ldflags.add("-flto=thin")
    ldflags.addAll(listOf("-target", "arm64-apple-macos14.0"))
}

val solib = when{
    HostManager.hostIsMingw -> "dll"
    HostManager.hostIsMac -> "dylib"
    else -> "so"
}
val lib = if (HostManager.hostIsMingw) "lib" else "a"


native {
    val obj = if (HostManager.hostIsMingw) "obj" else "o"
    val cxxflags = listOf("-std=c++11", *cflags.toTypedArray())
    suffixes {
        (".c" to ".$obj") {
            tool(*hostPlatform.clangForJni.clangC("").toTypedArray())
            flags(*cflags.toTypedArray(),
                  "-c", "-o", ruleOut(), ruleInFirst())
        }
        (".cpp" to ".$obj") {
            tool(*hostPlatform.clangForJni.clangCXX("").toTypedArray())
            flags(*cxxflags.toTypedArray(), "-c", "-o", ruleOut(), ruleInFirst())
        }
    }
    sourceSet {
        "main-c" {
            dir("prebuilt/nativeInteropStubs/c")
        }
        "main-cpp" {
            file("src/nativeInteropStubs/cpp/signalChaining.cpp")
            file("src/nativeInteropStubs/cpp/disable-abi-checks.cpp")
            //dir("src/nativeInteropStubs/cpp")
        }
        "sigaction-proxy" {
            file("src/nativeInteropStubs/cpp/sigactionProxy.cpp")
        }
    }
    val objSet = arrayOf(sourceSets["main-c"]!!.transform(".c" to ".$obj"),
                         sourceSets["main-cpp"]!!.transform(".cpp" to ".$obj"))

    target("preclangstubs.o", *objSet) {
        tool(*hostPlatform.clangForJni.clangCXX("").toTypedArray())
        flags(
            // "-shared",
            "-Wl,-r",
            "-Wl,-U,_mySigaction",
            "-o", ruleOut(), *ruleInAll(),
            *ldflags.toTypedArray())
    }

    val proxyObjs = arrayOf(sourceSets["sigaction-proxy"]!!.transform(".cpp" to ".$obj"))
    target(solib("clangstubs"), *proxyObjs) {
        tool(*hostPlatform.clangForJni.clangCXX("").toTypedArray())
        // val linkerFlags = listOf(
        //     "-lpthread", "-lz", "-lm", "-lcurses",
        //     "-Wl,-exported_symbols_list,clang.list",
        //     "-Wl,--no-demangle",
        //     "-Wl,-search_paths_first",
        //     "-Wl,-headerpad_max_install_names", 
        //     "-Wl,-U,_futimens", 
        //     //"-flto=thin", 
        //     "-target", "arm64-apple-macos14.0",
        //     "-Wl,-dead_strip")
        flags(
            "-shared",
            "-o", ruleOut(), *ruleInAll(),
            (tasks.named("preclangstubs.o").get() as ToolExecutionTask).output.path,
            *ldflags.toTypedArray())
    }
}

tasks.named(solib("clangstubs")).configure {
    dependsOn(":kotlin-native:libclangext:${lib("clangext")}")
    dependsOn("preclangstubs.o")
}

sourceSets {
    "main" {
        java {
            srcDirs("prebuilt/nativeInteropStubs/kotlin")
        }
        kotlin{
        }
    }
}

dependencies {
    api(project(":kotlin-stdlib"))
    api(project(":kotlin-native:Interop:Runtime"))

    testImplementation(kotlin("test-junit"))
    testImplementation(project(":compiler:util"))
}

val nativelibs = project.tasks.register<Copy>("nativelibs") {
    val clangstubsSolib = solib("clangstubs")
    dependsOn(clangstubsSolib)

    from(layout.buildDirectory.dir(clangstubsSolib))
    into(layout.buildDirectory.dir("nativelibs"))
}

kotlinNativeInterop {
    this.create("clang") {
        defFile("clang.def")
        compilerOpts(cflags)
        linkerOpts = ldflags
        genTask.dependsOn(libclangextTask)
        genTask.inputs.dir(libclangextDir)
    }
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        optIn.addAll(
                listOf(
                        "kotlinx.cinterop.BetaInteropApi",
                        "kotlinx.cinterop.ExperimentalForeignApi",
                )
        )
        freeCompilerArgs.addAll(
                listOf(
                        "-Xskip-prerelease-check",
                        // staticCFunction uses kotlin.reflect.jvm.reflect on its lambda parameter.
                        "-Xlambdas=class",
                )
        )
    }
}

tasks.withType<Test>().configureEach {
    val projectsWithNativeLibs = listOf(
            project, // Current one.
            project(":kotlin-native:Interop:Runtime")
    )
    dependsOn(projectsWithNativeLibs.map { "${it.path}:nativelibs" })
    dependsOn(nativeDependencies.llvmDependency)
    systemProperty("java.library.path", projectsWithNativeLibs.joinToString(File.pathSeparator) {
        it.layout.buildDirectory.dir("nativelibs").get().asFile.absolutePath
    })

    systemProperty("kotlin.native.llvm.libclang", "${nativeDependencies.llvmPath}/" + if (HostManager.hostIsMingw) {
        "bin/libclang.dll"
    } else {
        "lib/${System.mapLibraryName("clang")}"
    })

    systemProperty("kotlin.native.interop.indexer.temp", layout.buildDirectory.dir("testTemp").get().asFile)
}

// Please note that list of headers should be fixed manually.
// See KT-46231 for details.
tasks.register("updatePrebuilt") {
    dependsOn("genClangInteropStubs")

    doLast {
        copy {
            from(layout.buildDirectory.dir("nativeInteropStubs/clang/kotlin")) {
                include("clang/clang.kt")
            }
            into("prebuilt/nativeInteropStubs/kotlin")
        }

        copy {
            from(layout.buildDirectory.dir("interopTemp")) {
                include("clangstubs.c")
            }
            into("prebuilt/nativeInteropStubs/c")
        }
    }
}
