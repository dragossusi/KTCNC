# Ktlcnc native
> TODO: rename this to something else

### Building

The binary is created using make.

In order to create the binary, you need to set 2 variables:

| Env           | Description                |
|---------------|----------------------------|
| LINUXCNC_HOME | The linuxcnc home folder   |
| LINUXCNC_JDK  | The jdk to be used for JNI |

After the variables are set, use the `make` command to build the binary. This command will also copy the binary to the
resources of the kotlin module.