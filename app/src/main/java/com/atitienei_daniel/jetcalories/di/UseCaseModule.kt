package com.atitienei_daniel.jetcalories.di

import com.atitienei_daniel.core.domain.use_case.FilterOutDigits
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesFilterOutDigits(): FilterOutDigits = FilterOutDigits()
}