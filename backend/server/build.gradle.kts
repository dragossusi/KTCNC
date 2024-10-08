plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.kotlinx.rpc.plugin")
    id("io.ktor.plugin")
}

version = Versions.app

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")

    implementation(Libs.Rpc.ktorServer)
    implementation(Libs.okio)
    implementation(Libs.datetime)
    implementation(Libs.Rpc.serializationJson)

    implementation(project(":backend:database"))
    implementation(project(":backend:rpc"))
}
