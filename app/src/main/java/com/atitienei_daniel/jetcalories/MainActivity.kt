package com.atitienei_daniel.jetcalories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.atitienei_daniel.core.navigation.Route
import com.atitienei_daniel.jetcalories.navigation.navigate
import com.atitienei_daniel.jetcalories.ui.theme.JetCaloriesTheme
import com.atitienei_daniel.onboarding_presentation.welcome.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCaloriesTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Route.welcome) {
                    composable(Route.welcome) {
                        WelcomeScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.activity) {}
                    composable(Route.age) {}
                    composable(Route.height) {}
                    composable(Route.goal) {}
                    composable(Route.gender) {}
                    composable(Route.nutrientGoal) {}
                    composable(Route.search) {}
                    composable(Route.trackerOverview) {}
                    composable(Route.weight) {}
                }
            }
        }
    }
}