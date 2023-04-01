package com.atitienei_daniel.tracker_domain.model

sealed class MealType(val name: String) {
    object Breakfast : MealType(name = "breakfast")
    object Lunch : MealType(name = "lunch")
    object Dinner : MealType(name = "dinner")
    object Snack : MealType(name = "snack")

    companion object {
        fun fromString(name: String): MealType =
            when (name.lowercase()) {
                "breakfast" -> Breakfast
                "lunch" -> Lunch
                "dinner" -> Dinner
                "snack" -> Snack
                else -> Breakfast
            }
    }
}
