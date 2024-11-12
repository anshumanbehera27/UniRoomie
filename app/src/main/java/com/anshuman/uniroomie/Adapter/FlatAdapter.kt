package com.anshuman.uniroomie.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.uniroomie.Modles.FlatItem
import com.anshuman.uniroomie.R
import com.bumptech.glide.Glide

class FlatAdapter(private val flatList: List<FlatItem>) :
    RecyclerView.Adapter<FlatAdapter.FlatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_item, parent, false)
        return FlatViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlatViewHolder, position: Int) {
        holder.bind(flatList[position])
    }

    override fun getItemCount(): Int = flatList.size

    class FlatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val flatTypeTextView: TextView = itemView.findViewById(R.id.tvflatType)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTxt)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTxt)
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTxt)
        private val roomSizeTextView: TextView = itemView.findViewById(R.id.tvroomsize)
        private val occupancyTextView: TextView = itemView.findViewById(R.id.tvoccopied)
        private val flatImageView: ImageView = itemView.findViewById(R.id.pivFlatimage)

        fun bind(flatItem: FlatItem) {
            flatTypeTextView.text = flatItem.flatType
            priceTextView.text = flatItem.rentAmount
            titleTextView.text = flatItem.houseName
            addressTextView.text = flatItem.address
            roomSizeTextView.text = flatItem.size.toString()
            occupancyTextView.text = flatItem.occupancy.toString()

            // Check if flatImages is not empty before loading
            if (flatItem.flatImages.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(flatItem.flatImages[0]) // Load the first image in the list
                    .centerCrop()
                    .into(flatImageView)
            }
        }
    }
}
