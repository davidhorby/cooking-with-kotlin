package com.dhorby.kotlin.web

import com.dhorby.kotlin.web.Settings.DISH_DIRECTORY_URL
import com.dhorby.kotlin.web.Settings.PORT
import org.http4k.client.OkHttp
import org.http4k.cloudnative.env.Environment
import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.http4k.lens.int
import org.http4k.lens.uri
import org.http4k.server.Http4kServer
import org.http4k.server.Undertow
import org.http4k.server.asServer
import java.time.Clock

fun CookingServer(env: Environment): Http4kServer =
        CookingSystem(clock = Clock.systemUTC(),
                dishDirectoryHttp = ClientFilters.SetHostFrom(DISH_DIRECTORY_URL(env)).then(OkHttp())
        ).asServer(Undertow(PORT(env)))


object Settings {
    val PORT = EnvironmentKey.int().required("PORT")
    val DISH_DIRECTORY_URL = EnvironmentKey.uri().required("DISH_DIRECTORY_URL")
}