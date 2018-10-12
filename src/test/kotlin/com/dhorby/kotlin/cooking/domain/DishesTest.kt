package com.dhorby.kotlin.cooking.domain

import com.dhorby.kotlin.cooking.domain.BakedDish.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import com.dhorby.kotlin.cooking.domain.Ingredient.*
import com.dhorby.kotlin.cooking.domain.PotatoDish.PotatoWithCheese
import com.dhorby.kotlin.cooking.domain.PotatoDish.SimplePotato
import com.dhorby.kotlin.cooking.domain.TemperatureScale.*


@DisplayName("Dishes tests")
class DishesTest() {

    @Nested
    @DisplayName("Combinations")
    inner class Combinations {
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

    @Nested
    @DisplayName("Transformations")
    inner class Transformations {
        @Test
        fun `it should be possible to bake a potato`() {
            val cookedDish = Dish() containing Potato `cook at` 200 degrees Centigrade
            assertTrue(cookedDish is BakedPotato)
        }

        @Test
        fun `it should be possible to bake a potato with cheese`() {
            val dish = Dish() containing Potato and Cheese
            val cookedDish = dish `cook at` 200 degrees Centigrade
            assertTrue(cookedDish is BakedPotatoWithCheese)
        }

        @Test
        fun `it should be possible to bake a potato with beans`() {
            val dish = Dish() containing Potato and Beans
            val cookedDish = dish `cook at` 200 degrees Centigrade
            assertTrue(cookedDish is BakedPotatoWithBeans)
        }

        @Test
        fun `it should be possible to bake a potato with cheese and beans`() {
            val dish = Dish() containing Potato and Cheese and Beans
            val cookedDish = dish `cook at` 200 degrees Centigrade
            assertTrue(cookedDish is BakedDish.BakedPotatoWithCheeseAndBeans, "The class is not BakedPotatoWithCheeseAndBeans but is ${cookedDish.javaClass}")
        }

    }

}

