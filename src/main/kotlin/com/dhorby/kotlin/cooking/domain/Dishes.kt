package com.dhorby.kotlin.cooking.domain

import com.dhorby.kotlin.cooking.domain.BakedDish.*
import com.dhorby.kotlin.cooking.domain.PotatoDish.*
import com.dhorby.kotlin.cooking.domain.PotatoDish.SimplePotato

open class Dish(open val ingredients:Set<Ingredient>) {
    constructor() : this(mutableSetOf<Ingredient>())

    override fun toString(): String {
        return "${ingredients.joinToString(" and ")}"
    }
}

interface Baked

sealed class BakedDish() : Dish(), Baked {
    class BakedPotato : BakedDish()
    class BakedPotatoWithCheese : BakedDish()
    class BakedPotatoWithBeans : BakedDish()
    class BakedPotatoWithCheeseAndBeans : BakedDish()
}



sealed class PotatoDish : Dish() {
    class SimplePotato(override val ingredients:Set<Ingredient>) : PotatoDish()
    class PotatoWithCheese(override val ingredients:Set<Ingredient>) : PotatoDish()
    class PotatoWithBeans(override val ingredients:Set<Ingredient>) : PotatoDish()
    class PotatoWithCheeseAndBeans(override val ingredients:Set<Ingredient>) : PotatoDish()
}


class NotADish : Dish()
class NotABakedDish : BakedDish()


enum class Ingredient {
    Cheese, Beans, Avocado, Lettuce, Potato, Rice
}

enum class TemperatureScale {
    Fahrenheit, Centigrade
}

// Infix functions
infix fun Dish.containing(ingredient: Ingredient):Dish {
    return getNewDish(this, ingredient)
}

infix fun Dish.and(ingredient: Ingredient):Dish {
    return getNewDish(this, ingredient)
}

infix fun Dish.with(ingredient: Ingredient):Dish {
    return getNewDish(this, ingredient)
}

suspend infix fun Dish.degrees(temperatureScale: TemperatureScale):BakedDish {
    return when (this) {
        is SimplePotato -> BakedPotato()
        is PotatoWithCheese -> BakedPotatoWithCheese()
        is PotatoWithBeans -> BakedPotatoWithBeans()
        is PotatoWithCheeseAndBeans -> BakedPotatoWithCheeseAndBeans()
        else -> {
            NotABakedDish()
        }
    }
}

infix fun PotatoDish.degrees(temperatureScale: TemperatureScale):BakedDish {
    return when (this) {
        is PotatoDish -> BakedPotato()
        is PotatoWithCheese -> BakedPotatoWithCheese()
        is PotatoWithBeans -> BakedPotatoWithBeans()
        is PotatoWithCheeseAndBeans -> BakedPotatoWithCheeseAndBeans()
    }
}

infix fun Dish.`cook at`(temp: Int):Dish {
    return this
}

infix fun Dish.`minutes`(temp: Int):Dish {
    return this
}

infix fun Dish.`in the oven for`(timeInSeconds: Int):Dish {
    return this
}

private fun containsCheese(dish:Dish) {
    dish.ingredients.contains(Ingredient.Cheese)
}


private fun getNewDish(dish:Dish, ingredient: Ingredient): Dish {
    return when (ingredient) {
        Ingredient.Potato ->SimplePotato(dish.ingredients + ingredient)
        Ingredient.Cheese -> {
            when (dish) {
                is PotatoDish -> if (dish.ingredients.contains(Ingredient.Beans)) PotatoWithCheeseAndBeans(dish.ingredients)
                                 else PotatoWithCheese(dish.ingredients + ingredient)
                else -> {
                    NotADish()
                }
            }
        }
        Ingredient.Beans -> {
            when (dish) {
                is PotatoDish -> if (dish.ingredients.contains(Ingredient.Cheese)) PotatoWithCheeseAndBeans(dish.ingredients + ingredient)
                else PotatoWithBeans(dish.ingredients + ingredient)
                else -> {
                    NotADish()
                }
            }
        }
        Ingredient.Avocado -> TODO()
        Ingredient.Lettuce -> TODO()
        Ingredient.Rice -> TODO()
    }
}
