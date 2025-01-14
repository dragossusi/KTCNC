import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
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
                implementation(Libs.test)
                implementation(Libs.Coroutines.core)
                implementation(Libs.Serialization.json)

                // logging
                implementation(Libs.Log.logging)

                // okio
                implementation(Libs.okio)

                implementation(Libs.datetime)

                // compose
                implementation(compose.material3)
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                // internal modules
                implementation(project(":backend:rpc"))

                implementation(project(":frontend:clipboard"))
                implementation(project(":frontend:actor"))

                implementation(project(":dispatcher"))
                implementation(project(":editor"))
                implementation(project(":model"))
                implementation(project(":initializer"))

                implementation(project(":data:common:api"))
                implementation(project(":data:linuxcnc:api"))
                implementation(project(":data:lathetools:api"))
                implementation(project(":data:lathehal:api"))
                implementation(project(":data:gcode:api"))
                implementation(project(":data:settings:api"))

                implementation(project(":frontend:breadcrumb"))
                implementation(project(":frontend:filesystem"))
                implementation(project(":frontend:scroll"))
                implementation(project(":frontend:editor"))
                implementation(project(":frontend:widgets"))
                implementation(project(":frontend:listitem"))
                implementation(project(":frontend:numpad"))
                implementation(project(":frontend:format"))

                implementation(project(":startup:args"))

                implementation(project(":protos"))

                //    implementation(project(":vtk"))
                implementation(Libs.Kodein.compose)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.uiTooling)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(compose.desktop.uiTestJUnit4)
                implementation(Libs.Coroutines.test)
                implementation(Libs.mockk)
                implementation(Libs.Log.logback)
                implementation(Libs.Coroutines.test)
                implementation(compose.materialIconsExtended)
            }
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.freeCompilerArgs.add("-Xopt-in=kotlin.RequiresOptIn")
}
