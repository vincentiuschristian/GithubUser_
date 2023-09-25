package com.example.githubuser_.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUserResponse(

    @field:SerializedName("bio")
    val bio: String?,

    @field:SerializedName("login")
    val login: String?,

    @field:SerializedName("public_repos")
    val publicRepos: Int?,

    @field:SerializedName("followers")
    val followers: Int?,

    @field:SerializedName("avatar_url")
    val avatarUrl: String?,

    @field:SerializedName("html_url")
    val htmlUrl: String?,

    @field:SerializedName("following")
    val following: Int?,

    @field:SerializedName("name")
    val name: String?,

) : Parcelable
