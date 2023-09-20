package com.example.githubuser_.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: FavoriteUser)

    @Update
    fun update(favorite: FavoriteUser)

    @Delete
    fun delete(favorite: FavoriteUser)

    @Query("SELECT * from favoriteuser ORDER BY id ASC")
    fun getAllFavorite(): LiveData<List<FavoriteUser>>

}