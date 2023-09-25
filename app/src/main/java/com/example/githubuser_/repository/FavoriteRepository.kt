package com.example.githubuser_.repository

import androidx.lifecycle.LiveData
import com.example.githubuser_.database.FavoriteDao
import com.example.githubuser_.database.UserEntity

class FavoriteRepository private constructor(
    private val favoriteDao: FavoriteDao,
) {

//    fun getUser(username: String): LiveData<Result<List<UserEntity>>> = liveData {
//        try {
//            emit(Result.Loading)
//            val response: DetailUserResponse = ApiConfig.getApiService().getDataUser(username)
//            val favorited = favoriteDao.isFavorite(username)
//            val data = UserEntity(
//                response.login,
//                response.avatarUrl,
//                favorited
//            )
//
//            favoriteDao.delete(username)
//            favoriteDao.insert(data)
//
//            val user: LiveData<Result<List<UserEntity>>> = favoriteDao.getDataUser(username).map { Result.Success(it) }
//            emitSource(user)
//        } catch (e: Exception){
//            Log.d("FavoriteRepository", "Get Detail User : ${e.message.toString()}")
//        }
//    }

    fun getFavoriteUser(): LiveData<List<UserEntity>> {
        return favoriteDao.getAllFavorite()
    }

    suspend fun setFavorite(favorite: UserEntity, favoriteState: Boolean) {
            favorite.isFavorite = favoriteState
            favoriteDao.insert(favorite)
    }

    suspend fun deleteFavorite(username: String) {
        favoriteDao.delete(username)
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            favoriteDao: FavoriteDao,
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(favoriteDao)
            }.also { instance = it }
    }
}