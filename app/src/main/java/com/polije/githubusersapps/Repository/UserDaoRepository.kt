package com.polije.githubusersapps.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.polije.githubusersapps.Db.User
import com.polije.githubusersapps.Db.UserDao
import com.polije.githubusersapps.Db.UserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserDaoRepository(private val userDao: UserDao){

    fun getAllUser(): LiveData<List<User>> = userDao.getFavoriteUsers()

    fun insertUser(user: User){
        userDao.addFavoriteUser(user)
    }

    fun removeUser(user: User){
         userDao.removeFavoriteUser(user)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<User?> = userDao.getFavoriteUserByUsername(username)
}