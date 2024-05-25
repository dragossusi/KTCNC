import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
    id("io.github.takahirom.roborazzi")
}

version = Versions.app

kotlin {
    jvm()
    js(IR) { browser() }

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

                implementation(project(":frontend:format"))

                implementation(compose.materialIconsExtended)
                implementation(compose.material3)
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(Libs.Roborazzi.compose_desktop)
                implementation(Libs.Roborazzi.junit_rule)

                implementation(compose.desktop.currentOs)
                implementation(compose.desktop.uiTestJUnit4)
            }
        }
    }
}

// Roborazzi Desktop support uses Context Receivers
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions { freeCompilerArgs += "-Xcontext-receivers" }
}
