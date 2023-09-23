package com.example.githubuser_.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser_.database.UserEntity
import com.example.githubuser_.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository): ViewModel() {

    fun getFavoriteUser() = favoriteRepository.getFavoriteUser()

    fun saveFavorite(favorite: UserEntity){
        viewModelScope.launch {
            favoriteRepository.setFavorite(favorite, true)
        }
    }

    fun deleteFavorite(username: String){
        viewModelScope.launch {
            favoriteRepository.deleteFavorite(username)
        }
    }

}