package com.dhorby.kotlin.web

import org.http4k.cloudnative.env.Environment

/**
 * Main entry point. Note that this will not run without setting up the correct environmental variables
 * - see RunnableEnvironment for a demo-able version of the server.
 */

object CookingApp {

    @JvmStatic
    fun main(args: Array<String>) {
        CookingServer(Environment.ENV).start()
    }

}
