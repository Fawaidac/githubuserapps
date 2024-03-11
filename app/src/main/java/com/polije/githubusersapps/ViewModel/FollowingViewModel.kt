package com.polije.githubusersapps.ViewModel

import androidx.lifecycle.ViewModel
import com.polije.githubusersapps.Repository.UsersRepository

class FollowingViewModel : ViewModel() {
    private val usersRepository = UsersRepository()

    val users = usersRepository.users
    val loading = usersRepository.loading

    fun fetchUserFollowing(username: String) {
        usersRepository.fetchUserFollowing(username)
    }
}