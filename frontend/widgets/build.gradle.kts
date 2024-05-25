import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
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
                implementation(Libs.stdlib)
                implementation(Libs.Coroutines.core)
                implementation(Libs.Serialization.json)

                // logging
                implementation(Libs.Log.logging)

                // okio
                implementation(Libs.okio)

                implementation(Libs.datetime)
                implementation(Libs.bignum)

                // compose
                implementation(compose.materialIconsExtended)
                implementation(compose.material3)
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                implementation(project(":frontend:numpad"))
                implementation(project(":frontend:format"))
                implementation(project(":model"))
            }
        }
    }
}
