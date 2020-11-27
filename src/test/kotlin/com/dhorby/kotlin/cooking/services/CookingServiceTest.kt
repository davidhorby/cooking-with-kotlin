package com.dhorby.kotlin.cooking.services

import com.dhorby.kotlin.cooking.domain.Ingredient
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

internal class CookingServiceTest {

    @Test
    fun cook() {
        val dish = CookingService().cook(listOf(Ingredient.Potato, Ingredient.Cheese))
        assertThat(dish.ingredients , equalTo(setOf(Ingredient.Potato, Ingredient.Cheese)))
    }
}