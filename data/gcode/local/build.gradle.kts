plugins { kotlin("jvm") }

version = Versions.app

dependencies {
    implementation(Libs.stdlib)
    implementation(Libs.Kodein.core)
    implementation(Libs.Settings.core)
    implementation(Libs.Settings.coroutines)
    implementation(project(":data:gcode:api"))
    implementation(project(":data:linuxcnc:api"))
    implementation(project(":data:linuxcnc:local"))
    implementation(project(":logger"))
    implementation(project(":dispatcher"))
    implementation(project(":logger"))
    implementation(project(":reader:gcode"))
//                implementation(project(":database"))
    implementation(project(":model"))
    implementation(project(":protos"))
    implementation(project(":editor"))
    implementation(Libs.okio)

    // logging
    implementation(Libs.Log.logging)
}
