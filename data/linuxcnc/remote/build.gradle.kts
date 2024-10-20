plugins { kotlin("multiplatform") }

version = Versions.app

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.stdlib)
                implementation(Libs.Kodein.core)
                implementation(Libs.Coroutines.core)
                implementation(project(":data:linuxcnc:api"))
                implementation(project(":logger"))
                implementation(project(":dispatcher"))
                implementation(project(":backend:rpc"))
                implementation(project(":protos"))
                implementation(project(":model"))
                implementation(Libs.okio)
                implementation(Libs.Rpc.ktorClient)

                // logging
                implementation(Libs.Log.logging)
            }
        }
    }
}
