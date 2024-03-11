package com.polije.githubusersapps.Services

import com.polije.githubusersapps.Model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("/users")
    fun getUsers(@Query("per_page") perPage: Int = 10): Call<List<UserModel>>

    @GET("/search/users")
    fun searchUsers(@Query("q") query: String): Call<UserSearchResponse>

    @GET("/users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetailResponse>

    @GET("/users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<UserModel>>

    @GET("/users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<UserModel>>
}

