package com.dhorby.kotlin.cooking.actions

import com.dhorby.kotlin.cooking.domain.ShoppingItem

typealias TotalPrice = Double
typealias SalesTax = Double
typealias NumberOfShoppingItems = Int

interface Shopping {
    fun listItems():List<ShoppingItem>
    fun addItem(shoppingItem: ShoppingItem)
    fun addMultipleItems(shoppingItem: ShoppingItem, numberOfShoppingItems: NumberOfShoppingItems )
    fun removeItem(ingredient: ShoppingItem)
    fun getTotal():TotalPrice
    fun getSalesTax(): SalesTax
}