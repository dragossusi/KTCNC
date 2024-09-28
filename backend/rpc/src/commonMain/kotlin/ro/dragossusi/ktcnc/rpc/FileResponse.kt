package ro.dragossusi.ktcnc.rpc

import kotlinx.serialization.Serializable

@Serializable
data class FileResponse(
    val name: String,
    val isDirectory: Boolean,
    val path: String
)