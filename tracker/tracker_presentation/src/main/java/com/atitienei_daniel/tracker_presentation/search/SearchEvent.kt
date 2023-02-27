package com.atitienei_daniel.tracker_presentation.search

import com.atitienei_daniel.tracker_domain.model.MealType
import com.atitienei_daniel.tracker_domain.model.TrackableFood
import java.time.LocalDate

sealed interface SearchEvent {
    data class OnQueryChange(val query: String) : SearchEvent
    object OnSearch : SearchEvent
    data class OnToggleTrackableFood(val food: TrackableFood) : SearchEvent
    data class OnAmountForFoodChange(
        val food: TrackableFood,
        val amount: String
    ) : SearchEvent

    data class OnTrackFoodClick(
        val food: TrackableFood,
        val mealType: MealType,
        val date: LocalDate
    ) : SearchEvent
}