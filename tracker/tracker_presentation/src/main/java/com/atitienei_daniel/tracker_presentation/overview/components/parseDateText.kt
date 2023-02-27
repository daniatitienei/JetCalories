package com.atitienei_daniel.tracker_presentation.overview.components

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun parseDateText(date: LocalDate): String {
    val today = LocalDate.now()
    return when (date) {
        today -> "Today"
        today.minusDays(1) -> "Yesterday"
        today.plusDays(1) -> "Tomorrow"
        else -> DateTimeFormatter.ofPattern("dd LLLL").format(date)
    }
}