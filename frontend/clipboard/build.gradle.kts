plugins { kotlin("multiplatform") }

version = Versions.app

kotlin {
    jvm()
    if (Platforms.jsEnabled) {
        js(IR) {
            browser()
        }
    }

    compilerOptions { freeCompilerArgs.add("-Xexpect-actual-classes") }
}
