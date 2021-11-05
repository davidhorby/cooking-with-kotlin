package com.dhorby.kotlin.cooking.domain

import com.dhorby.kotlin.cooking.domain.BakedDish.*
import com.dhorby.kotlin.cooking.domain.Ingredient.*
import com.dhorby.kotlin.cooking.domain.PotatoDish.*

open class Dish(open val ingredients: Set<Ingredient>) {
    constructor() : this(mutableSetOf<Ingredient>())

    open val dishName:String = "Basic dish"

    override fun toString(): String {
        return "${ingredients.joinToString(" and ")}"
    }

    class Builder {

    }
}

data class DishName(val value: String)
data class Message(val message: String)

interface Baked
interface Chopped
interface NotChoppable

sealed class Either<out A, out B> {
    class ChoppedIngredient<A>(val ingredient: Ingredient) : Either<A, Nothing>()
    class NotChoppable<B>() : Either<Nothing, B>()
}

sealed class BakedDish() : Dish(), Baked {
    class BakedPotato : BakedDish() {
        override val dishName: String = "BakedPotato"
    }
    class BakedPotatoWithCheese : BakedDish(){
        override val dishName: String = "BakedPotatoWithCheese"
    }
    class BakedPotatoWithBeans : BakedDish(){
        override val dishName: String = "BakedPotatoWithBeans"
    }
    class BakedPotatoWithCheeseAndBeans : BakedDish() {
        override val dishName: String = "BakedPotatoWithCheeseAndBeans"
    }
}



sealed class PotatoDish : Dish() {
    class SimplePotato(override val ingredients:Set<Ingredient>) : PotatoDish() {
        override val dishName: String = "SimplePotato"
    }
    class PotatoWithCheese(override val ingredients:Set<Ingredient>) : PotatoDish() {
        override val dishName: String = "PotatoWithCheese"
    }
    class PotatoWithBeans(override val ingredients:Set<Ingredient>) : PotatoDish() {
        override val dishName: String = "PotatoWithBeans"
    }
    class PotatoWithCheeseAndBeans(override val ingredients:Set<Ingredient>) : PotatoDish() {
        override val dishName: String = "votatoWithCheeseAndBeans"
    }
}

typealias Carrot = Ingredient
typealias Onion = Ingredient

sealed class ChoppedIngredient : Chopped {
    class ChoppedCarrots(ingredient: Carrot) : ChoppedIngredient()
    class ChoppedOnions(ingredient: Onion) : ChoppedIngredient()
}


class NotADish : Dish()  {
    override val dishName: String = "NotADish"
}
class NotABakedDish : BakedDish()  {
    override val dishName: String = "NotABakedDish"
}


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
