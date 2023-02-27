package com.atitienei_daniel.core.domain.data_store

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.atitienei_daniel.core.domain.model.ActivityLevel
import com.atitienei_daniel.core.domain.model.Gender
import com.atitienei_daniel.core.domain.model.GoalType
import com.atitienei_daniel.core.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserDataStore {

    suspend fun saveGender(gender: Gender)
    suspend fun saveAge(age: Int)
    suspend fun saveWeight(weight: Float)
    suspend fun saveHeight(height: Int)
    suspend fun saveActivityLevel(level: ActivityLevel)
    suspend fun saveGoalType(type: GoalType)
    suspend fun saveCarbRatio(ratio: Float)
    suspend fun saveProteinRatio(ratio: Float)
    suspend fun saveFatRatio(ratio: Float)

    fun loadUserInfo(): Flow<UserInfo>

    suspend fun saveShouldShowOnBoarding(shouldShow: Boolean)
    fun loadShouldShowOnBoarding(): Flow<Boolean>

    companion object {
        val shouldShowOnBoarding = booleanPreferencesKey("should_show")
        val genderKey = stringPreferencesKey("gender")
        val ageKey = intPreferencesKey("age")
        val weightKey = floatPreferencesKey("weight")
        val heightKey = intPreferencesKey("height")
        val activityLevelKey = stringPreferencesKey("activity_level")
        val goalTypeKey = stringPreferencesKey("goal_type")
        val carbRatioKey = floatPreferencesKey("carb_ratio")
        val proteinRatioKey = floatPreferencesKey("protein_ratio")
        val fatRatioKey = floatPreferencesKey("fat_ratio")
    }
}