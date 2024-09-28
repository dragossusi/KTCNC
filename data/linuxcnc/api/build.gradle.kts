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
                implementation(Libs.Settings.core)
                implementation(Libs.Settings.coroutines)
                implementation(Libs.okio)
                implementation(Libs.datetime)

                implementation(project(":data:common:api"))
                implementation(project(":logger"))
                implementation(project(":dispatcher"))
                implementation(project(":backend:rpc"))
                implementation(project(":model"))
                implementation(project(":protos"))
                implementation(project(":editor"))


                // logging
                implementation(Libs.Log.logging)
            }
        }
    }
}
