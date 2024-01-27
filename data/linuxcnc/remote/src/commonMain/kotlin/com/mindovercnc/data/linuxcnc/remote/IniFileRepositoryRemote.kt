package com.mindovercnc.data.linuxcnc.remote

import com.mindovercnc.data.linuxcnc.IniFileRepository
import com.mindovercnc.data.linuxcnc.model.G53AxisLimits
import com.mindovercnc.data.linuxcnc.model.IniFile
import mu.KotlinLogging
import okio.Path.Companion.toPath

/** Remote implementation for [IniFileRepository].*/
class IniFileRepositoryRemote : IniFileRepository {
    override fun getIniFile(): IniFile {
        val path = ".".toPath()
        return IniFile(
            "",
            path,
            path,
            path,
            emptyList()
        )
    }

    override fun getActiveLimits(): G53AxisLimits {
        return G53AxisLimits(
            0.0,
            0.0,
            0.0,
            0.0
        )
    }

    override fun setCustomG53AxisLimits(g53AxisLimits: G53AxisLimits) {
        /* no-op */
    }

    override fun toggleCustomLimits() {
        /* no-op */
    }

    override fun isCustomLimitsActive(): Boolean {
        return true
    }

    private companion object {
        val LOG = KotlinLogging.logger("IniFileRepositoryRemote")
    }
}