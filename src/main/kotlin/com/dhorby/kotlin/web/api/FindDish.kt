package com.dhorby.kotlin.web.api

import com.dhorby.kotlin.cooking.domain.Dish
import com.dhorby.kotlin.cooking.domain.DishName
import com.dhorby.kotlin.cooking.domain.Message
import com.dhorby.kotlin.cooking.web.CookingServer
import org.http4k.contract.ContractRoute
import org.http4k.contract.ContractRouteSpec0
import org.http4k.contract.meta
import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.lens.Query
import org.http4k.template.ThymeleafTemplates

fun FindDish(lookup: (DishName) -> Dish?): ContractRoute {
    val dishName = Query.map(::DishName).required("dishname")

    val message = Body.auto<Message>().toLens()
    val dishFinder: HttpHandler = { dishName ->
        lookup(dishName(dishName))?.ingredients
                ?.let { ingredients ->
                    Response(Status.OK).with(message of Message("Found dish with ingredients $ingredients"))
                }
                ?: Response(Status.NOT_FOUND).with(message of Message("Unknown dish"))
    }

    return "/dish" meta {
        queries += dishName
        summary = "Search for a recipe"
        returning(Status.OK, message to Message("Found dish with ingredients"))
        returning(Status.NOT_FOUND, message to Message("Unknown dish"))
    } bindContract Method.GET to dishFinder
}

fun CookDish(): ContractRoute {
    val renderer = ThymeleafTemplates().HotReload("src/main/resources")
    val message = Body.auto<Message>().toLens()
    val contractRoute: ContractRouteSpec0.Binder = "/cook" meta {
        summary = "Start cooking!"
        returning(Status.OK, message to Message("Start cooking"))
        returning(Status.NOT_FOUND, message to Message("Unknown dish"))
    } bindContract Method.GET

    val handler:HttpHandler = { CookingServer.cook(renderer) }
    return contractRoute to handler
}
