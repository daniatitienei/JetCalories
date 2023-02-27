package com.atitienei_daniel.onboarding_presentation.height

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
class HeightViewModel @Inject constructor(
    private val userDataStore: UserDataStore,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    var height by mutableStateOf("170")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHeightValueChange(height: String) {
        if (height.length <= 3) {
            this.height = filterOutDigits.execute(height)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val heightNumber = height.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(UiEvent.ShowSnackBar(message = "Height can't be empty."))
                return@launch
            }

            userDataStore.saveHeight(heightNumber)
            _uiEvent.send(UiEvent.Navigate)
        }
    }
}