package com.dhorby.kotlin.text.service

import com.dhorby.kotlin.cooking.domain.Dish
import com.dhorby.kotlin.cooking.domain.DishName
import org.http4k.cloudnative.RemoteRequestFailed
import org.http4k.core.*
import org.http4k.filter.ClientFilters
import org.http4k.filter.HandleRemoteRequestFailed
import org.http4k.format.Jackson.auto

class DishDirectory(http: HttpHandler) {

    // this filter will handle and rethrow non-successful HTTP responses
    private val http = ClientFilters.HandleRemoteRequestFailed({ status.successful || status == Status.NOT_FOUND }).then(http)

    private val dishes = Body.auto<List<Dish>>().toLens()
    private val dish = Body.auto<Dish>().toLens()

    fun list(): List<Dish> = dishes(http(Request(Method.GET, "/dish")))

    fun lookup(dishName: DishName): Dish? =
            with(http(Request(Method.GET, "/dish/${dishName.value}"))) {
                when (status) {
                    Status.NOT_FOUND -> null
                    Status.OK -> dish(this)
                    else -> throw RemoteRequestFailed(status, "dish directory")
                }
            }

}
