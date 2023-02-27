package com.atitienei_daniel.jetcalories

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.atitienei_daniel.core.domain.data_store.UserDataStore
import com.atitienei_daniel.core.navigation.Route
import com.atitienei_daniel.jetcalories.ui.theme.JetCaloriesTheme
import com.atitienei_daniel.onboarding_presentation.activity_level.ActivityLevelScreen
import com.atitienei_daniel.onboarding_presentation.age.AgeScreen
import com.atitienei_daniel.onboarding_presentation.gender.GenderScreen
import com.atitienei_daniel.onboarding_presentation.goal.GoalScreen
import com.atitienei_daniel.onboarding_presentation.height.HeightScreen
import com.atitienei_daniel.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.atitienei_daniel.onboarding_presentation.weight.WeightScreen
import com.atitienei_daniel.onboarding_presentation.welcome.WelcomeScreen
import com.atitienei_daniel.tracker_presentation.overview.TrackerOverviewScreen
import com.atitienei_daniel.tracker_presentation.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userDataStore: UserDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val shouldShowOnboarding by
            userDataStore.loadShouldShowOnBoarding().collectAsState(initial = true)

            JetCaloriesTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = if (shouldShowOnboarding) Route.welcome else Route.trackerOverview
                ) {
                    composable(Route.welcome) {
                        WelcomeScreen(
                            onNextClick = {
                                navController.navigate(Route.gender)
                            }
                        )
                    }
                    composable(Route.activityLevel) {
                        ActivityLevelScreen(
                            onNextClick = {
                                navController.navigate(Route.goal)
                            }
                        )
                    }
                    composable(Route.age) {
                        AgeScreen(
                            onNextClick = {
                                navController.navigate(Route.height)
                            }
                        )
                    }
                    composable(Route.height) {
                        HeightScreen(
                            onNextClick = {
                                navController.navigate(Route.weight)
                            }
                        )
                    }
                    composable(Route.goal) {
                        GoalScreen(
                            onNextClick = {
                                navController.navigate(Route.nutrientGoal)
                            }
                        )
                    }
                    composable(Route.gender) {
                        GenderScreen(
                            onNextClick = {
                                navController.navigate(Route.age)
                            }
                        )
                    }
                    composable(Route.nutrientGoal) {
                        NutrientGoalScreen(
                            onNextClick = {
                                navController.navigate(Route.trackerOverview)
                            }
                        )
                    }
                    composable(
                        route = Route.search,
                        arguments = listOf(
                            navArgument("mealName") {
                                type = NavType.StringType
                            },
                            navArgument("dayOfMonth") {
                                type = NavType.IntType
                            },
                            navArgument("month") {
                                type = NavType.IntType
                            },
                            navArgument("year") {
                                type = NavType.IntType
                            },
                        )
                    ) { backStackEntry ->
                        val mealName = backStackEntry.arguments?.getString("mealName")!!
                        val dayOfMonth = backStackEntry.arguments?.getInt("dayOfMonth")!!
                        val month = backStackEntry.arguments?.getInt("month")!!
                        val year = backStackEntry.arguments?.getInt("year")!!

                        Log.d(
                            "searchTerms",
                            "dayOfMonth: $dayOfMonth\nmonth: $month\nyear: $year\nmealName: $mealName"
                        )

                        SearchScreen(
                            mealName = mealName,
                            dayOfMonth = dayOfMonth,
                            month = month,
                            year = year,
                            onNavigateUp = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable(Route.trackerOverview) {
                        TrackerOverviewScreen(
                            onNavigateToSearch = { mealName, dayOfMonth, month, year ->
                                navController.navigate(
                                    Route.search
                                        .replace("{mealName}", mealName)
                                        .replace("{dayOfMonth}", dayOfMonth.toString())
                                        .replace("{month}", month.toString())
                                        .replace("{year}", year.toString())
                                )
                            }
                        )
                    }
                    composable(Route.weight) {
                        WeightScreen(
                            onNextClick = {
                                navController.navigate(Route.activityLevel)
                            }
                        )
                    }
                }
            }
        }
    }
}