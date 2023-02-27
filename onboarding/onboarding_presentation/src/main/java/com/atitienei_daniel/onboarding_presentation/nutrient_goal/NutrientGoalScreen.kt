@file:OptIn(ExperimentalMaterial3Api::class)

package com.atitienei_daniel.onboarding_presentation.nutrient_goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.atitienei_daniel.core.util.UiEvent
import com.atitienei_daniel.core_ui.LocalSpacing
import com.atitienei_daniel.onboarding_presentation.components.UnitTextField

@Composable
fun NutrientGoalScreen(
    onNextClick: () -> Unit,
    viewModel: NutrientGoalViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNextClick()
                is UiEvent.ShowSnackBar -> snackBarHostState.showSnackbar(message = event.message)
                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
                Snackbar(snackbarData = it)
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Next") },
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.ArrowForward,
                        contentDescription = null
                    )
                },
                onClick = {
                    viewModel.onEvent(NutrientGoalEvent.OnNextClick)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "What are your nutrient goals?")

            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            UnitTextField(
                value = uiState.carbsRatio,
                onValueChange = {
                    viewModel.onEvent(NutrientGoalEvent.OnCarbsRatioEnter(it))
                },
                unit = "% carbs"
            )

            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            UnitTextField(
                value = uiState.proteinRatio,
                onValueChange = { viewModel.onEvent(NutrientGoalEvent.OnProteinRatioEnter(it)) },
                unit = "% proteins"
            )

            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            UnitTextField(
                value = uiState.fatRatio,
                onValueChange = { viewModel.onEvent(NutrientGoalEvent.OnFatRatioEnter(it)) },
                unit = "% fats"
            )
        }
    }
}