package com.atitienei_daniel.jetcalories.navigation

import androidx.navigation.NavController
import com.atitienei_daniel.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    navigate(event.route) {
        launchSingleTop = true
    }
}