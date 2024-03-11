package com.polije.githubusersapps

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.polije.githubusersapps.Adapter.TabAdapter
import com.polije.githubusersapps.Db.User
import com.polije.githubusersapps.Model.UserDetailResponse
import com.polije.githubusersapps.Repository.UserDaoRepository
import com.polije.githubusersapps.ViewModel.MainViewModel
import com.polije.githubusersapps.ViewModel.UserViewModel
import com.polije.githubusersapps.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var btnFavorite: ImageButton
    private lateinit var userDaoRepository: UserDaoRepository
    private var user: User? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val sectionsPagerAdapter = TabAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        progressBar = findViewById(R.id.progressBar)
        btnFavorite = findViewById(R.id.btnFavorite)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val username = intent.getStringExtra("login")

        viewModel.userDetail.observe(this, Observer { userDetail ->
            populateUserDetail(userDetail)
            userDetail?.let { userDetails ->
                val user = User(userDetails.login, userDetails.avatarUrl)
                userViewModel.check(user).observeOnce(this) { existingUser ->
                    val drawable = if (existingUser != null)   R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                    btnFavorite.setImageResource(drawable)
                }
            }

            userViewModel.isFavorite.observe(this, Observer { isFavorite ->
                val drawableRes = if (isFavorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                btnFavorite.setImageResource(drawableRes)
            })
        })

        btnFavorite.setOnClickListener {
            val userDetail = viewModel.userDetail.value
            userDetail?.let { userDetails ->
                val user = User(userDetails.login, userDetails.avatarUrl)
                userViewModel.checkIfFavorite(user)
                userViewModel.check(user).observeOnce(this) { existingUser ->
                    if (existingUser != null) {
                        removeUser()
                    } else {
                        addUser()
                    }

                }
            }
        }

        viewModel.loading.observe(this, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        username?.let {
            viewModel.getDetailUser(it)
        }
    }

    fun addUser(){
        val userDetail = viewModel.userDetail.value
        userDetail?.let {
            val user = User(it.login, it.avatarUrl)
            userViewModel.insert(user)
            Toast.makeText(this, "Success add ${it.login} to favorite", Toast.LENGTH_LONG).show()
            btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
    }
    fun removeUser(){
        val userDetail = viewModel.userDetail.value
        userDetail?.let {
            val user = User(it.login, it.avatarUrl)
            userViewModel.remove(user)
            Toast.makeText(this, "Success remove ${it.login} from favorite", Toast.LENGTH_LONG).show()
            btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }
    private fun populateUserDetail(userDetail: UserDetailResponse) {

        findViewById<TextView>(R.id.name).text = userDetail.login
        findViewById<TextView>(R.id.email).text = userDetail.email
        findViewById<TextView>(R.id.username).text = userDetail.name
        findViewById<TextView>(R.id.countFollowers).text = userDetail.followers.toString()
        findViewById<TextView>(R.id.countFollowing).text = userDetail.following.toString()
        findViewById<ImageView>(R.id.avatar).setBackgroundResource(R.drawable.bg);
        findViewById<ImageView>(R.id.avatar).clipToOutline = true;
        Glide.with(this)
            .load(userDetail.avatarUrl)
            .into(findViewById(R.id.avatar))
    }

}
fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: Observer<T>) {
    observe(owner, object : Observer<T> {
        override fun onChanged(t: T) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}
