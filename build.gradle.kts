buildscript {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")

        gradlePluginPortal()
    }
}

plugins {
    kotlin("multiplatform") version Versions.kotlin apply false
    kotlin("plugin.serialization") version Versions.kotlin apply false
    kotlin("plugin.compose") version Versions.kotlin apply false

    id("org.jetbrains.compose") version Versions.compose apply false

    id("com.squareup.wire") version Versions.Grpc.Wire.plugin apply false
    id("com.android.application") version Versions.Android.plugin apply false
    id("io.github.takahirom.roborazzi") version Versions.roborazzi apply false

    id("com.google.devtools.ksp") version Versions.ksp apply false
    id("com.google.protobuf") version Versions.Grpc.plugin apply false

    id("androidx.room") version Versions.room apply false
    // rpc
    id("org.jetbrains.kotlinx.rpc.plugin") version Versions.Kotlinx.rpc apply false

    id("io.ktor.plugin") version Versions.ktor
}

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")
    }
}
