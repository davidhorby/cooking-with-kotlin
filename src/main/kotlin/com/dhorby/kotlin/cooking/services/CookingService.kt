package com.dhorby.kotlin.cooking.services

import com.dhorby.kotlin.cooking.domain.Dish
import com.dhorby.kotlin.cooking.domain.Ingredient
import com.dhorby.kotlin.cooking.domain.containing

class CookingService() {

    fun cook(ingredients: List<Ingredient>): Dish {
        val dish = ingredients.foldRight(Dish()) { ingredient: Ingredient, dish: Dish ->
            dish.containing(ingredient)
        }
        return dish
    }

}