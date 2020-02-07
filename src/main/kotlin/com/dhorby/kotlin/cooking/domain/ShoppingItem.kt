package com.dhorby.kotlin.cooking.domain

typealias ItemName = String
typealias ItemPrice = Double

data class ShoppingItem(val name: ItemName, val price:ItemPrice)