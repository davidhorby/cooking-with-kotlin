package com.dhorby.kotlin.cooking.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class BurritoTest() {

    @Test
    fun `it should be possible to create a potato dish`() {
        val dishUnderTest = Dish() containing Ingredient.Potato
        assertTrue(dishUnderTest is PotatoDish)
        assertTrue(dishUnderTest is SimplePotato)
    }

    @Test
    fun `it should be possible to create a potato with cheese dish` () {
        val dishUnderTest = Dish() containing Ingredient.Potato and Ingredient.Cheese
        assertTrue(dishUnderTest is PotatoDish)
        assertTrue(dishUnderTest is PotatoWithCheese)
    }

    @Test
    fun `it should not be possible to create dish with just cheese` () {
        val dishUnderTest = Dish() containing Ingredient.Cheese
        assertTrue(dishUnderTest is NotADish)
    }




}

