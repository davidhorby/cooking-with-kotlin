package com.dhorby.kotlin.cooking.services

import com.dhorby.kotlin.cooking.domain.ShoppingItem
import com.dhorby.kotlin.cooking.domain.ShoppingItems
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

@DisplayName("Shopping Service tests")
internal class ShoppingCatTest {


    @Test
    internal fun addItem() {
        val shoppingCart = ShoppingCartService()
        shoppingCart.addItem(ShoppingItems.MACMINI.item)
        assertEquals(listOf(ShoppingItems.MACMINI.item), shoppingCart.listItems())
    }

    @Test
    internal fun removeItem() {
        val shoppingCart = ShoppingCartService()
        shoppingCart.addItem(ShoppingItems.MACMINI.item)
        shoppingCart.addItem(ShoppingItems.IPOD.item)
        assertEquals(listOf(ShoppingItems.MACMINI.item, ShoppingItems.IPOD.item), shoppingCart.listItems())
        shoppingCart.removeItem(ShoppingItems.MACMINI.item)
        assertEquals(listOf(ShoppingItems.IPOD.item), shoppingCart.listItems())
    }

    @Test
    internal fun listItems() {
        val expectedShoppingList = listOf(ShoppingItems.IPHONE.item, ShoppingItems.MACMINI.item)
        val shoppingCart = ShoppingCartService()
        shoppingCart.addItem(ShoppingItems.IPHONE.item)
        shoppingCart.addItem(ShoppingItems.MACMINI.item)
        val shoppingList: List<ShoppingItem> = shoppingCart.listItems()
        assertEquals(expectedShoppingList, shoppingList)
    }

    @Test
    internal fun `add multiple items of the same type`() {
        val expectedShoppingList = listOf(ShoppingItems.IPHONE.item, ShoppingItems.IPHONE.item, ShoppingItems.IPHONE.item)
        val shoppingCart = ShoppingCartService()
        shoppingCart.addItem(ShoppingItems.IPHONE.item)
        shoppingCart.addItem(ShoppingItems.IPHONE.item)
        shoppingCart.addItem(ShoppingItems.IPHONE.item)
        val shoppingList: List<ShoppingItem> = shoppingCart.listItems()
        assertEquals(shoppingList, expectedShoppingList)
    }

    @Test
    internal fun `check total price`() {
        val shoppingCart = ShoppingCartService()
        shoppingCart.addItem(ShoppingItems.IPHONE.item)
        shoppingCart.addItem(ShoppingItems.IPHONE.item)
        shoppingCart.addItem(ShoppingItems.IPHONE.item)
        val shoppingTotalPrice = shoppingCart.getTotal()
        assertEquals(1050.66,shoppingTotalPrice)
    }

    @Test
    internal fun `check sales tax`() {
        val shoppingCart = ShoppingCartService()
        shoppingCart.addItem(ShoppingItems.IPHONE.item)
        shoppingCart.addItem(ShoppingItems.IPHONE.item)
        shoppingCart.addItem(ShoppingItems.IPHONE.item)
        val shoppingTotalPrice = shoppingCart.getSalesTax()
        assertEquals(131.33, shoppingTotalPrice )
    }

    @Test
    internal fun `check adding multiple items`() {
        val expectedShoppingList = listOf(ShoppingItems.MACBOOKPRO.item, ShoppingItems.MACBOOKPRO.item, ShoppingItems.MACBOOKPRO.item, ShoppingItems.MACBOOKPRO.item)
        val shoppingCart = ShoppingCartService()
        shoppingCart.addMultipleItems(ShoppingItems.MACBOOKPRO.item, 4)
        val shoppingList: List<ShoppingItem> = shoppingCart.listItems()
        assertEquals(expectedShoppingList, shoppingList)
    }
}