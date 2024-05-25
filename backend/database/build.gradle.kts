import Versions.sqliteJdbc

plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp")
    id("androidx.room")
}

version = Versions.app

repositories { mavenCentral() }

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.stdlib)
                api(Libs.Coroutines.core)
                api(Libs.Kodein.core)

                implementation(Libs.Log.logging)

                implementation(Libs.okio)

                implementation("org.xerial:sqlite-jdbc:$sqliteJdbc")
                implementation(Libs.Room.runtime)
                implementation(Libs.Sqlite.bundled)

                implementation(project(":dispatcher"))
                implementation(project(":initializer"))
                implementation(project(":model"))

                implementation(project(":data:lathetools:api"))
                implementation(project(":ktlcnc:model"))
            }
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    ksp(Libs.Room.compiler)
}
