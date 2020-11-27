package com.dhorby.kotlin.web.api

import com.dhorby.kotlin.text.service.DishDirectory
import org.http4k.contract.contract
import org.http4k.contract.openapi.ApiInfo
import org.http4k.contract.openapi.v3.OpenApi3
import org.http4k.format.Jackson
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes


fun CookingApi(dishDirectory: DishDirectory): RoutingHttpHandler =
        "/api" bind routes(
                contract {
                    renderer = OpenApi3(ApiInfo("Dish Server API", "v1.0", "List of dishes"), Jackson)
                    descriptionPath = "/openapi.json"
                    routes += FindDish(dishDirectory::lookup)
                    routes += CookDish()
                }
        )

