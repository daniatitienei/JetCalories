package com.atitienei_daniel.tracker_domain.di

import com.atitienei_daniel.core.domain.data_store.UserDataStore
import com.atitienei_daniel.tracker_domain.repository.TrackerRepository
import com.atitienei_daniel.tracker_domain.use_case.CalculateMealNutrients
import com.atitienei_daniel.tracker_domain.use_case.DeleteTrackedFood
import com.atitienei_daniel.tracker_domain.use_case.GetFoodsForDate
import com.atitienei_daniel.tracker_domain.use_case.SearchFood
import com.atitienei_daniel.tracker_domain.use_case.TrackFood
import com.atitienei_daniel.tracker_domain.use_case.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @ViewModelScoped
    @Provides
    fun providesTrackerUseCases(
        repository: TrackerRepository,
        dataStore: UserDataStore
    ): TrackerUseCases =
        TrackerUseCases(
            searchFood = SearchFood(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            trackFood = TrackFood(repository),
            getFoodsForDate = GetFoodsForDate(repository),
            calculateMealNutrients = CalculateMealNutrients()
        )
}