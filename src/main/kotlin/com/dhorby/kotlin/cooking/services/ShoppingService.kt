package com.dhorby.kotlin.cooking.services

import com.dhorby.kotlin.cooking.actions.SalesTax
import com.dhorby.kotlin.cooking.actions.Shopping
import com.dhorby.kotlin.cooking.actions.TotalPrice
import com.dhorby.kotlin.cooking.domain.ShoppingCart
import com.dhorby.kotlin.cooking.domain.ShoppingItem

class ShoppingService:Shopping {

    val shoppingCart:ShoppingCart = ShoppingCart()

    override fun listItems(): List<ShoppingItem> {
       return shoppingCart.listItems()
    }

    override fun addItem(shoppingItem: ShoppingItem) {
        shoppingCart.addItem(shoppingItem)
    }

    override fun removeItem(shoppingItem: ShoppingItem) {
        shoppingCart.removeItem(shoppingItem)
    }

    override fun getTotal(): TotalPrice {
        return shoppingCart.getTotal()
    }

    override fun getSalesTax(): SalesTax {
        return shoppingCart.getSalesTax()
    }

}