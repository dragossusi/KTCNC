plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
}

version = Versions.app

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.material3)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(Libs.Compose.splitpane)
            }
        }
    }
}
