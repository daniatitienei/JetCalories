package com.atitienei_daniel.tracker_data.mapper

import com.atitienei_daniel.tracker_data.local.entity.TrackedFoodEntity
import com.atitienei_daniel.tracker_domain.model.MealType
import com.atitienei_daniel.tracker_domain.model.TrackedFood
import java.time.LocalDate

fun TrackedFoodEntity.toTrackedFood(): TrackedFood =
    TrackedFood(
        id = id,
        name = name,
        calories = calories,
        imageUrl = imageUrl,
        carbs = carbs,
        protein = protein,
        fat = fat,
        amount = amount,
        date = LocalDate.of(year, month, dayOfMonth),
        mealType = MealType.fromString(type)
    )

fun TrackedFood.toEntity(): TrackedFoodEntity =
    TrackedFoodEntity(
        id = id,
        name = name,
        calories = calories,
        imageUrl = imageUrl,
        carbs = carbs,
        protein = protein,
        fat = fat,
        amount = amount,
        dayOfMonth = date.dayOfMonth,
        year = date.year,
        month = date.monthValue,
        type = mealType.name
    )