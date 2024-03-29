package com.dhorby.kotlin.cooking

import com.dhorby.kotlin.cooking.domain.Dish
import com.dhorby.kotlin.cooking.domain.Ingredient.*
import com.dhorby.kotlin.cooking.domain.`cook at`
import com.dhorby.kotlin.cooking.domain.and
import com.dhorby.kotlin.cooking.domain.containing
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking


fun main(args: Array<String>) = runBlocking {
    val cookingFixedTheadPool = newFixedThreadPoolContext(5, "cooking")
    val dish: Dish = Dish() containing Potato and Cheese and Beans
    val dish1 = dish `cook at` 200
    println(dish)
    println(dish1)
}

