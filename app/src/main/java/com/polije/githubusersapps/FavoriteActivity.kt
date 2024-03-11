package com.polije.githubusersapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polije.githubusersapps.Adapter.FavoriteAdapter
import com.polije.githubusersapps.Adapter.OnItemClickListenerFavorite
import com.polije.githubusersapps.Db.User
import com.polije.githubusersapps.ViewModel.UserViewModel

class FavoriteActivity : AppCompatActivity() , OnItemClickListenerFavorite{
    private lateinit var userViewModel: UserViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var rvFavorite: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        favoriteAdapter = FavoriteAdapter(emptyList(), this)
        rvFavorite = findViewById(R.id.rVFavorite)
        rvFavorite.layoutManager = LinearLayoutManager(this)
        rvFavorite.adapter = favoriteAdapter


        userViewModel.users.observe(this, Observer { users ->
            users?.let {
                favoriteAdapter.setUserList(it)
            }
        })
    }

    override fun onItemClick(user: User) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("login", user.login)
        startActivity(intent)
    }
}