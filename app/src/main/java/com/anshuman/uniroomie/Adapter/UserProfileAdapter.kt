package com.anshuman.uniroomie.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.uniroomie.Modles.UserProfile
import com.anshuman.uniroomie.R
import com.bumptech.glide.Glide

class UserProfileAdapter(
    private val userList: List<UserProfile>,
    private val onItemClick: (UserProfile) -> Unit
) : RecyclerView.Adapter<UserProfileAdapter.UserProfileViewHolder>() {

    inner class UserProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userNameText: TextView = itemView.findViewById(R.id.userName)
        private val flatTypeText: TextView = itemView.findViewById(R.id.flatType)
        private val rentAmountText: TextView = itemView.findViewById(R.id.rentAmount)
        private val flatImage: ImageView = itemView.findViewById(R.id.flatImage)

        fun bind(userProfile: UserProfile) {
            userNameText.text = userProfile.personalDetails.userName
            flatTypeText.text = userProfile.flatDetails.flatType
            rentAmountText.text = userProfile.flatAddress.rentAmount.toString()

            // Load the first flat image
            Glide.with(itemView.context)
                .load(userProfile.flatImages.images.get(0))
                .into(flatImage)

            itemView.setOnClickListener {
                onItemClick(userProfile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_profile_item, parent, false)
        return UserProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserProfileViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size
}
