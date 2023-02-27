package com.atitienei_daniel.onboarding_presentation.activity_level

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atitienei_daniel.core.domain.data_store.UserDataStore
import com.atitienei_daniel.core.domain.model.ActivityLevel
import com.atitienei_daniel.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityLevelViewModel @Inject constructor(
    private val userDataStore: UserDataStore
) : ViewModel() {

    var selectedGoalType by mutableStateOf<ActivityLevel>(ActivityLevel.Medium)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onActivityLevelClick(level: ActivityLevel) {
        selectedGoalType = level
    }

    fun onNextClick() {
        viewModelScope.launch {
            userDataStore.saveActivityLevel(level = selectedGoalType)
            _uiEvent.send(UiEvent.Navigate)

        }
    }
}