package com.mindovercnc.model

enum class SpindleDirection(val shortName: String) {
    Forward("fwd"),
    Reverse("rev"),
    Both("both"),

    /** Used for slotting tools. */
    None("none")
}
