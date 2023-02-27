package com.atitienei_daniel.tracker_presentation.overview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.atitienei_daniel.core_ui.LocalSpacing
import com.atitienei_daniel.tracker_domain.model.TrackedFood
import com.atitienei_daniel.tracker_presentation.components.NutrientInfo

@Composable
fun TrackedFoodItem(
    food: TrackedFood,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val spacing = LocalSpacing.current

    Row(
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(food.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(100.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
        )
        Spacer(modifier = Modifier.width(spacing.spaceMedium))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = food.name,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            onDeleteClick()
                        }
                )

            }
            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            Text(text = "${food.amount}g - ${food.calories}kcal")
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                NutrientInfo(
                    name = "Carbs",
                    amount = food.carbs,
                    unit = "g",
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp
                )
                NutrientInfo(
                    name = "Protein",
                    amount = food.protein,
                    unit = "g",
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp
                )
                NutrientInfo(
                    name = "Fat",
                    amount = food.fat,
                    unit = "g",
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp
                )
            }
        }
    }
}