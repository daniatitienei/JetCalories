package com.atitienei_daniel.tracker_presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atitienei_daniel.core.domain.data_store.UserDataStore
import com.atitienei_daniel.core.navigation.Route
import com.atitienei_daniel.core.util.UiEvent
import com.atitienei_daniel.tracker_domain.use_case.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    private val userDataStore: UserDataStore,
    private val trackerUseCases: TrackerUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrackerOverviewState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getsFoodForDateJob: Job? = null

    init {
        viewModelScope.launch {
            userDataStore.saveShouldShowOnBoarding(false)
        }
    }

    fun onEvent(event: TrackerOverviewEvent) {
        when (event) {
            is TrackerOverviewEvent.OnAddFoodClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.Navigate(
                            route = Route.search
                                    + "/${event.meal.mealType.name}"
                                    + "/${_uiState.value.date.dayOfMonth}"
                                    + "/${_uiState.value.date.month}"
                                    + "/${_uiState.value.date.year}"
                        )
                    )
                }
            }

            TrackerOverviewEvent.OnNextDayClick -> {
                _uiState.update { state ->
                    state.copy(
                        date = state.date.plusDays(1)
                    )
                }
                refreshFoods()
            }

            is TrackerOverviewEvent.OnDeleteTrackedFoodClick -> {
                viewModelScope.launch {
                    trackerUseCases.deleteTrackedFood.execute(event.trackedFood)
                    refreshFoods()
                }
            }

            TrackerOverviewEvent.OnPreviousDayClick -> {
                _uiState.update { state ->
                    state.copy(
                        date = state.date.minusDays(1)
                    )
                }
                refreshFoods()
            }

            is TrackerOverviewEvent.OnToggleMealClick -> {
                _uiState.update { state ->
                    state.copy(
                        meals = state.meals
                            .map { currentMeal ->
                                if (currentMeal == event.meal) {
                                    currentMeal.copy(
                                        isExpanded = !currentMeal.isExpanded
                                    )
                                } else currentMeal
                            }
                    )
                }
            }
        }
    }

    private fun refreshFoods() {
        getsFoodForDateJob?.cancel()
        getsFoodForDateJob = viewModelScope.launch {
            trackerUseCases.getFoodsForDate.execute(_uiState.value.date)
                .onEach { foods ->
                    val nutrientsResult = trackerUseCases.calculateMealNutrients.execute(foods)
                    _uiState.update { state ->
                        state.copy(
                            totalCalories = nutrientsResult.totalCalories,
                            totalCarbs = nutrientsResult.totalCarbs,
                            totalFat = nutrientsResult.totalFat,
                            totalProtein = nutrientsResult.totalProtein,
                            proteinGoal = nutrientsResult.totalProtein,
                            caloriesGoal = nutrientsResult.caloriesGoal,
                            carbsGoal = nutrientsResult.carbsGoal,
                            fatGoal = nutrientsResult.fatGoal,
                            meals = state.meals.map {
                                val nutrientsForMeal =
                                    nutrientsResult.mealNutrients[it.mealType]
                                        ?: return@map it.copy(
                                            carbs = 0,
                                            protein = 0,
                                            fat = 0,
                                            calories = 0
                                        )

                                it.copy(
                                    carbs = nutrientsForMeal.carbs,
                                    protein = nutrientsForMeal.protein,
                                    fat = nutrientsForMeal.fat,
                                    calories = nutrientsForMeal.calories
                                )
                            }
                        )
                    }
                }.launchIn(viewModelScope)
        }
    }
}