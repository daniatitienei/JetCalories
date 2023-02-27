package com.atitienei_daniel.onboarding_presentation.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atitienei_daniel.core.domain.data_store.UserDataStore
import com.atitienei_daniel.core.domain.use_case.FilterOutDigits
import com.atitienei_daniel.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val userDataStore: UserDataStore,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    var weight by mutableStateOf("70.0")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightValueChange(weight: String) {
        if (weight.length <= 5) {
            this.weight = filterOutDigits.execute(weight)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val weightNumber = weight.toFloatOrNull() ?: kotlin.run {
                _uiEvent.send(UiEvent.ShowSnackBar(message = "Weight can't be empty."))
                return@launch
            }

            userDataStore.saveWeight(weightNumber)
            _uiEvent.send(UiEvent.Navigate)
        }
    }
}