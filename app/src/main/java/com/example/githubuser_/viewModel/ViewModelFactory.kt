package com.example.githubuser_.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser_.di.Injection
import com.example.githubuser_.repository.UserRepository
import com.example.githubuser_.setting_preference.SettingPreference
import com.example.githubuser_.setting_preference.SettingViewModel
import com.example.githubuser_.setting_preference.dataStore

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val pref: SettingPreference,

    ) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {

        MainViewModel::class.java -> MainViewModel(userRepository)
        DetailViewModel::class.java -> DetailViewModel(userRepository)
        FollowViewModel::class.java -> FollowViewModel(userRepository)
        SettingViewModel::class.java -> SettingViewModel(pref)
        FavoriteViewModel::class.java -> FavoriteViewModel(userRepository)

        else -> throw IllegalArgumentException("Unknown ViewModel class : " + modelClass.name)
    } as T

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(application.applicationContext),
                    Injection.provideSettings(application.dataStore)
                )
            }.also { instance = it }


    }

}