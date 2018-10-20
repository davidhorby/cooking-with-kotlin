package com.dhorby.kotlin.cooking.web

import com.dhorby.kotlin.cooking.domain.Dish
import com.dhorby.kotlin.cooking.domain.Ingredient
import com.dhorby.kotlin.cooking.services.CookingService
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import org.http4k.template.TemplateRenderer
import org.http4k.template.ThymeleafTemplates
import org.http4k.template.ViewModel
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

open class Page() : ViewModel
class IndexPage() : Page()
class CookPage(val dish: Dish) : Page()

class CookingServer() {
    companion object {
        operator fun invoke(port:Int): Http4kServer {
            return buildServer(port)
        }

        private fun buildServer(port:Int): Http4kServer {
            val renderer = ThymeleafTemplates(configure = thymeleafConfiguration()).CachingClasspath()
            return routes(
                    "/" bind Method.GET to { Response(OK).body(renderer(IndexPage())) },
                    "/ping" bind Method.GET to { Response(OK) },
                    "/cook" bind Method.GET to { cook(renderer) },
                    "/fail" bind Method.POST to { Response(INTERNAL_SERVER_ERROR) }
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

        fun cook(renderer:TemplateRenderer): Response {
            val dish = CookingService().cook(listOf(Ingredient.Potato, Ingredient.Cheese))
            return Response(OK).body(renderer(CookPage(dish)))
        }

    }

}

fun main(args: Array<String>) {
    CookingServer(8080).start()
}



