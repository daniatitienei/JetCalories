package com.atitienei_daniel.tracker_presentation.search

data class SearchState(
    val isSearching: Boolean = false,
    val query: String = "",
    val trackableFood: List<TrackableFoodUiState> = emptyList()
)