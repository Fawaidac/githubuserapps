package com.polije.githubusersapps.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.polije.githubusersapps.Db.User
import com.polije.githubusersapps.Db.UserDatabase
import com.polije.githubusersapps.Model.UserModel
import com.polije.githubusersapps.Repository.UserDaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val _users = MutableLiveData<List<User>>()
    var users: LiveData<List<User>> = _users

    private val userDaoRepository: UserDaoRepository

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    fun checkIfFavorite(user: User) {
        viewModelScope.launch {
            val existingUser = userDaoRepository.getFavoriteUserByUsername(user.login)
            _isFavorite.value = true
        }
    }

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        userDaoRepository = UserDaoRepository(userDao)
        users = userDaoRepository.getAllUser()
    }

    fun insert(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            userDaoRepository.insertUser(user)
        }
    }
    fun remove(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            userDaoRepository.removeUser(user)
        }
    }

    fun check(user: User): LiveData<User?> {
        return userDaoRepository.getFavoriteUserByUsername(user.login)
    }
}