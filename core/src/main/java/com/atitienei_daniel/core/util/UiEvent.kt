package com.atitienei_daniel.core.util

sealed interface UiEvent {
    data class Navigate(val route: String) : UiEvent
    object NavigateUp : UiEvent
    data class ShowSnackBar(val message: String) : UiEvent
}