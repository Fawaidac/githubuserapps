package com.polije.githubusersapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.githubusersapps.Adapter.OnItemClickListener
import com.polije.githubusersapps.Adapter.UserAdapter
import com.polije.githubusersapps.Model.UserModel
import com.polije.githubusersapps.Preferens.ThemePrefs
import com.polije.githubusersapps.Preferens.dataStore
import com.polije.githubusersapps.ViewModel.MainViewModel
import com.polije.githubusersapps.ViewModel.ThemeViewModel
import com.polije.githubusersapps.ViewModel.ViewModelFactory
import com.polije.githubusersapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnItemClickListener {

    lateinit var binding: ActivityMainBinding
    private lateinit var usersAdapter: UserAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(
            MainViewModel::class.java)
        val prefs = ThemePrefs.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactory(prefs)).get(
            ThemeViewModel::class.java
        )

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        setupRecyclerView()
        observeViewModel()

        binding.progressBar.visibility = View.VISIBLE
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchUsers(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    viewModel.fetchUsers()
                } else {
                    newText.let { viewModel.searchUsers(it) }
                }
                return true
            }
        })

        binding.setting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        binding.favorite.setOnClickListener{
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(user: UserModel) {
       val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("login", user.login)
        startActivity(intent)
    }

    private fun observeViewModel() {
        viewModel.users.observe(this, Observer { userList ->
            userList?.let {
                usersAdapter.setUserList(it)
            }
        })

        viewModel.loading.observe(this, Observer {
            showLoading(it)
        })
    }
    private fun setupRecyclerView() {
        usersAdapter = UserAdapter(emptyList(), this)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = usersAdapter
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}