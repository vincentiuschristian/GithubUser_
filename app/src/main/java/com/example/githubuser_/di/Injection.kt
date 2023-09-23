package com.example.githubuser_.di

import android.content.Context
import com.example.githubuser_.database.FavoriteDatabase
import com.example.githubuser_.repository.FavoriteRepository

object Injection {
    fun provideRepository(context: Context):FavoriteRepository{
        context.apply {
            val database = FavoriteDatabase.getDatabase(context)
            val appExecutors = AppExecutors()
            val dao = database.favoriteDao()
            return FavoriteRepository.getInstance(dao, appExecutors)
        }

    }
}