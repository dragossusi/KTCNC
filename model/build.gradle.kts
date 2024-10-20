plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

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
                implementation(Libs.Coroutines.core)
                implementation(Libs.Serialization.json)
                //                implementation(project(":ktlcnc"))
                implementation(project(":protos"))
                implementation(Libs.okio)
                implementation(Libs.datetime)

                implementation(project(":frontend:format"))
            }
        }
    }
}
