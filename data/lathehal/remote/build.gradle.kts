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
                implementation(Libs.Kodein.core)
                implementation(Libs.Settings.core)
                implementation(Libs.Settings.coroutines)
                implementation(project(":data:lathetools:api"))
                implementation(project(":data:lathehal:api"))
                implementation(project(":logger"))
                implementation(project(":dispatcher"))
//                implementation(project(":database"))
                implementation(project(":model"))
                implementation(project(":protos"))
                implementation(Libs.okio)

                // logging
                implementation(Libs.Log.logging)
            }
        }
    }
}
