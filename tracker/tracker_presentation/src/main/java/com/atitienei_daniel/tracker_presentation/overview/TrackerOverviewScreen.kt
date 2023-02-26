@file:OptIn(ExperimentalMaterial3Api::class)

package com.atitienei_daniel.tracker_presentation.overview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.atitienei_daniel.core.util.UiEvent
import com.atitienei_daniel.core_ui.LocalSpacing
import com.atitienei_daniel.tracker_presentation.overview.components.DaySelector
import com.atitienei_daniel.tracker_presentation.overview.components.ExpandableMeal
import com.atitienei_daniel.tracker_presentation.overview.components.NutrientsHeader
import com.atitienei_daniel.tracker_presentation.overview.components.TrackedFoodItem

@Composable
fun TrackerOverviewScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                bottom = innerPadding.calculateBottomPadding() + 15.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            item {
                NutrientsHeader(state = uiState)
            }
            item {
                DaySelector(
                    date = uiState.date,
                    onPreviousDayClick = {
                        viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick)
                    },
                    onNextDayClick = {
                        viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.spaceMedium)
                )
            }
            items(uiState.meals) { meal ->
                ExpandableMeal(
                    meal = meal,
                    onToggleClick = {
                        viewModel.onEvent(TrackerOverviewEvent.OnToggleMealClick(meal))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.spaceMedium)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.spaceSmall)
                    ) {
                        uiState.trackedFoods.forEach { trackedFood ->
                            TrackedFoodItem(
                                food = trackedFood,
                                onDeleteClick = {
                                    viewModel.onEvent(
                                        TrackerOverviewEvent.OnDeleteTrackedFoodClick(trackedFood)
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(spacing.spaceMedium))
                        }
                        OutlinedButton(
                            onClick = {
                                viewModel.onEvent(TrackerOverviewEvent.OnAddFoodClick(meal))
                            },
                            border = BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(spacing.spaceSmall))
                            Text(text = "Add ${meal.name}")
                        }
                    }
                }
            }
        }
    }

    // todo
}