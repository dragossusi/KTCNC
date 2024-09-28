package ro.dragossusi.ktcnc.server

import io.ktor.server.application.*
import io.ktor.server.netty.*
import ro.dragossusi.ktcnc.server.routing.configureRouting

fun main(args: Array<String>) {
    EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    configureRouting()
}