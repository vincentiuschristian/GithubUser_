package com.example.githubuser_.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser_.database.UserEntity
import com.example.githubuser_.repository.UserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFavoriteUser() = userRepository.getFavoriteUser()

    fun saveFavorite(favorite: UserEntity) {
        viewModelScope.launch {
            userRepository.setFavorite(favorite, true)
        }
    }

    fun deleteFavorite(username: String) {
        viewModelScope.launch {
            userRepository.deleteFavorite(username)
        }
    }

}