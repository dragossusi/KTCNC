package com.mindovercnc.linuxcnc.tools.local.di

import com.mindovercnc.linuxcnc.tools.CuttingInsertsRepository
import com.mindovercnc.linuxcnc.tools.LatheToolRepository
import com.mindovercnc.linuxcnc.tools.ToolHolderRepository
import com.mindovercnc.linuxcnc.tools.WorkpieceMaterialRepository
import com.mindovercnc.linuxcnc.tools.local.CuttingInsertsRepositoryLocal
import com.mindovercnc.linuxcnc.tools.local.LatheToolRepositoryLocal
import com.mindovercnc.linuxcnc.tools.local.ToolHolderRepositoryLocal
import com.mindovercnc.linuxcnc.tools.local.WorkpieceMaterialRepositoryLocal
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val ToolsLocalModule = DI.Module("tools_local") {
    bindSingleton<ToolHolderRepository> { ToolHolderRepositoryLocal(instance(), instance()) }
    bindSingleton<CuttingInsertsRepository> { CuttingInsertsRepositoryLocal(instance()) }
    bindSingleton<LatheToolRepository> { LatheToolRepositoryLocal(instance(), instance()) }
    bindSingleton<WorkpieceMaterialRepository> { WorkpieceMaterialRepositoryLocal(instance()) }
}