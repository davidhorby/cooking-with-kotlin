package com.dhorby.kotlin.cooking.services

import com.dhorby.kotlin.cooking.domain.ShoppingItem
import com.dhorby.kotlin.cooking.domain.ShoppingItems
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

@DisplayName("Shopping Service tests")
internal class ShoppingServiceTest {


    @Test
    internal fun addItem() {
        val shoppingService = ShoppingService()
        shoppingService.addItem(ShoppingItems.MACMINI.item)
        assertEquals(listOf(ShoppingItems.MACMINI.item), shoppingService.listItems())
    }

    @Test
    internal fun removeItem() {
        val shoppingService = ShoppingService()
        shoppingService.addItem(ShoppingItems.MACMINI.item)
        shoppingService.addItem(ShoppingItems.IPOD.item)
        assertEquals(listOf(ShoppingItems.MACMINI.item, ShoppingItems.IPOD.item), shoppingService.listItems())
        shoppingService.removeItem(ShoppingItems.MACMINI.item)
        assertEquals(listOf(ShoppingItems.IPOD.item), shoppingService.listItems())
    }

    @Test
    internal fun listItems() {
        val expectedShoppingList = listOf(ShoppingItems.IPHONE.item, ShoppingItems.MACMINI.item)
        val shoppingService = ShoppingService()
        shoppingService.addItem(ShoppingItems.IPHONE.item)
        shoppingService.addItem(ShoppingItems.MACMINI.item)
        val shoppingList: List<ShoppingItem> = shoppingService.listItems()
        assertEquals(shoppingList, expectedShoppingList)
    }

    @Test
    internal fun `add multiple items of the same type`() {
        val expectedShoppingList = listOf(ShoppingItems.IPHONE.item, ShoppingItems.IPHONE.item, ShoppingItems.IPHONE.item)
        val shoppingService = ShoppingService()
        shoppingService.addItem(ShoppingItems.IPHONE.item)
        shoppingService.addItem(ShoppingItems.IPHONE.item)
        shoppingService.addItem(ShoppingItems.IPHONE.item)
        val shoppingList: List<ShoppingItem> = shoppingService.listItems()
        assertEquals(shoppingList, expectedShoppingList)
    }

    @Test
    internal fun `check total price`() {
        val expectedShoppingList = listOf(ShoppingItems.IPHONE.item, ShoppingItems.IPHONE.item, ShoppingItems.IPHONE.item)
        val shoppingService = ShoppingService()
        shoppingService.addItem(ShoppingItems.IPHONE.item)
        shoppingService.addItem(ShoppingItems.IPHONE.item)
        shoppingService.addItem(ShoppingItems.IPHONE.item)
        val shoppingTotalPrice = shoppingService.getTotal()
        assertEquals(shoppingTotalPrice, 1050.66)
    }
}