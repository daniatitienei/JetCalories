package com.atitienei_daniel.core.util

sealed interface UiEvent {
    object Navigate : UiEvent
    object NavigateUp : UiEvent
    data class ShowSnackBar(val message: String) : UiEvent
}