package com.polije.githubusersapps.FragmentTabView

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polije.githubusersapps.Adapter.OnItemClickListener
import com.polije.githubusersapps.Adapter.UserAdapter
import com.polije.githubusersapps.DetailActivity
import com.polije.githubusersapps.ViewModel.FollowersViewModel
import com.polije.githubusersapps.Model.UserModel
import com.polije.githubusersapps.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowersFragment : Fragment(), OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var followersAdapter: UserAdapter
    private lateinit var viewModel: FollowersViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var rvUser: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val username = arguments?.getString("username")
        val view = inflater.inflate(R.layout.fragment_followers, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(FollowersViewModel::class.java)
        clearList()
        username?.let { observeFollowers(it) }
        progressBar = view.findViewById(R.id.progressBar)
        rvUser = view.findViewById(R.id.rvUser)
        setupRecyclerView()
        return  view
    }

    private fun setupRecyclerView() {
        followersAdapter = UserAdapter(emptyList(), this)
        rvUser.layoutManager = LinearLayoutManager(requireContext())
        rvUser.adapter = followersAdapter
    }
    private fun clearList() {
        if(::followersAdapter.isInitialized) {
            followersAdapter.setUserList(emptyList())
        }
    }

    private fun observeFollowers(username: String) {
        viewModel.fetchUserFollowers(username)
        viewModel.users.observe(viewLifecycleOwner, Observer { userList ->
            userList?.let {
                followersAdapter.setUserList(it)
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })
    }

    private fun showLoading(loading: Boolean?) {
        progressBar.visibility = if (loading == true) View.VISIBLE else View.GONE
        rvUser.visibility = if (loading == true) View.GONE else View.VISIBLE
    }

    override fun onItemClick(user: UserModel) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("login", user.login)
        intent.putExtra("avatar", user.avatar_url)
        startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param username Parameter 1.
         * @return A new instance of fragment FollowersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(username: String) =
            FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString("username", username)
                }
            }
    }
}