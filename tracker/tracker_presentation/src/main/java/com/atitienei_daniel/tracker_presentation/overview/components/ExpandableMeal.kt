package com.atitienei_daniel.tracker_presentation.overview.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atitienei_daniel.core_ui.LocalSpacing
import com.atitienei_daniel.tracker_presentation.components.NutrientInfo
import com.atitienei_daniel.tracker_presentation.components.UnitDisplay
import com.atitienei_daniel.tracker_presentation.overview.Meal

@Composable
fun ExpandableMeal(
    meal: Meal,
    onToggleClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val spacing = LocalSpacing.current

    val arrowRotation by animateFloatAsState(
        targetValue = if (meal.isExpanded) {
            0f
        } else {
            180f
        }
    )

    Column(
        modifier = modifier.clickable { onToggleClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = meal.name, style = MaterialTheme.typography.titleLarge)
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier.rotate(arrowRotation)
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UnitDisplay(
                    amount = meal.calories,
                    unit = "kcal",
                    amountTextSize = 30.sp
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    NutrientInfo(name = "Carbs", amount = meal.carbs, unit = "g")
                    NutrientInfo(name = "Protein", amount = meal.protein, unit = "g")
                    NutrientInfo(name = "Fat", amount = meal.fat, unit = "g")
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(spacing.spaceMedium))

    AnimatedVisibility(visible = meal.isExpanded) {
        content()
    }
}