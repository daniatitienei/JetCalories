package com.atitienei_daniel.onboarding_presentation.nutrient_goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atitienei_daniel.core.domain.data_store.UserDataStore
import com.atitienei_daniel.core.navigation.Route
import com.atitienei_daniel.core.util.UiEvent
import com.atitienei_daniel.onboarding_domain.use_case.ValidateNutrients
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val validateNutrients: ValidateNutrients,
    private val userDataStore: UserDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutrientGoalState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.OnCarbsRatioEnter -> {
                _uiState.update { state ->
                    state.copy(
                        carbsRatio = event.ratio
                    )
                }
            }

            is NutrientGoalEvent.OnFatRatioEnter -> {
                _uiState.update { state ->
                    state.copy(
                        fatRatio = event.ratio
                    )
                }
            }

            is NutrientGoalEvent.OnProteinRatioEnter -> {
                _uiState.update { state ->
                    state.copy(
                        proteinRatio = event.ratio
                    )
                }
            }

            NutrientGoalEvent.OnNextClick -> {
                _uiState.value.let { screenState ->
                    val result = validateNutrients.execute(
                        fatRatioText = screenState.fatRatio,
                        carbsRatioText = screenState.carbsRatio,
                        proteinRatioText = screenState.proteinRatio
                    )

                    viewModelScope.launch {
                        when (result) {
                            is ValidateNutrients.Result.Error -> _uiEvent.send(
                                UiEvent.ShowSnackBar(
                                    message = result.message
                                )
                            )

                            is ValidateNutrients.Result.Success -> {
                                userDataStore.saveCarbRatio(result.carbsRatio)
                                userDataStore.saveProteinRatio(result.proteinRatio)
                                userDataStore.saveFatRatio(result.fatRatio)

                                viewModelScope.launch {
                                    _uiEvent.send(UiEvent.Navigate(route = Route.trackerOverview))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}