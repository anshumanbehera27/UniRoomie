package com.anshuman.uniroomie.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.uniroomie.Modles.User
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.Screens.MessageActivity

class UserMsgAdapter( private val userList: List<User>,
                      private val onUserClick: (Int) -> Unit) : RecyclerView.Adapter<UserMsgAdapter.UserMsgViewHolder>() {

    class UserMsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameTextView: TextView = itemView.findViewById(R.id.tvmsgusername)
        val msgView: ImageView = itemView.findViewById(R.id.ivmessagefrgement)
        val profileImageView: ImageView = itemView.findViewById(R.id.userimageview1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMsgViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_message_item, parent, false)
        return UserMsgViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserMsgViewHolder, position: Int) {
        val user = userList[position]

        // Set user details
        holder.userNameTextView.text = user.personalDetails.userName
        holder.profileImageView.setImageResource(
            if (user.flatImages.isNotEmpty()) R.drawable.profile else R.drawable.profile
        )

        // Handle click event
        holder.msgView.setOnClickListener {
            onUserClick(position) // Pass position to callback
        }

    }

    override fun getItemCount(): Int = userList.size
}
