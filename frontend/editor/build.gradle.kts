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
                implementation(Libs.Coroutines.core)
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(Libs.okio)
                implementation(Libs.Kodein.compose)
                implementation(project(":editor"))
                implementation(project(":frontend:scroll"))
            }
        }
    }
}
