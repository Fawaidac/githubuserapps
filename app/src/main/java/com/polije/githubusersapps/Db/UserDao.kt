package com.polije.githubusersapps.Db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getFavoriteUsers(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavoriteUser(user: User)

    @Delete
    fun removeFavoriteUser(user: User)

    @Query("SELECT * FROM users WHERE login = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<User?>
}
