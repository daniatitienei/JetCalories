package com.atitienei_daniel.onboarding_domain.di

import com.atitienei_daniel.onboarding_domain.use_case.ValidateNutrients
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object OnBoardingDomainModule {

    @Provides
    fun providesValidateNutrients(): ValidateNutrients = ValidateNutrients()
}