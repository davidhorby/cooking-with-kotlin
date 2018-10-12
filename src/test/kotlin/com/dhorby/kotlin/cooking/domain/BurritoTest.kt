package com.dhorby.kotlin.cooking.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class BurritoTest() {

    @Test
    fun `it should be possible to create a potato dish`() {
        val dishUnderTest = Dish() containing Ingredient.Potato
        assertTrue(dishUnderTest is PotatoDish)
    }


}

