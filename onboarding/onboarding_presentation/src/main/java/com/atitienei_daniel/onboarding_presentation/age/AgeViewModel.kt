package com.atitienei_daniel.onboarding_presentation.age

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
class AgeViewModel @Inject constructor(
    private val userDataStore: UserDataStore,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    var age by mutableStateOf("18")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeValueChange(age: String) {
        if (age.length <= 3) {
            this.age = filterOutDigits.execute(age)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val ageNumber = age.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(UiEvent.ShowSnackBar(message = "Age can't be empty."))
                return@launch
            }

            userDataStore.saveAge(ageNumber)
            _uiEvent.send(UiEvent.Navigate)

        }
    }
}