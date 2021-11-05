//@file:JvmName("CookingServer")
package com.dhorby.kotlin.cooking.web

import com.dhorby.kotlin.cooking.domain.*
import com.dhorby.kotlin.cooking.domain.TemperatureScale.Centigrade
import com.dhorby.kotlin.cooking.services.CookingService
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR
import org.http4k.core.Status.Companion.OK
import org.http4k.core.body.form
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
class CookPage(val dish: Dish, val allIngredients: List<Ingredient>) : Page() {
    public val dishName = dish::class.java
}

class CookingServer() {
    companion object {
        operator fun invoke(port: Int): Http4kServer {
            return buildServer(port)
        }

        val cookingFixedTheadPool = newFixedThreadPoolContext(5, "cooking")

        private fun buildServer(port: Int): Http4kServer {
            val renderer = ThymeleafTemplates().HotReload("src/main/resources")
            val routes: RoutingHttpHandler = routes(
                "/" bind Method.GET to { Response(OK).body(renderer(IndexPage())) },
                "/ping" bind Method.GET to { Response(OK) },
                "/cook" bind Method.GET to { request ->
                    val ingredients: List<String?> = request.queries("ingredient")
                    val ingredientMap: List<Ingredient> = ingredients.filterNotNull().map {
                                Ingredient.valueOf(it)
                            }
                    cook(renderer, ingredientMap)
                },
                "/cook" bind Method.POST to { request ->
                    try {
                        val receivedForm = request.form("ingredient")
//                            val ingredients: List<String> = receivedForm.fieldValues("ingredient")
//                            val ingredientMap: List<Ingredient> = ingredients.map {
//                                Ingredient.valueOf(it)
//                            }
                        cook(renderer)
                    } catch (ex: Exception) {
                        println(ex.message)
                        ex.printStackTrace()
                        Response(OK)
                    }

                },
                "/fail" bind Method.POST to { Response(INTERNAL_SERVER_ERROR) }
            )
            return routes.asServer(SunHttp(port))
        }

        fun cook(renderer: TemplateRenderer, ingredientMap: List<Ingredient> = emptyList()): Response {
            val cookedDish = runBlocking {
                withContext(cookingFixedTheadPool) {
                    CookingService().cook(ingredientMap) `cook at` 200 degrees Centigrade
                }
            }
            val cookPage = CookPage(cookedDish, allIngredients)
            println(cookPage.dishName)
            return Response(OK).body(renderer(cookPage))
        }

    }


}

fun main(args: Array<String>) {
    CookingServer(8080).start()
}





