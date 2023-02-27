package com.atitienei_daniel.tracker_domain.use_case

import com.atitienei_daniel.tracker_domain.model.TrackableFood
import com.atitienei_daniel.tracker_domain.repository.TrackerRepository

class SearchFood(
    private val repository: TrackerRepository
) {
    suspend fun execute(
        query: String,
        page: Int = 1,
        pageSize: Int = 40
    ): Result<List<TrackableFood>> {
        if (query.isBlank()) {
            return Result.success(emptyList())
        }
        return repository.searchFood(
            query = query.trim(),
            page = page,
            pageSize = pageSize
        )
    }
}