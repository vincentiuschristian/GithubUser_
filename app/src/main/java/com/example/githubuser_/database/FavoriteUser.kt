package com.example.githubuser_.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUser (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "username")
    var username: String? = "",

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = "",

    @ColumnInfo(name = "follower")
    var follower: String? = "",

    @ColumnInfo(name = "following")
    var following: String? = "",

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean

): Parcelable