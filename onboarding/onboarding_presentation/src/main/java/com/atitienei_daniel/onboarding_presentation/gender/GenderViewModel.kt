package com.atitienei_daniel.onboarding_presentation.gender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atitienei_daniel.core.domain.data_store.UserDataStore
import com.atitienei_daniel.core.domain.model.Gender
import com.atitienei_daniel.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val userDataStore: UserDataStore
) : ViewModel() {

    var selectedGender by mutableStateOf<Gender>(Gender.Male)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGenderClick(gender: Gender) {
        selectedGender = gender
    }

    fun onNextClick() {
        viewModelScope.launch {
            userDataStore.saveGender(gender = selectedGender)
            _uiEvent.send(UiEvent.Navigate)
        }
    }
}