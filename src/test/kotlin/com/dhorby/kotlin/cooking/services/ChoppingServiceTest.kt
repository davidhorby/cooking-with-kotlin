package com.dhorby.kotlin.cooking.services

import com.dhorby.kotlin.cooking.domain.Either
import com.dhorby.kotlin.cooking.domain.Ingredient
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@DisplayName("Chopping Service tests")
internal class ChoppingServiceTest {

    @Test
    fun `it should not be possible to chop beans` () {
        val chopResult = ChoppingService().chop(Ingredient.Beans)
        assertTrue(chopResult is Either.NotChoppable)
    }

    @Test
    fun `it should be possible to chop carrots` () {
        val chopResult = ChoppingService().chop(Ingredient.Carrots)
        assertTrue(chopResult is Either.ChoppedIngredient)
    }
}