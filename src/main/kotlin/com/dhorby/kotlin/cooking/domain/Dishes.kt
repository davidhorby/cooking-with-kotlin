package com.dhorby.kotlin.cooking.domain

open class Dish {
    val ingredients = mutableSetOf<Ingredient>()

    override fun toString(): String {
        return "You have ordered a dish with ${ingredients.joinToString(" and ")}"
    }
}


sealed class PotatoDish : Dish()
    class BakedPotatoWithCheese : PotatoDish()
    class BakedPotatoWithBeans() : PotatoDish()
    class BakedPotatoWithCheeseAndBeans() : PotatoDish()

sealed class MexicanDish(): Dish()
    data class Burrito(val cheese:Cheese): MexicanDish() {
        val lettuce = Ingredient.Lettuce
        val rice = Ingredient.Rice
    }
    class Fajita() : MexicanDish()

object NotADish : Dish()
class RealPotatoDish:PotatoDish()


sealed class Cheese
    class MontereyJack(): Cheese()
    class Cheddar(): Cheese()
    class Oaxaca(): Cheese()


enum class Ingredient {
    Cheese, Beans, Avocado, Lettuce, Potato, Rice
}

// Infix functions
infix fun Dish.containing(ingredient: Ingredient):Dish {
    this.ingredients.add(ingredient)
    val result: Dish = when (ingredient) {
        Ingredient.Potato -> RealPotatoDish()
        Ingredient.Cheese -> TODO()
        Ingredient.Beans -> TODO()
        Ingredient.Avocado -> TODO()
        Ingredient.Lettuce -> TODO()
        Ingredient.Rice -> TODO()
    }
    return result
}
