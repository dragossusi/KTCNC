plugins { kotlin("multiplatform") }

version = Versions.app

kotlin {
    jvm()
    js(IR) {
        browser()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.stdlib)
                implementation(Libs.Kodein.core)
                implementation(Libs.Settings.core)
                implementation(Libs.Settings.coroutines)
                implementation(project(":logger"))
                implementation(project(":dispatcher"))
                implementation(project(":logger"))
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
