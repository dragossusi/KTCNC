package com.mindovercnc.model

enum class SimpleCycle(val displayableString: String) {
    Turning("OD Turning"),
    Boring("ID Turning"),
    OdChamfer("OD Chamfer"),
    IdChamfer("ID Chamfer"),
    OdRadius("OD Radius"),
    IdRadius("ID Radius"),
    Facing("Facing"),
    Threading("Threading"),
    Drilling("Drilling/Reaming"),
    KeySlot("Slotting"),
}
