package com.dhorby.kotlin.cooking.web

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import org.http4k.template.ThymeleafTemplates
import org.http4k.template.ViewModel
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

open class Page() : ViewModel
class IndexPage() : Page()

class CookingServer() {
    companion object {
        operator fun invoke(port:Int): Http4kServer {
            return buildServer(port)
        }

        fun buildServer(port:Int): Http4kServer {
            val renderer = ThymeleafTemplates(configure = thymeleafConfiguration()).CachingClasspath()
            return routes(
                    "/" bind Method.GET to { request: Request -> Response(OK).body(renderer(IndexPage())) },
                    "/ping" bind Method.GET to { request: Request -> Response(OK) },
                    "/fail" bind Method.POST to { request: Request -> Response(INTERNAL_SERVER_ERROR) }
            ).asServer(SunHttp(port))
        }

        private fun thymeleafConfiguration(): (TemplateEngine) -> TemplateEngine {
            return { templateEngine ->
                val loader = ClassLoaderTemplateResolver(ClassLoader.getSystemClassLoader())
                loader.prefix = "/"
                loader.suffix = ".html"
                templateEngine.setTemplateResolver(loader)
                templateEngine
            }
        }

    }

}

fun main(args: Array<String>) {
    CookingServer(8080).start()
}



