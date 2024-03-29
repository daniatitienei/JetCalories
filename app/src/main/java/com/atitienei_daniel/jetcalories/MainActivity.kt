package com.atitienei_daniel.jetcalories

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
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
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userDataStore: UserDataStore

    private lateinit var appUpdateManager: AppUpdateManager
    private var updateType = AppUpdateType.IMMEDIATE

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) {
            Log.d("Update result", "Update flow failed! Result code: $result.resultCode");
        }
    }

    val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            lifecycleScope.launch {
                delay(3.seconds)
                appUpdateManager.completeUpdate()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (updateType == AppUpdateType.FLEXIBLE) {
            appUpdateManager.unregisterListener(installStateUpdatedListener)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        if (updateType == AppUpdateType.FLEXIBLE) {
            appUpdateManager.registerListener(installStateUpdatedListener)
        }
        checkForUpdates()
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
                                navController.navigate(Route.goal)
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
                                navController.navigate(Route.height)
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (updateType == AppUpdateType.IMMEDIATE) {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        activityResultLauncher,
                        AppUpdateOptions.newBuilder(updateType).build()
                    )
                }
            }
        }
    }

    fun checkForUpdates() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            val isAvailable =
                appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isAllowed = when (updateType) {
                AppUpdateType.IMMEDIATE -> appUpdateInfo.isImmediateUpdateAllowed
                AppUpdateType.FLEXIBLE -> appUpdateInfo.isFlexibleUpdateAllowed
                else -> false
            }
            if (isAvailable && isAllowed) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    activityResultLauncher,
                    AppUpdateOptions.newBuilder(updateType).build()
                )
            }
        }
    }
}