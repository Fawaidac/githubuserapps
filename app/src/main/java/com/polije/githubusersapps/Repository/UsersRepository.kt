package com.polije.githubusersapps.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polije.githubusersapps.Model.*
import com.polije.githubusersapps.Services.ApiServices
import com.polije.githubusersapps.Services.RetrofitServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepository {
    private val _users = MutableLiveData<List<UserModel>>()
    val users: LiveData<List<UserModel>> = _users

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun updateUserList(newUserList: List<UserModel>) {
        _users.value = newUserList
    }

    private val _userDetail = MutableLiveData<UserDetailResponse>()
    val userDetail: LiveData<UserDetailResponse> = _userDetail

    fun updateUserDetail(newUserDetail: UserDetailResponse) {
        _userDetail.value = newUserDetail
    }

    fun fetchUsers() {
        _loading.value = true
        val services = RetrofitServices.buildSvc(ApiServices::class.java)
        val call = services.getUsers()

        call.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                _loading.value = false
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    userResponse?.let {
                        updateUserList(it)
                    }
                } else {
                    Log.e("fetchUsers", "Failed to fetch users: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                _loading.value = false
                t.printStackTrace()
                Log.e("fetchUsers", "Error fetching users: ${t.message}")
            }
        })

    }

    fun searchUsers(query: String) {
        _loading.value = true
        val services = RetrofitServices.buildSvc(ApiServices::class.java)
        val call = services.searchUsers(query)

        call.enqueue(object : Callback<UserSearchResponse> {
            override fun onResponse(call: Call<UserSearchResponse>, response: Response<UserSearchResponse>) {
                _loading.value = false
                if (response.isSuccessful) {
                    val userResponse = response.body()?.items
                    userResponse?.let {
                        updateUserList(it)
                    }
                } else {
                    Log.e("searchUsers", "Failed to search users: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                _loading.value = false
                t.printStackTrace()
                Log.e("searchUsers", "Error searching users: ${t.message}")
            }
        })
    }
    fun fetchUserFollowers(username: String) {
        _loading.value = true
        val services = RetrofitServices.buildSvc(ApiServices::class.java)
        val call = services.getUserFollowers(username)

        call.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                _loading.value = false
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    userResponse?.let {
                        updateUserList(it)
                    }
                } else {
                    Log.e("fetchUserFollowers", "Failed to fetch user followers: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                _loading.value = false
                t.printStackTrace()
                Log.e("fetchUserFollowers", "Error fetching user followers: ${t.message}")
            }
        })
    }

    fun fetchUserFollowing(username: String) {
        _loading.value = true
        val services = RetrofitServices.buildSvc(ApiServices::class.java)
        val call = services.getUserFollowing(username)

        call.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                _loading.value = false
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    userResponse?.let {
                        updateUserList(it)
                    }
                } else {
                    Log.e("fetchUserFollowing", "Failed to fetch user followers: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                _loading.value = false
                t.printStackTrace()
                Log.e("fetchUserFollowing", "Error fetching user followers: ${t.message}")
            }
        })
    }
    fun fetchUserDetail(username: String) {
        _loading.value = true
        val services = RetrofitServices.buildSvc(ApiServices::class.java)
        val call = services.getUserDetail(username)

        call.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(call: Call<UserDetailResponse>, response: Response<UserDetailResponse>) {
                _loading.value = false
                if (response.isSuccessful) {
                    val userDetail = response.body()
                    userDetail?.let {
                       updateUserDetail(it)
                    }
                } else {
                    Log.e("fetchUserDetail", "Failed to fetch user detail: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _loading.value = false
                t.printStackTrace()
                Log.e("fetchUserDetail", "Error fetching user detail: ${t.message}")
            }
        })
    }
}
