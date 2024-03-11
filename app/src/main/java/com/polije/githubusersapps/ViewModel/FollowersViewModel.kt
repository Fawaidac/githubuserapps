package com.polije.githubusersapps.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.polije.githubusersapps.Model.UserDetailResponse
import com.polije.githubusersapps.Repository.UsersRepository

class FollowersViewModel : ViewModel() {
    private val usersRepository = UsersRepository()

    val users = usersRepository.users
    val loading = usersRepository.loading

    fun fetchUserFollowers(username: String) {
        usersRepository.fetchUserFollowers(username)
    }

}