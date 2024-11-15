package com.anshuman.uniroomie.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.uniroomie.Modles.Usermsg
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.Screens.MessageActivity

class UserMsgAdapter(private val userList: List<Usermsg>) : RecyclerView.Adapter<UserMsgAdapter.UserMsgViewHolder>() {

    // ViewHolder class to hold references to UI elements
    class UserMsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameTextView: TextView = itemView.findViewById(R.id.tvflatusername)
        val msgview: ImageView = itemView.findViewById(R.id.ivmessage)

    }

    // Create a new ViewHolder and inflate the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMsgViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_message_item, parent, false)
        return UserMsgViewHolder(view)
    }

    // Bind the data to the ViewHolder
    override fun onBindViewHolder(holder: UserMsgViewHolder, position: Int) {
        val user = userList[position]

        // Set the username in the TextView
        holder.userNameTextView.text = user.name

        holder.msgview.setOnClickListener {
            // Create an Intent to start MessageViewActivity
            val intent = Intent(holder.itemView.context, MessageActivity::class.java)

            // Pass the username and default profile image as extras
            intent.putExtra("USER_NAME", user.name)
            intent.putExtra("PROFILE_IMAGE", R.drawable.profile)  // Use a default image resource ID

            // Start the MessageViewActivity
            holder.itemView.context.startActivity(intent)
        }
    }
    // Return the total count of items
    override fun getItemCount(): Int = userList.size
}
