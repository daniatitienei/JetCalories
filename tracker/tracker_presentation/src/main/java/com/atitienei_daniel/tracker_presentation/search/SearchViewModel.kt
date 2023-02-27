package com.atitienei_daniel.tracker_presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atitienei_daniel.core.domain.use_case.FilterOutDigits
import com.atitienei_daniel.core.util.UiEvent
import com.atitienei_daniel.tracker_domain.use_case.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChange -> {
                _uiState.update { state ->
                    state.copy(
                        query = event.query,
                    )
                }
            }

            is SearchEvent.OnAmountForFoodChange -> {
                _uiState.update { state ->
                    state.copy(
                        trackableFood = state.trackableFood.map {
                            if (it.food == event.food) {
                                it.copy(amount = filterOutDigits.execute(event.amount))
                            } else it
                        }
                    )
                }
            }

            is SearchEvent.OnSearch -> {
                executeSearch()
            }

            is SearchEvent.OnToggleTrackableFood -> {
                _uiState.update { state ->
                    state.copy(
                        trackableFood = state.trackableFood.map {
                            if (it.food == event.food) {
                                it.copy(isExpanded = !it.isExpanded)
                            } else it
                        }
                    )
                }
            }

            is SearchEvent.OnTrackFoodClick -> {
                trackFood(event)
            }
        }
    }

    private fun executeSearch() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    trackableFood = emptyList(),
                    isSearching = true
                )
            }
            trackerUseCases
                .searchFood.execute(state.query)
                .onSuccess { foods ->
                    _uiState.update { state ->
                        state.copy(
                            trackableFood = foods.map {
                                TrackableFoodUiState(it)
                            },
                            isSearching = false,
                        )
                    }
                }
                .onFailure {
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            message = "An error occurred"
                        )
                    )
                }
        }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodClick) {
        viewModelScope.launch {
            val uiState = _uiState.value.trackableFood.find { it.food.name == event.food.name }
            trackerUseCases.trackFood.execute(
                food = uiState?.food ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }
}
