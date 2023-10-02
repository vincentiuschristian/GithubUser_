package com.example.githubuser_.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.githubuser_.database.FavoriteDatabase
import com.example.githubuser_.repository.UserRepository
import com.example.githubuser_.retrofit.ApiConfig
import com.example.githubuser_.setting_preference.SettingPreference

object Injection {
    fun provideRepository(context: Context): UserRepository {
        context.apply {
            val database = FavoriteDatabase.getDatabase(context)
            val dao = database.favoriteDao()
            val apiService = ApiConfig.getApiService()
            return UserRepository.getInstance(apiService, dao)
        }
    }

    fun provideSettings(dataStore: DataStore<Preferences>): SettingPreference{
        return SettingPreference.getInstance(dataStore)
    }
}