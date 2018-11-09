package com.dhorby.kotlin.cooking.services

import com.dhorby.kotlin.cooking.domain.ChoppedIngredient
import com.dhorby.kotlin.cooking.domain.Either
import com.dhorby.kotlin.cooking.domain.Ingredient
import com.dhorby.kotlin.cooking.domain.NotChoppable


class ChoppingService() {

    fun chop(ingredient: Ingredient): Either<ChoppedIngredient, NotChoppable>  {

        return when(ingredient) {

            Ingredient.Cheese,
            Ingredient.Beans,
            Ingredient.Rice -> Either.NotChoppable()
            Ingredient.Avocado,
            Ingredient.Lettuce,
            Ingredient.Potato,
            Ingredient.Carrots,
            Ingredient.Onions -> Either.ChoppedIngredient(ingredient)
        }

    }
}