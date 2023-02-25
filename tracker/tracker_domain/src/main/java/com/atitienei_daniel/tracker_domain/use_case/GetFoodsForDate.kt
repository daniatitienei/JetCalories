package com.atitienei_daniel.tracker_domain.use_case

import com.atitienei_daniel.tracker_domain.model.TrackedFood
import com.atitienei_daniel.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetFoodsForDate(
    private val repository: TrackerRepository
) {

    suspend fun execute(date: LocalDate): Flow<List<TrackedFood>> =
        repository.getFoodsForDate(date)
}