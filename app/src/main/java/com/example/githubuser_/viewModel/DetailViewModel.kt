package com.example.githubuser_.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser_.repository.Result
import com.example.githubuser_.repository.UserRepository
import com.example.githubuser_.response.DetailUserResponse
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _detailUser = MutableLiveData<DetailUserResponse?>()
    val detailUser: LiveData<DetailUserResponse?> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        var username = "KEY_DATA"
    }

    init {
        getDetailUser()
    }

    private fun getDetailUser() {
        viewModelScope.launch {
            userRepository.getDetailUser(username).observeForever { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.value = true
                    }

                    is Result.Success -> {
                        _isLoading.value = false
                        _detailUser.value = result.data
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
}
