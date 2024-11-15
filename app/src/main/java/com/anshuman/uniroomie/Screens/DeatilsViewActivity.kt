package com.anshuman.uniroomie.Screens


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anshuman.uniroomie.R
import com.bumptech.glide.Glide
import com.anshuman.uniroomie.databinding.ActivityDeatilsViewBinding

class DeatilsViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeatilsViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityDeatilsViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the data passed from the adapter
        val userId = intent.getStringExtra("userId")
        val userName = intent.getStringExtra("userName")
        val address = intent.getStringExtra("address")
        val rentAmount = intent.getDoubleExtra("rentAmount", 0.0)
        val flatType = intent.getStringExtra("flatType")
        val roomSize = intent.getIntExtra("roomSize", 0)
        val occupied = intent.getIntExtra("occupied", 0)
        val flatImages = intent.getStringArrayExtra("flatImages")?.toList() ?: emptyList()
        val drink = intent.getBooleanExtra("drinking", false)
        val smoke = intent.getBooleanExtra("smoking", false)
        val workout = intent.getBooleanExtra("workout", false)
        val nonVegetarian = intent.getBooleanExtra("nonVegetarian", false)

        // Set the data to views using ViewBinding
        binding.tvflatType.text = flatType
        binding.priceTxt.text = "$$rentAmount"
        binding.titleAddressTxt.text = address
        binding.descriptionTxt.text = "Room Size: $roomSize | Occupied: $occupied"
        binding.tvflatusername.text = userName

        // Set user preferences
        binding.drinkingValue.text = if (drink) "Yes" else "No"
        binding.workoutValue.text = if (workout) "Yes" else "No"
        binding.smokingValue.text = if (smoke) "Yes" else "No"
        binding.nonVegetarianValue.text = if (nonVegetarian) "Yes" else "No"

        // Load the first flat image (if available)
        if (flatImages.isNotEmpty()) {
            Glide.with(this)
                .load(flatImages[0]) // Assuming it's a list of image URLs
                .into(binding.flatimages)
        } else {
            // Set a placeholder if no image is available
            binding.flatimages.setImageResource(R.drawable.house_1)
        }
    }
}
