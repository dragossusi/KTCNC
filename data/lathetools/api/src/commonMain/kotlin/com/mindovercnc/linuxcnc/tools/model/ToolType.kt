package com.mindovercnc.linuxcnc.tools.model

enum class ToolType(
    val displayableValue: String
) {
    Turning("Turning"),
    Boring("Boring"),
    Drilling("Drilling"),
    Reaming("Reaming"),
    Parting("Parting"),
    Grooving("Grooving"),
    OdThreading("Od Threading"),
    IdThreading("Id Threading"),
    Slotting("Key Slotting");
}
