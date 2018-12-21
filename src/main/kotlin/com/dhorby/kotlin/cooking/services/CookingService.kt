package com.dhorby.kotlin.cooking.services

import com.dhorby.kotlin.cooking.domain.Dish
import com.dhorby.kotlin.cooking.domain.Ingredient
import com.dhorby.kotlin.cooking.domain.containing

class CookingService() {

    fun cook(ingredients: List<Ingredient>): Dish {
        return ingredients.fold(Dish()) { dish: Dish, ingredient: Ingredient ->
            dish.containing(ingredient)
        }
    }

}