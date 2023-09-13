package com.example.githubuser_.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser_.response.DetailUserResponse
import com.example.githubuser_.response.ItemsItem
import com.example.githubuser_.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    companion object {
        var username = "KEY_DATA"
        const val TAG = "DetailUserActivity"
    }

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _listFollowers = MutableLiveData<List<ItemsItem>?>()
    val listFollowers: MutableLiveData<List<ItemsItem>?> = _listFollowers

    private val _listFollowing = MutableLiveData<List<ItemsItem>?>()
    val listFollowing: MutableLiveData<List<ItemsItem>?> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getDetailUser()
    }

    private fun getDetailUser() {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getDetailUser(username)
            client.enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _detailUser.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure : ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
        }

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
                        Log.e(TAG, "onFailure : ${response.body()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
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
                        Log.e(TAG, "onFailure : ${response.body()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    }
