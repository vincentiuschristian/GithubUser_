package com.example.githubuser_.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser_.repository.Result
import com.example.githubuser_.repository.UserRepository
import com.example.githubuser_.response.ItemsItem

class FollowViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _listFollowers = MutableLiveData<List<ItemsItem>?>()
    val listFollowers: MutableLiveData<List<ItemsItem>?> = _listFollowers

    private val _listFollowing = MutableLiveData<List<ItemsItem>?>()
    val listFollowing: MutableLiveData<List<ItemsItem>?> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollower(username: String) {
        userRepository.getFollower(username).observeForever { result ->
            when (result) {
                is Result.Loading -> {
                    _isLoading.value = true
                }

                is Result.Success -> {
                    _isLoading.value = false
                    _listFollowers.value = result.data
                }

                is Result.Error -> {
                    _isLoading.value = false
                }

                is Result.Empty -> {
                    _isLoading.value = false
                }
            }
        }
    }

    fun getFollowing(username: String) {
        userRepository.getFollowing(username).observeForever { result ->
            when (result) {
                is Result.Loading -> {
                    _isLoading.value = true
                }

                is Result.Success -> {
                    _isLoading.value = false
                    _listFollowing.value = result.data
                }

                is Result.Error -> {
                    _isLoading.value = false
                }

                is Result.Empty -> {
                    _isLoading.value = false
                }
            }
        }
    }
}