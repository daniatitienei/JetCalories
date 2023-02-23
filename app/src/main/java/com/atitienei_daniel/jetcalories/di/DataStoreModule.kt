package com.atitienei_daniel.jetcalories.di

import com.atitienei_daniel.core.domain.DefaultUserDataStore
import com.atitienei_daniel.core.domain.data_store.UserDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    @Singleton
    abstract fun bindsUserDataStore(
        defaultUserDataStore: DefaultUserDataStore
    ): UserDataStore
}