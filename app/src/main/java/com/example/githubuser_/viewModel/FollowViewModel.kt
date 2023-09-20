package com.example.githubuser_.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser_.response.ItemsItem
import com.example.githubuser_.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _listFollowers = MutableLiveData<List<ItemsItem>?>()
    val listFollowers: MutableLiveData<List<ItemsItem>?> = _listFollowers

    private val _listFollowing = MutableLiveData<List<ItemsItem>?>()
    val listFollowing: MutableLiveData<List<ItemsItem>?> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowersData(username: String) {
        _isLoading.value = true
        val config = ApiConfig.getApiService().getListFollowers(username)
        config.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowers.value = responseBody
                    } else {
                        Log.e(DetailViewModel.TAG, "onFailure : ${response.body()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.d(DetailViewModel.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowingData(username: String) {
        _isLoading.value = true
        val config = ApiConfig.getApiService().getListFollowing(username)
        config.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowing.value = responseBody
                    } else {
                        Log.e(DetailViewModel.TAG, "onFailure : ${response.body()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.d(DetailViewModel.TAG, "onFailure: ${t.message}")
            }
        })
    }
}