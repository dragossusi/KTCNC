plugins { kotlin("multiplatform") }

version = Versions.app

kotlin {
    jvm()
    if (Platforms.jsEnabled) {
        js(IR) {
            browser()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.stdlib)
                implementation(Libs.Settings.core)
                implementation(Libs.Settings.coroutines)
                implementation(Libs.okio)

                // logging
                implementation(Libs.Log.logging)
            }
        }
    }
}
