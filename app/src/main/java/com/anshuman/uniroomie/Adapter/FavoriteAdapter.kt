package com.anshuman.uniroomie.Adapter



import android.content.Context
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
import com.google.firebase.database.FirebaseDatabase

class FavoriteAdapter(
    private val context: Context,
    private var favoriteList: MutableList<User>
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val flatImageView: ImageView = itemView.findViewById(R.id.pivFlatimage)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTxt)
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTxt)
        private val roomSizeTextView: TextView = itemView.findViewById(R.id.tvroomsize)
        private val occupiedTextView: TextView = itemView.findViewById(R.id.tvoccopied)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTxt)
        private val flatTypeTextView: TextView = itemView.findViewById(R.id.tvflatType)
        private val favorite: ImageView = itemView.findViewById(R.id.ivFav)

        fun bind(user: User) {
            // Bind user data
            titleTextView.text = user.personalDetails.userName
            addressTextView.text = user.flatAddress.address
            roomSizeTextView.text = user.flatDetails.size.toString()
            occupiedTextView.text = user.flatDetails.capacity.toString()
            priceTextView.text = "$${user.flatAddress.rentAmount}"

            // Load image
            if (user.flatImages.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(user.flatImages[0])
                    .into(flatImageView)
            } else {
                flatImageView.setImageResource(R.drawable.house_1)
            }

            // Set the favorite icon based on the user's favorited status
            updateFavIcon(user, favorite)

            // Handle favorite icon click to remove it
            favorite.setOnClickListener {
                removeFromFavorites(user)
            }

            //updateFavoriteList(favoriteList)
        }

        private fun removeFromFavorites(user: User) {
            val database = FirebaseDatabase.getInstance()
            val favoritesRef = database.getReference("favorites")

            // Remove user from favorites in Firebase
            favoritesRef.child(user.userId).removeValue()
                .addOnSuccessListener {
                    // Update local list and notify adapter
                    favoriteList.remove(user)
                    notifyDataSetChanged()

                    // Provide feedback
                    Toast.makeText(context, "${user.personalDetails.userName} removed from favorites", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error: Unable to remove favorite", Toast.LENGTH_SHORT).show()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.viewholder_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int = favoriteList.size

    fun updateFavoriteList(newList: MutableList<User>) {
        favoriteList = newList
        notifyDataSetChanged()
    }
}
