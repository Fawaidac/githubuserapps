package com.polije.githubusersapps.Db

import android.content.Context
import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
) : Parcelable

