package com.example.githubuser_.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.githubuser_.database.FavoriteDatabase
import com.example.githubuser_.repository.FavoriteRepository
import com.example.githubuser_.setting_preference.SettingPreference

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        context.apply {
            val database = FavoriteDatabase.getDatabase(context)
            val dao = database.favoriteDao()
            return FavoriteRepository.getInstance(dao)
        }

    }

    fun provideSettings(dataStore: DataStore<Preferences>): SettingPreference{
        return SettingPreference.getInstance(dataStore)
    }
}