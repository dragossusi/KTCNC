plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
//    id("io.ktor.plugin")
//    id("org.jetbrains.kotlinx.rpc.plugin")
}

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            api(Libs.Coroutines.core)
            api(Libs.Serialization.json)

//            implementation(Libs.Rpc.core)
//            implementation(Libs.Rpc.serializationJson)
//            implementation(Libs.datetime)
        }
    }
}
