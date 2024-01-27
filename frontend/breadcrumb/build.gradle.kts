import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("io.github.takahirom.roborazzi")
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
                implementation(Libs.Coroutines.core)
                implementation(Libs.Serialization.json)
                implementation(Libs.okio)

                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(project(":frontend:scroll"))
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
