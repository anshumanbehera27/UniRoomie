package com.anshuman.uniroomie.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.uniroomie.Modles.User
import com.anshuman.uniroomie.R
import com.bumptech.glide.Glide

class UserAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val flatImageView: ImageView = itemView.findViewById(R.id.pivFlatimage)
        private val flatTypeTextView: TextView = itemView.findViewById(R.id.tvflatType)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTxt)
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTxt)
        private val roomSizeTextView: TextView = itemView.findViewById(R.id.tvroomsize)
        private val occupiedTextView: TextView = itemView.findViewById(R.id.tvoccopied)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTxt)

        fun bind(user: User) {
            // Bind flat type
            flatTypeTextView.text = user.flatDetails.flatType

            // Bind title (user name in this case)
            titleTextView.text = user.personalDetails.userName

            // Bind address
            addressTextView.text = user.flatAddress.address

            // Bind room size
            roomSizeTextView.text = user.flatDetails.size.toString()

            // Bind number of occupied rooms (if available)
            occupiedTextView.text = user.flatDetails.capacity.toString()

            // Bind price
            priceTextView.text = "$${user.flatAddress.rentAmount}"

            // Load the first flat image
            if (user.flatImages.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(user.flatImages[0]) // Assuming flatImages is a list of URLs
                    .into(flatImageView)
            } else {
                // Set a placeholder if no image is available
                flatImageView.setImageResource(R.drawable.house_1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size
}
