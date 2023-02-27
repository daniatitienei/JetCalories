package com.atitienei_daniel.tracker_domain.use_case

data class TrackerUseCases(
    val searchFood: SearchFood,
    val deleteTrackedFood: DeleteTrackedFood,
    val trackFood: TrackFood,
    val getFoodsForDate: GetFoodsForDate,
    val calculateMealNutrients: CalculateMealNutrients
)