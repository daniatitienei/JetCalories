@file:OptIn(ExperimentalMaterial3Api::class)

package com.atitienei_daniel.onboarding_presentation.gender

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.atitienei_daniel.core.domain.model.Gender
import com.atitienei_daniel.core.util.UiEvent
import com.atitienei_daniel.core_ui.LocalSpacing
import com.atitienei_daniel.onboarding_presentation.components.SelectableButton

@Composable
fun GenderScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: GenderViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    GenderScreenContent(
        selectedGender = viewModel.selectedGender,
        onNextClick = viewModel::onNextClick,
        onGenderClick = viewModel::onGenderClick
    )
}

@Composable
private fun GenderScreenContent(
    selectedGender: Gender,
    onNextClick: () -> Unit,
    onGenderClick: (Gender) -> Unit
) {
    val spacing = LocalSpacing.current

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
                    onNextClick()
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
            Text(text = "What is your gender?")
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
            ) {
                SelectableButton(
                    text = Gender.Male.name,
                    isSelected = selectedGender == Gender.Male,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    onClick = {
                        onGenderClick(Gender.Male)
                    }
                )

                SelectableButton(
                    text = Gender.Female.name,
                    isSelected = selectedGender == Gender.Female,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    onClick = {
                        onGenderClick(Gender.Female)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GenderScreenPreview() {
    GenderScreenContent(
        selectedGender = Gender.Male,
        onGenderClick = {},
        onNextClick = {}
    )
}