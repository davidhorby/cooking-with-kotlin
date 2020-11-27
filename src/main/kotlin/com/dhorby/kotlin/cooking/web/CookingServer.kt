//@file:JvmName("CookingServer")
package com.dhorby.kotlin.cooking.web

import com.dhorby.kotlin.cooking.domain.Dish
import com.dhorby.kotlin.cooking.domain.Ingredient
import com.dhorby.kotlin.cooking.domain.allIngredients
import com.dhorby.kotlin.cooking.services.CookingService
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import org.http4k.template.TemplateRenderer
import org.http4k.template.ThymeleafTemplates
import org.http4k.template.ViewModel

open class Page() : ViewModel
class IndexPage() : Page()
class CookPage(val dish: Dish, val allIngredients: List<Ingredient>) : Page()

class CookingServer() {
    companion object {
        operator fun invoke(port:Int): Http4kServer {
            return buildServer(port)
        }

        private fun buildServer(port:Int): Http4kServer {
            val renderer = ThymeleafTemplates().HotReload("src/main/resources")
            val routes: RoutingHttpHandler = routes(
                    "/" bind Method.GET to { Response(OK).body(renderer(IndexPage())) },
                    "/ping" bind Method.GET to { Response(OK) },
                    "/cook" bind Method.GET to { cook(renderer) },
                    "/cook" bind Method.POST to { request ->
                        val ings = request.query("ingredient")
                        println("--->>>>>" + ings)
                        cook(renderer)
                    },
                    "/fail" bind Method.POST to { Response(INTERNAL_SERVER_ERROR) }
            )
            return routes.asServer(SunHttp(port))
        }

        fun cook(renderer:TemplateRenderer): Response {
            val dish = CookingService().cook(listOf(Ingredient.Potato, Ingredient.Cheese))
            return Response(OK).body(renderer(CookPage(dish, allIngredients)))
        }

    }


}

fun main(args: Array<String>) {
    CookingServer(8080).start()
}





