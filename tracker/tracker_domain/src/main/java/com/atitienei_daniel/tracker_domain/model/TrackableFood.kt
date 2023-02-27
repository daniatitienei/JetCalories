package com.atitienei_daniel.tracker_domain.model

data class TrackableFood(
    val name: String,
    val imageUrl: String?,
    val caloriesPer100g: Int,
    val carbsPer100g: Int,
    val fatsPer100g: Int,
    val proteinPer100g: Int
)