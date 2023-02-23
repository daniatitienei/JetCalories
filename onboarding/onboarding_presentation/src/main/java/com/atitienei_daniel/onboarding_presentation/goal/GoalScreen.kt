@file:OptIn(ExperimentalMaterial3Api::class)

package com.atitienei_daniel.onboarding_presentation.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.atitienei_daniel.core.domain.model.GoalType
import com.atitienei_daniel.core.util.UiEvent
import com.atitienei_daniel.core_ui.LocalSpacing
import com.atitienei_daniel.onboarding_presentation.components.SelectableButton

@Composable
fun GoalScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: GoalViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
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
                    viewModel.onNextClick()
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
            Text(text = "What's your activity level?")
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
            ) {
                SelectableButton(
                    text = GoalType.LoseWeight.name,
                    isSelected = viewModel.selectedGoalType is GoalType.LoseWeight,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    onClick = {
                        viewModel.onActivityLevelClick(GoalType.LoseWeight)
                    }
                )

                SelectableButton(
                    text = GoalType.KeepWeight.name,
                    isSelected = viewModel.selectedGoalType is GoalType.KeepWeight,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    onClick = {
                        viewModel.onActivityLevelClick(GoalType.KeepWeight)
                    }
                )

                SelectableButton(
                    text = GoalType.GainWeight.name,
                    isSelected = viewModel.selectedGoalType is GoalType.GainWeight,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    onClick = {
                        viewModel.onActivityLevelClick(GoalType.GainWeight)
                    }
                )
            }
        }
    }
}