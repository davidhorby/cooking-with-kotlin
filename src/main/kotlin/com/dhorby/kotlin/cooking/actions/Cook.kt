package com.dhorby.kotlin.cooking.actions

import kotlinx.coroutines.experimental.*


fun main(args: Array<String>) = runBlocking {
    launch { cook(1000L) }
    println("Hello,")
}

// this is your first suspending function
suspend fun cook(cookTime:Long) {
    delay(cookTime)
    println("World!")
}