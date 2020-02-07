package com.dhorby.kotlin.cooking.extensions


fun Double.round(): Double {
    return "%.2f".format(this).toDouble()
}