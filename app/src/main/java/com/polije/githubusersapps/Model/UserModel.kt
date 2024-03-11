package com.polije.githubusersapps.Model

import com.google.gson.annotations.SerializedName

data class UserModel(
    val login: String?,
    val id: Int?,
    val avatar_url: String?,
)
data class UserFollowingResponse(
    val items: List<UserModel>
)
data class UserFollowersResponse(
    val items: List<UserModel>
)
data class UserSearchResponse(
    val items: List<UserModel>
)

data class UserDetailResponse(
    val login: String,
    val avatarUrl: String,
    val name: String,
    val email: String,
    val followers: Int,
    val following: Int,
)