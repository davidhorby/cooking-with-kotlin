//@file:JvmName("CookingServer")
package com.dhorby.kotlin.text.web

import com.dhorby.kotlin.cooking.domain.Dish
import com.dhorby.kotlin.cooking.domain.Ingredient
import com.dhorby.kotlin.cooking.domain.allIngredients
import com.dhorby.kotlin.cooking.services.CookingService
import com.dhorby.kotlin.cooking.web.IndexPage
import com.dhorby.kotlin.cooking.web.Page
import com.dhorby.kotlin.text.service.MatcherService
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

class MatchPage(val matches: List<String> = emptyList()) : Page()

class MatchServer() {
    companion object {
        operator fun invoke(port:Int): Http4kServer {
            return buildServer(port)
        }

        private fun buildServer(port:Int): Http4kServer {
            val renderer = ThymeleafTemplates().HotReload("src/main/resources")
            return routes(
                    "/" bind Method.GET to { Response(OK).body(renderer(MatchPage())) },
                    "/ping" bind Method.GET to { Response(OK) },
                    "/match" bind Method.GET to {request ->
                        val name = request.query("name")
                        match(renderer, name!!)
                    },
                    "/fail" bind Method.POST to { Response(INTERNAL_SERVER_ERROR) }
            ).asServer(SunHttp(port))
        }

        fun match(renderer:TemplateRenderer, institutionName:String): Response {
            val matches = MatcherService().match(institutionName)
            val results = matches.map { it.origVal }
            return Response(OK).body(renderer(MatchPage(results)))
        }

    }


}

fun main(args: Array<String>) {
    MatchServer(8080).start()
}





