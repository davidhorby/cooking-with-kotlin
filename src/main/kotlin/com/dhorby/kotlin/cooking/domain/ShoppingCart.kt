package com.dhorby.kotlin.cooking.domain

import com.dhorby.kotlin.cooking.actions.NumberOfShoppingItems
import com.dhorby.kotlin.cooking.actions.SalesTax
import com.dhorby.kotlin.cooking.actions.Shopping
import com.dhorby.kotlin.cooking.actions.TotalPrice
import com.dhorby.kotlin.cooking.extensions.round

class ShoppingCart: Shopping {

    private val shoppingList:MutableList<ShoppingItem> = mutableListOf<ShoppingItem>()
    private val salesTax=12.5

    override fun listItems(): MutableList<ShoppingItem> {
        return shoppingList
    }

    override fun addItem(shoppingItem: ShoppingItem) {
        shoppingList.add(shoppingItem)
    }

    override fun addMultipleItems(shoppingItem: ShoppingItem, numberOfShoppingItems: NumberOfShoppingItems) {
        for (i in 1..numberOfShoppingItems) shoppingList += shoppingItem
    }

    override fun removeItem(shoppingItem: ShoppingItem) {
        shoppingList.remove(shoppingItem)
    }

    override fun getTotal(): TotalPrice {
        return shoppingList.map { it.price }.sum().round()
    }

    override fun getSalesTax():SalesTax {
        return ((getTotal() / 100) * salesTax).round()
    }

}