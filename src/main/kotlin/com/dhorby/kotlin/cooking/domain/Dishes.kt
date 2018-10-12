package com.dhorby.kotlin.cooking.domain

open class Dish {
    val ingredients = mutableSetOf<Ingredient>()

    override fun toString(): String {
        return "You have ordered a dish with ${ingredients.joinToString(" and ")}"
    }
}


sealed class PotatoDish : Dish()
    open class SimplePotato : PotatoDish()
    open class PotatoWithCheese : PotatoDish()
    class PotatoWithBeans() : PotatoDish()
    class PotatoWithCheeseAndBeans() : PotatoDish()

sealed class MexicanDish(): Dish()
    data class Burrito(val cheese:Cheese): MexicanDish() {
        val lettuce = Ingredient.Lettuce
        val rice = Ingredient.Rice
    }
    class Fajita() : MexicanDish()

class NotADish : Dish()


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
    return getNewDish(this, ingredient)
}



//if (ingredients.contains(Ingredient.Potato)

infix fun Dish.and(ingredient: Ingredient):Dish {
    this.ingredients.add(ingredient)
    return getNewDish(this, ingredient)
}

private fun getNewDish(dish:Dish, ingredient: Ingredient): Dish {
    val result: Dish = when (ingredient) {
        Ingredient.Potato -> SimplePotato()
        Ingredient.Cheese -> {
            when (dish) {
                is PotatoDish -> PotatoWithCheese()
                else -> {
                    NotADish()
                }
            }
        }
        Ingredient.Beans -> TODO()
        Ingredient.Avocado -> TODO()
        Ingredient.Lettuce -> TODO()
        Ingredient.Rice -> TODO()
    }
    return result
}
