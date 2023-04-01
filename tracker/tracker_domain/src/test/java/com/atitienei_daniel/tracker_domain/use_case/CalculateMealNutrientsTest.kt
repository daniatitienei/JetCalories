package com.atitienei_daniel.tracker_domain.use_case

import com.atitienei_daniel.core.domain.data_store.UserDataStore
import com.atitienei_daniel.core.domain.model.ActivityLevel
import com.atitienei_daniel.core.domain.model.Gender
import com.atitienei_daniel.core.domain.model.GoalType
import com.atitienei_daniel.core.domain.model.UserInfo
import com.atitienei_daniel.tracker_domain.model.MealType
import com.atitienei_daniel.tracker_domain.model.TrackedFood
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientsTest {

    private lateinit var calculateMealNutrients: CalculateMealNutrients

    @Before
    fun setUp() = runBlocking {
        val userDataStore = mockk<UserDataStore>(relaxed = true)
        every { userDataStore.loadUserInfo() } returns flow {
            UserInfo(
                gender = Gender.Male,
                age = 20,
                weight = 80f,
                height = 180,
                activityLevel = ActivityLevel.Medium,
                goalType = GoalType.KeepWeight,
                carbRatio = 0.4f,
                proteinRatio = 0.3f,
                fatRatio = 0.3f
            )
        }

        calculateMealNutrients = CalculateMealNutrients(userDataStore)
    }

    @Test
    fun `Calories for breakfast properly calculated`() = runBlocking {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("breakfast", "lunch", "dinner", "snack").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }
        val result = calculateMealNutrients.execute(trackedFoods)
        val breakfastCalories = result.mealNutrients.values
            .filter { it.mealType == MealType.Breakfast }
            .sumOf { it.calories }
        val expectedCalories = trackedFoods
            .filter { it.mealType == MealType.Breakfast }
            .sumOf { it.calories }

        assertThat(breakfastCalories).isEqualTo(expectedCalories)
    }
}