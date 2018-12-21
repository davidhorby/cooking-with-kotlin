package com.dhorby.kotlin.cooking.services

import com.dhorby.kotlin.cooking.domain.Ingredient
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.should.shouldMatch
import org.junit.jupiter.api.Test

internal class CookingServiceTest {

    @Test
    fun cook() {
        val dish = CookingService().cook(listOf(Ingredient.Potato, Ingredient.Cheese))
        dish.ingredients shouldMatch equalTo(setOf(Ingredient.Potato, Ingredient.Cheese))
    }
}