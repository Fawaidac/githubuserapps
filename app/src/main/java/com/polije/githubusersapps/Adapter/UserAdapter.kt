package com.polije.githubusersapps.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.polije.githubusersapps.MainActivity
import com.polije.githubusersapps.Model.UserModel
import com.polije.githubusersapps.R

class UserAdapter(private var userList: List<UserModel>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bindView(user, itemClickListener)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setUserList(userList: List<UserModel>) {
        this.userList = userList
        notifyDataSetChanged()
    }
}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ivAvatar: ImageView = itemView.findViewById(R.id.image)
    private val tvName: TextView = itemView.findViewById(R.id.name)

    fun bindView(user: UserModel, itemClickListener: OnItemClickListener) {
        tvName.text = user.login

        Glide.with(itemView)
            .load(user.avatar_url)
            .into(ivAvatar)

        itemView.setOnClickListener {
            itemClickListener.onItemClick(user)
        }
        ivAvatar.setBackgroundResource(R.drawable.bg);
        ivAvatar.clipToOutline = true;
    }
}

interface OnItemClickListener {
    fun onItemClick(user: UserModel)
}
