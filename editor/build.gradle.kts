plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

version = Versions.app

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {

            dependencies {
                implementation(project(":dispatcher"))
                implementation(Libs.stdlib)
                implementation(Libs.Serialization.json)
                implementation(Libs.Coroutines.core)
                implementation(Libs.Serialization.json_okio)
                implementation(Libs.okio)
                implementation(Libs.Kodein.core)
            }
        }
    }
}