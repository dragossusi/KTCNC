import java.io.File
import org.gradle.api.Project

object NativePaths {
  fun createJvmArgs(project: Project) =
    "-Djava.library.path=${getNativePaths(project).joinToString(":")}"

  private fun getNativePaths(project: Project): List<String> {

    val linuxCncHome = project.requirePath("LINUXCNC_HOME", "linuxcnc.home")

    val linuxCncJdk = project.requirePath("LINUXCNC_JDK", "linuxcnc.jdk")

    val vtkLib = project.requirePath("VTK_LIB", "vtk.lib")

    return listOf(
      // linuxcnc home
      File(linuxCncHome, "lib").path,
      // jdk
      File(linuxCncJdk, "lib").path,
      // vtk
      vtkLib
    )
  }
}
