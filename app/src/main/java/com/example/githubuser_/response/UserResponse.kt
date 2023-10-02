package com.example.githubuser_.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val items: List<ItemsItem?>? = null
) : Parcelable

@Parcelize
data class ItemsItem(

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
