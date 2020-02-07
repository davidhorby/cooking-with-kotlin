package com.dhorby.kotlin.cooking.actions

import com.dhorby.kotlin.cooking.domain.ShoppingItem

interface Shopping {
    fun listItems():List<ShoppingItem>
    fun addItem(shoppingItem: ShoppingItem)
    fun removeItem(ingredient: ShoppingItem)
}