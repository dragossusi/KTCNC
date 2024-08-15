plugins {
    kotlin("multiplatform")
    id("com.squareup.wire")
}

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("com.squareup.wire:wire-grpc-client:4.7.2")
            }
        }
    }
}

wire {
    sourcePath {
        srcDir(File("src/main"))
    }
    kotlin {
        javaInterop = true
        rpcRole = "client"
        rpcCallStyle = "blocking"
    }
}
