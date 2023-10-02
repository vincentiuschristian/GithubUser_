package com.example.githubuser_.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser_.repository.Result
import com.example.githubuser_.repository.UserRepository
import com.example.githubuser_.response.ItemsItem

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _dataUser = MutableLiveData<List<ItemsItem>?>()
    val dataUser: MutableLiveData<List<ItemsItem>?> = _dataUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getUser()
    }

    fun getUser(username: String = "vincent") {
        userRepository.getUser(username).observeForever { result ->
            when (result) {
                is Result.Loading -> {
                    _isLoading.value = true
                }

                is Result.Success -> {
                    _isLoading.value = false
                    _dataUser.value = result.data
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


//    fun getUser(username: String) {
//        userRepository.getUser(username).observe()
//    }

    /*
        init {
            getDataUser()
        }

        fun getDataUser(user: String = "vincent") {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getUser(user)
            client.enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful) {
                        _dataUser.value = responseBody?.items as List<ItemsItem>?
                    } else {
                        Log.e(TAG, "onFailure: ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }

        companion object {
            private const val TAG = "MainViewModel"
        }*/

}