package com.example.githubuser_.repository

import androidx.lifecycle.LiveData
import com.example.githubuser_.database.FavoriteDao
import com.example.githubuser_.database.UserEntity
import com.example.githubuser_.di.AppExecutors

class FavoriteRepository private constructor(
    private val favoriteDao: FavoriteDao,
    //private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val executorService: AppExecutors
) {

    fun getFavoriteUser(): LiveData<List<UserEntity>> {
        return favoriteDao.getAllFavorite()
    }

    fun setFavorite(favorite: UserEntity, favoriteState: Boolean) {
        executorService.networkIO.execute {
            favorite.isFavorite = favoriteState
            favoriteDao.insert(favorite)
        }
    }

    fun deleteFavorite(username: String) {
        executorService.diskIO.execute {
            favoriteDao.delete(username)
        }
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            favoriteDao: FavoriteDao,
            appExecutors: com.example.githubuser_.di.AppExecutors
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(favoriteDao, appExecutors)
            }.also { instance = it }
    }
}