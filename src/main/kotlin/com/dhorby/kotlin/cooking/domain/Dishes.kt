package com.dhorby.kotlin.cooking.domain

import com.dhorby.kotlin.cooking.domain.BakedDish.*
import com.dhorby.kotlin.cooking.domain.Ingredient.*
import com.dhorby.kotlin.cooking.domain.PotatoDish.*
import com.dhorby.kotlin.cooking.domain.PotatoDish.SimplePotato

open class Dish(open val ingredients:Set<Ingredient>) {
    constructor() : this(mutableSetOf<Ingredient>())

    override fun toString(): String {
        return "${ingredients.joinToString(" and ")}"
    }
}

interface Baked
interface Chopped
interface NotChoppable

sealed class Either<out A, out B> {
    class ChoppedIngredient<A>(val ingredient: Ingredient): Either<A, Nothing>()
    class NotChoppable<B>(): Either<Nothing, B>()
}

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

typealias Carrot = Ingredient
typealias Onion = Ingredient

sealed class ChoppedIngredient : Chopped {
    class ChoppedCarrots(ingredient: Carrot) : ChoppedIngredient()
    class ChoppedOnions(ingredient: Onion) : ChoppedIngredient()
}


class NotADish : Dish()
class NotABakedDish : BakedDish()


enum class Ingredient {
    Cheese, Beans, Avocado, Lettuce, Potato, Rice, Carrots, Onions
}

val allIngredients = listOf(Cheese, Beans, Avocado, Lettuce, Potato, Rice, Carrots, Onions)

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
    dish.ingredients.contains(Cheese)
}


private fun getNewDish(dish:Dish, ingredient: Ingredient): Dish {
    return when (ingredient) {
        Potato ->SimplePotato(dish.ingredients + ingredient)
        Cheese -> {
            when (dish) {
                is PotatoDish -> if (dish.ingredients.contains(Beans)) PotatoWithCheeseAndBeans(dish.ingredients)
                                 else PotatoWithCheese(dish.ingredients + ingredient)
                else -> {
                    NotADish()
                }
            }
        }
        Beans -> {
            when (dish) {
                is PotatoDish -> if (dish.ingredients.contains(Cheese)) PotatoWithCheeseAndBeans(dish.ingredients + ingredient)
                else PotatoWithBeans(dish.ingredients + ingredient)
                else -> {
                    NotADish()
                }
            }
        }
        Avocado -> TODO()
        Lettuce -> TODO()
        Rice -> TODO()
        Carrots -> TODO()
        Onions -> TODO()
    }
}
