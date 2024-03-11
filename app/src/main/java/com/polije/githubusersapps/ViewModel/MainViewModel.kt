package com.polije.githubusersapps.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.polije.githubusersapps.Model.UserDetailResponse
import com.polije.githubusersapps.Repository.UsersRepository

class MainViewModel : ViewModel() {
    private val usersRepository = UsersRepository()

    val users = usersRepository.users
    val loading = usersRepository.loading
    val userDetail: LiveData<UserDetailResponse> = usersRepository.userDetail

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        usersRepository.fetchUsers()
    }

    fun searchUsers(query: String) {
        usersRepository.searchUsers(query)
    }

    fun getDetailUser(username: String){
        usersRepository.fetchUserDetail(username)
    }
}