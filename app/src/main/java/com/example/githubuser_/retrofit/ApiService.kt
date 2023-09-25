package com.example.githubuser_.retrofit

import com.example.githubuser_.response.DetailUserResponse
import com.example.githubuser_.response.ItemsItem
import com.example.githubuser_.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") users: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getListFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getListFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>


}