package com.example.githubuser_.retrofit

import com.example.githubuser_.response.DetailUserResponse
import com.example.githubuser_.response.ItemsItem
import com.example.githubuser_.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getUser(
        @Query("q") users: String
    ): UserResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): DetailUserResponse

    @GET("users/{username}/followers")
    suspend fun getListFollowers(
        @Path("username") username: String
    ): List<ItemsItem>

    @GET("users/{username}/following")
    suspend fun getListFollowing(
        @Path("username") username: String
    ): List<ItemsItem>


}