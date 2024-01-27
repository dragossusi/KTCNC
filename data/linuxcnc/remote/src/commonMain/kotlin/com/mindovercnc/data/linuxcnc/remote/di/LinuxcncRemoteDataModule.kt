package com.mindovercnc.data.linuxcnc.remote.di

import com.mindovercnc.data.linuxcnc.*
import com.mindovercnc.data.linuxcnc.remote.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val LinuxcncRemoteDataModule = DI.Module("linuxcnc_remote_data") {
    bindSingleton<SystemMessageRepository> {
        SystemMessageRepositoryRemote(instance(), instance())
    }
    bindSingleton<IniFileRepository> { IniFileRepositoryRemote() }

    bindSingleton<CncCommandRepository> {
        CncCommandRepositoryRemote(instance())
    }
    bindSingleton<CncStatusRepository> {
        CncStatusRepositoryRemote(instance())
    }

    bindSingleton<LinuxCncToolsRepository> { LinuxCncToolsRepositoryRemote() }
    bindSingleton<VarFileRepository> { VarFileRepositoryRemote() }
    bindSingleton<FileSystemRepository> { FileSystemRepositoryRemote() }
}