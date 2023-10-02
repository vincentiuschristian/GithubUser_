package com.example.githubuser_.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.githubuser_.database.FavoriteDao
import com.example.githubuser_.database.UserEntity
import com.example.githubuser_.response.DetailUserResponse
import com.example.githubuser_.response.ItemsItem
import com.example.githubuser_.retrofit.ApiService

class UserRepository private constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
) {

    fun getUser(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val responses = apiService.getUser(username)
            val item = responses.items?.filterNotNull()
            if (item != null) {
                emit(Result.Success(item))
            } else {
                emit(Result.Error("Items list is null"))
            }
        } catch (e: Exception) {
            Log.d("User Repository", "getUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun getDetailUser(username: String): LiveData<Result<DetailUserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("User Repository", "getUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollower(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getListFollowers(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("User Repository Follower", "Follower: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowing(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getListFollowing(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("User Repository Following", "Following: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFavoriteUser(): LiveData<List<UserEntity>> {
        return favoriteDao.getAllFavorite()
    }

    suspend fun setFavorite(favorites: UserEntity, favoriteState: Boolean) {
        favorites.isFavorite = favoriteState
        favoriteDao.insert(favorites)
    }

    suspend fun deleteFavorite(username: String) {
        favoriteDao.delete(username)
    }

    companion object {
        var username = "KEY_DATA"

        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, favoriteDao)
            }.also { instance = it }
    }
}