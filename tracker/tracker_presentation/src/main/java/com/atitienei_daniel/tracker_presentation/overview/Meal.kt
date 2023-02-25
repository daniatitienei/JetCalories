package com.atitienei_daniel.tracker_presentation.overview

import com.atitienei_daniel.tracker_domain.model.MealType

data class Meal(
    val name: String,
    val mealType: MealType,
    val carbs: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val calories: Int = 0,
    val isExpanded: Boolean = false
)

val defaultMeals = listOf(
    Meal(
        name = "Breakfast",
        mealType = MealType.BreakFast
    ),
    Meal(
        name = "Lunch",
        mealType = MealType.Lunch
    ),
    Meal(
        name = "Dinner",
        mealType = MealType.Dinner
    ),
    Meal(
        name = "Snack",
        mealType = MealType.Snack
    )
)

