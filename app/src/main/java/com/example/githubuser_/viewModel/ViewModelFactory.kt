package com.example.githubuser_.viewModel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser_.di.Injection
import com.example.githubuser_.repository.FavoriteRepository
import com.example.githubuser_.setting_preference.SettingPreference
import com.example.githubuser_.setting_preference.SettingViewModel

class ViewModelFactory private constructor(
    private val favoriteRepository: FavoriteRepository,
    private val pref: SettingPreference,

    ) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                return SettingViewModel(pref) as T
            }

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                return FavoriteViewModel(favoriteRepository) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class : " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context, userSetting: DataStore<Preferences>): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context), Injection.provideSettings(userSetting))
            }.also { instance = it }
    }

}