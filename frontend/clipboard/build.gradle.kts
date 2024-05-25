plugins { kotlin("multiplatform") }

version = Versions.app

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    compilerOptions { freeCompilerArgs.add("-Xexpect-actual-classes") }
}
