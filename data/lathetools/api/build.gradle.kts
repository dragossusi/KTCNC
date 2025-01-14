plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

version = Versions.app

kotlin {
    jvm()
    if (Platforms.jsEnabled) {
        js(IR) { browser() }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.stdlib)
                implementation(Libs.Serialization.json)
                implementation(Libs.Kodein.core)
                implementation(Libs.Settings.core)
                implementation(Libs.Settings.coroutines)
                implementation(project(":data:common:api"))
                implementation(project(":logger"))
                implementation(project(":dispatcher"))
                //                implementation(project(":database"))
                implementation(project(":model"))
                implementation(project(":protos"))
                implementation(project(":editor"))
                implementation(Libs.okio)

                // logging
                implementation(Libs.Log.logging)
            }
        }
    }
}
