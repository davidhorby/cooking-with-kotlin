package com.dhorby.kotlin.cooking.domain


sealed class Dish
    sealed class PotatoDish(): Dish()
        class BakedPotatoWithCheese : PotatoDish()
        class BakedPotatoWithBeans() : PotatoDish()
        class BakedPotatoWithCheeseAndBeans() : PotatoDish()
    sealed class MexicanDish(): Dish()
        data class Burrito(val cheese:Cheese, val guacamole: Guacamole?, val salsa: Salsa? ): MexicanDish() {
            val lettuce:Lettuce = Lettuce()
            val rice:Rice = Rice()
        }
        class Fajita() : MexicanDish()
    object NotADish : Dish()


sealed class Cheese
    class MontereyJack(): Cheese()
    class Cheddar(): Cheese()
    class Oaxaca(): Cheese()

class Lettuce
class Rice
class Guacamole
class Salsa

