package com.atitienei_daniel.core.domain

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.atitienei_daniel.core.domain.data_store.UserDataStore
import com.atitienei_daniel.core.domain.model.ActivityLevel
import com.atitienei_daniel.core.domain.model.Gender
import com.atitienei_daniel.core.domain.model.GoalType
import com.atitienei_daniel.core.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DefaultUserDataStore(
    private val context: Context
) : UserDataStore {

    private val Context.dataStore by preferencesDataStore("user_info")

    override suspend fun saveGender(gender: Gender) {
        context.dataStore.edit { settings ->
            settings[UserDataStore.genderKey] = gender.name
        }
    }

    override suspend fun saveAge(age: Int) {
        context.dataStore.edit { settings ->
            settings[UserDataStore.ageKey] = age
        }
    }

    override suspend fun saveWeight(weight: Float) {
        context.dataStore.edit { settings ->
            settings[UserDataStore.weightKey] = weight
        }
    }

    override suspend fun saveHeight(height: Int) {
        context.dataStore.edit { settings ->
            settings[UserDataStore.heightKey] = height
        }
    }

    override suspend fun saveActivityLevel(level: ActivityLevel) {
        context.dataStore.edit { settings ->
            settings[UserDataStore.activityLevelKey] = level.name
        }
    }

    override suspend fun saveGoalType(type: GoalType) {
        context.dataStore.edit { settings ->
            settings[UserDataStore.goalTypeKey] = type.name
        }
    }

    override suspend fun saveCarbRatio(ratio: Float) {
        context.dataStore.edit { settings ->
            settings[UserDataStore.carbRatioKey] = ratio
        }
    }

    override suspend fun saveProteinRatio(ratio: Float) {
        context.dataStore.edit { settings ->
            settings[UserDataStore.proteinRatioKey] = ratio
        }
    }

    override suspend fun saveFatRatio(ratio: Float) {
        context.dataStore.edit { settings ->
            settings[UserDataStore.fatRatioKey] = ratio
        }
    }

    override fun loadUserInfo(): Flow<UserInfo> = context.dataStore.data
        .map { preferences ->
            UserInfo(
                age = preferences[UserDataStore.ageKey] ?: -1,
                gender = Gender.fromString(preferences[UserDataStore.genderKey] ?: "male"),
                activityLevel = ActivityLevel.fromString(
                    preferences[UserDataStore.activityLevelKey] ?: "medium"
                ),
                goalType = GoalType.fromString(
                    preferences[UserDataStore.goalTypeKey] ?: "keep_weight"
                ),
                carbRatio = preferences[UserDataStore.carbRatioKey] ?: -1f,
                fatRatio = preferences[UserDataStore.fatRatioKey] ?: -1f,
                height = preferences[UserDataStore.heightKey] ?: -1,
                proteinRatio = preferences[UserDataStore.proteinRatioKey] ?: -1f,
                weight = preferences[UserDataStore.weightKey] ?: -1f
            )
        }
}

