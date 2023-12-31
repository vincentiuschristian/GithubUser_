package com.example.githubuser_.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)

    @Query("DELETE from favorite where username = :username")
    suspend fun delete(username: String)

    @Query("SELECT * from favorite where favorite = 1")
    fun getAllFavorite(): LiveData<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE username = :username)")
    fun isFavorite(username: String): Boolean

}