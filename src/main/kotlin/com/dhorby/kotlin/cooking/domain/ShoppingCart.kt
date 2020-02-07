package com.dhorby.kotlin.cooking.domain

import com.dhorby.kotlin.cooking.actions.Shopping
import com.dhorby.kotlin.cooking.actions.TotalPrice

class ShoppingCart: Shopping {

    private val shoppingList:MutableList<ShoppingItem> = mutableListOf<ShoppingItem>()

    override fun listItems(): MutableList<ShoppingItem> {
        return shoppingList
    }

    override fun addItem(shoppingItem: ShoppingItem) {
        shoppingList.add(shoppingItem)
    }

    override fun removeItem(shoppingItem: ShoppingItem) {
        shoppingList.remove(shoppingItem)
    }

    override fun getTotal(): TotalPrice {
        return shoppingList.map { it.price }.sum()
    }

}