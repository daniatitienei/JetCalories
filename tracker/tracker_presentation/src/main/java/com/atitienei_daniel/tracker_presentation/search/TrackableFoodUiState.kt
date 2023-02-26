package com.atitienei_daniel.tracker_presentation.search

import com.atitienei_daniel.tracker_domain.model.TrackableFood

data class TrackableFoodUiState(
    val food: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String = ""
)