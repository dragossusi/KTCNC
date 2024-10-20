plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.kotlinx.rpc.plugin")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()

    if (Platforms.jsEnabled) {
        js(IR) {
            browser()
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(Libs.Coroutines.core)
            api(Libs.Serialization.core)
            api(Libs.Serialization.json)

            implementation(Libs.Rpc.core)
            implementation(Libs.Rpc.serializationJson)
            implementation(Libs.datetime)
        }
    }
}
