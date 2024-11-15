package com.anshuman.uniroomie.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.uniroomie.Modles.User
import com.anshuman.uniroomie.R
import com.bumptech.glide.Glide
import com.anshuman.uniroomie.Screens.DeatilsViewActivity
import com.google.firebase.database.FirebaseDatabase

class UserAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var filteredList: MutableList<User> = userList.toMutableList()
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val flatImageView: ImageView = itemView.findViewById(R.id.pivFlatimage)
        private val flatTypeTextView: TextView = itemView.findViewById(R.id.tvflatType)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTxt)
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTxt)
        private val roomSizeTextView: TextView = itemView.findViewById(R.id.tvroomsize)
        private val occupiedTextView: TextView = itemView.findViewById(R.id.tvoccopied)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTxt)
        private val favorite :ImageView = itemView.findViewById(R.id.ivFav)

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

            // Set item click listener to navigate to the details activity
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, DeatilsViewActivity::class.java)

                // Pass the necessary data to the details activity
                intent.putExtra("userId", user.userId)
                intent.putExtra("userName", user.personalDetails.userName)
                intent.putExtra("address", user.flatAddress.address)
                intent.putExtra("rentAmount", user.flatAddress.rentAmount)
                intent.putExtra("flatType", user.flatDetails.flatType)
                intent.putExtra("roomSize", user.flatDetails.size)
                intent.putExtra("occupied", user.flatDetails.capacity)
                intent.putExtra("flatImages", user.flatImages.toTypedArray())
                intent.putExtra("drinking", user.userPreferences.drink)
                intent.putExtra("smoking", user.userPreferences.smoke)
                intent.putExtra("workout", user.userPreferences.workout)
                intent.putExtra("nonVegetarian", user.userPreferences.nonVegetarian)

                // Start the detail activity
                context.startActivity(intent)
            }
            // Add favorite functionality
            favorite.setOnClickListener {
                saveToFavorites(user, favorite)

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
    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            userList.toMutableList()
        } else {
            userList.filter { it.personalDetails.userName.contains(query, ignoreCase = true) }.toMutableList()
        }
        notifyDataSetChanged()
    }

    private fun saveToFavorites(user: User, favorite: ImageView) {
        val database = FirebaseDatabase.getInstance()
        val favoritesRef = database.getReference("favorites")

        // Save user data to Firebase
        favoritesRef.child(user.userId).setValue(user)
            .addOnSuccessListener {
                user.isFavorited = true // Update local model
                updateFavIcon(user, favorite)
                Log.d("FAV", "User add Add to Favrioite ")

            }
            .addOnFailureListener { exception ->
              Log.d("FAV" , "You got some error for fac ${exception}")
            }
    }

    private fun updateFavIcon(user: User, favorite: ImageView) {
        if (user.isFavorited) {
            favorite.setImageResource(R.drawable.ic_fav_fill) // Filled icon
        } else {
            favorite.setImageResource(R.drawable.favorite) // Outline icon
        }
    }
}



