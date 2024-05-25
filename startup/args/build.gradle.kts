plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
}

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.cli)
                implementation(Libs.Log.logging)
                implementation(Libs.okio)
                implementation(compose.ui)
            }
        }
    }
}
