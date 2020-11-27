package com.dhorby.kotlin.web

import com.dhorby.kotlin.text.service.DishDirectory
import com.dhorby.kotlin.web.api.CookingApi
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.http4k.routing.ResourceLoader
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.routes
import org.http4k.routing.static
import java.time.Clock


fun CookingSystem(clock: Clock,
                  dishDirectoryHttp: HttpHandler): HttpHandler {

    val dishDirectory = DishDirectory(
            ClientFilters.RequestTracing()
                    .then(dishDirectoryHttp)
    )

    val app: RoutingHttpHandler = routes(
            CookingApi(dishDirectory),
            static(resourceLoader = ResourceLoader.Classpath("public"))
    )

    return app
}

