package com.dhorby.kotlin.cooking.web

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.server.Http4kServer
import org.http4k.server.SunHttp
import org.http4k.server.asServer

class CookingServer() {
    companion object {
        operator fun invoke(port:Int): Http4kServer {
            return buildServer(port)
        }

        fun buildServer(port:Int): Http4kServer {
            val app: HttpHandler = { request: Request -> Response(OK).body(request.body) }
            return app.asServer(SunHttp(port = port))
        }

    }

}

fun main(args: Array<String>) {
    CookingServer(8080).start()
}



