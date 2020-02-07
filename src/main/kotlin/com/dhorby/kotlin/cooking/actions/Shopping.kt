package com.dhorby.kotlin.cooking.actions

import com.dhorby.kotlin.cooking.domain.ShoppingItem

typealias TotalPrice = Double

interface Shopping {
    fun listItems():List<ShoppingItem>
    fun addItem(shoppingItem: ShoppingItem)
    fun removeItem(ingredient: ShoppingItem)
    fun getTotal():TotalPrice
}