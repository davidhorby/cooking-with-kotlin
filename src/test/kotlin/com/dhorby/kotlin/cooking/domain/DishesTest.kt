package com.dhorby.kotlin.cooking.domain

import com.dhorby.kotlin.cooking.domain.BakedDish.*
import com.dhorby.kotlin.cooking.domain.Ingredient.*
import com.dhorby.kotlin.cooking.domain.PotatoDish.PotatoWithCheese
import com.dhorby.kotlin.cooking.domain.PotatoDish.SimplePotato
import com.dhorby.kotlin.cooking.domain.TemperatureScale.Centigrade
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Dishes tests")
class DishesTest() {

    @ObsoleteCoroutinesApi
    val cookingFixedTheadPool = newFixedThreadPoolContext(5, "cooking")


    @Nested
    @DisplayName("Combinations")
    inner class Combinations {
        @Test
        fun `it should be possible to create a potato dish`() {
            val dishUnderTest = Dish() containing Potato
            assertTrue(dishUnderTest is PotatoDish)
            assertTrue(dishUnderTest is SimplePotato)
        }

        @Test
        fun `it should be possible to create a potato with cheese dish`() {
            val dishUnderTest = Dish() containing Potato and Cheese
            assertTrue(dishUnderTest is PotatoDish)
            assertTrue(dishUnderTest is PotatoWithCheese)
        }

        @Test
        fun `it should not be possible to create dish with just cheese`() {
            val dishUnderTest = Dish() containing Cheese
            assertTrue(dishUnderTest is NotADish)
        }
    }

    @Nested
    @DisplayName("Enums")
    inner class Enums {

        @Test
        fun `lookup and enum by names`() {
            val testName = "Cheese"
            val cheeseEnum = Ingredient.valueOf(testName)
            assertThat(cheeseEnum.name, equalTo("Cheese"))
        }

        @Test
        fun `should not find invalid enums`() {
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Ingredient.valueOf("Sofa")
            }
        }
    }

    @Nested
    @DisplayName("Transformations")
    inner class Transformations {
        @Test
        fun `it should be possible to bake a potato`() {
            val dish = Dish() containing Potato
            val cookedDish = bakeDish(dish)
            assertTrue(cookedDish is BakedPotato)
        }

        @Test
        fun `it should be possible to bake a potato with cheese`() {
            val dish = Dish() containing Potato and Cheese
            val cookedDish = bakeDish(dish)
            assertTrue(cookedDish is BakedPotatoWithCheese)
        }


        @Test
        fun `it should be possible to bake a potato with beans`() {
            val dish = Dish() containing Potato and Beans
            val cookedDish = bakeDish(dish)
            assertTrue(cookedDish is BakedPotatoWithBeans)
        }

        @Test
        fun `it should be possible to bake a potato with cheese and beans`() {
            val dish = Dish() containing Potato and Cheese and Beans
            val cookedDish = bakeDish(dish)
            assertTrue(cookedDish is BakedPotatoWithCheeseAndBeans, "The class is not BakedPotatoWithCheeseAndBeans but is ${cookedDish.javaClass}")
        }

        private fun bakeDish(dish: Dish): BakedDish {
            val cookedDish = runBlocking {
                withContext(cookingFixedTheadPool) {
                    dish `cook at` 200 degrees Centigrade
                }
            }
            return cookedDish
        }

    }

}

