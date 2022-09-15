package com.example.mvvmkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.data.models.User
import com.squareup.picasso.Picasso

class UsersAdapter(val data: List<User>) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user,
                parent, false)
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val usernameTv: TextView = itemView.findViewById(R.id.userNameTv)
    val userImageView: ImageView = itemView.findViewById(R.id.userImageView)

    fun bind(item: User) = with(itemView) {
        usernameTv.text = item.login
        Picasso.get().load(item.avatarUrl).into(userImageView)
    }

}
