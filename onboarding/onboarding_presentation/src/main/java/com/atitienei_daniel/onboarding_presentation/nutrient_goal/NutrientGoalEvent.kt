package com.atitienei_daniel.onboarding_presentation.nutrient_goal

sealed interface NutrientGoalEvent {
    data class OnFatRatioEnter(val ratio: String) : NutrientGoalEvent
    data class OnCarbsRatioEnter(val ratio: String) : NutrientGoalEvent
    data class OnProteinRatioEnter(val ratio: String) : NutrientGoalEvent

    object OnNextClick : NutrientGoalEvent
}