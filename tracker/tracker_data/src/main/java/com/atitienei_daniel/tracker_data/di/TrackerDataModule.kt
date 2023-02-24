package com.atitienei_daniel.tracker_data.di

import android.content.Context
import androidx.room.Room
import com.atitienei_daniel.tracker_data.local.TrackerDatabase
import com.atitienei_daniel.tracker_data.remote.OpenFoodApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()

    @Provides
    @Singleton
    fun providesOpenFoodApi(client: OkHttpClient): OpenFoodApi =
        Retrofit.Builder()
            .baseUrl(OpenFoodApi.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(OpenFoodApi::class.java)

    @Provides
    @Singleton
    fun providesTrackerDatabase(@ApplicationContext context: Context): TrackerDatabase =
        Room.databaseBuilder(
            context,
            TrackerDatabase::class.java,
            "tracker_db"
        ).build()
}