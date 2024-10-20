plugins { kotlin("multiplatform") }

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

                implementation(Libs.bignum)
                implementation(Libs.Log.logging)
            }
        }
    }
}
