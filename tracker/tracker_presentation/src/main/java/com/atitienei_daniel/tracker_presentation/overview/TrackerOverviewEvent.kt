package com.atitienei_daniel.tracker_presentation.overview

import com.atitienei_daniel.tracker_domain.model.TrackedFood

sealed interface TrackerOverviewEvent {
    object OnNextDayClick: TrackerOverviewEvent
    object OnPreviousDayClick: TrackerOverviewEvent
    data class OnToggleMealClick(val meal: Meal): TrackerOverviewEvent
    data class OnDeleteTrackedFoodClick(val trackedFood: TrackedFood): TrackerOverviewEvent
    data class OnAddFoodClick(val meal: Meal): TrackerOverviewEvent
}