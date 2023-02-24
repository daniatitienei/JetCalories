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
import com.atitienei_daniel.onboarding_presentation.activity_level.ActivityLevelScreen
import com.atitienei_daniel.onboarding_presentation.age.AgeScreen
import com.atitienei_daniel.onboarding_presentation.gender.GenderScreen
import com.atitienei_daniel.onboarding_presentation.goal.GoalScreen
import com.atitienei_daniel.onboarding_presentation.height.HeightScreen
import com.atitienei_daniel.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.atitienei_daniel.onboarding_presentation.weight.WeightScreen
import com.atitienei_daniel.onboarding_presentation.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                    composable(Route.activityLevel) {
                        ActivityLevelScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.age) {
                        AgeScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.height) {
                        HeightScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.goal) {
                        GoalScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.gender) {
                        GenderScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.nutrientGoal) {
                        NutrientGoalScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.search) {}
                    composable(Route.trackerOverview) {}
                    composable(Route.weight) {
                        WeightScreen(onNavigate = navController::navigate)
                    }
                }
            }
        }
    }
}