package com.example.githubuser_.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser_.response.ItemsItem
import com.example.githubuser_.response.UserResponse
import com.example.githubuser_.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _dataUser = MutableLiveData<List<ItemsItem>>()
    val dataUser: LiveData<List<ItemsItem>> = _dataUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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
    }

}