package com.dhorby.kotlin.cooking.domain

import com.dhorby.kotlin.cooking.actions.Shopping

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

}