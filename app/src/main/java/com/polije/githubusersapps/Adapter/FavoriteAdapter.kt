package com.polije.githubusersapps.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.polije.githubusersapps.Db.User
import com.polije.githubusersapps.Model.UserModel
import com.polije.githubusersapps.R

class FavoriteAdapter(private var users: List<User> , private val itemClickListener: OnItemClickListenerFavorite) : RecyclerView.Adapter<FavoriteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindView(users[position], itemClickListener )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setUserList(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }
}

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ivAvatar: ImageView = itemView.findViewById(R.id.image)
    private val tvName: TextView = itemView.findViewById(R.id.name)

    fun bindView(user: User, itemClickListener: OnItemClickListenerFavorite) {
        tvName.text = user.login

        Glide.with(itemView)
            .load(user.avatarUrl)
            .into(ivAvatar)

        itemView.setOnClickListener {
            itemClickListener.onItemClick(user)
        }
        ivAvatar.setBackgroundResource(R.drawable.bg);
        ivAvatar.clipToOutline = true;
    }
}
interface OnItemClickListenerFavorite {
    fun onItemClick(user: User)
}

