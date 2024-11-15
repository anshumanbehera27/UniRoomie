package com.anshuman.uniroomie.Screens


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
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


        // Back button
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        // Handel the rating based on the flat type
        assineRating(flatType.toString())

        // Handel the map view From the address
        binding.tvLocationView.setOnClickListener {
            Toast.makeText(this, "Map View", Toast.LENGTH_SHORT).show()
            val addressParts = address?.split(",")?.map { it.trim() }

            if (addressParts?.size!! >= 2) {
                val city = addressParts[addressParts.size - 2]
                val state = addressParts[addressParts.size - 1]
                Toast.makeText(this, "City: $city, State: $state", Toast.LENGTH_SHORT).show()

                // Create an Intent to start the MapActivity
                val intent = Intent(this, MapActivity::class.java)
                intent.putExtra("CITY", city)
                intent.putExtra("STATE", state)

                // Start the MapActivity
                startActivity(intent)


                
            }
            else{
                Toast.makeText(this, "Invalid Address", Toast.LENGTH_SHORT).show()


            } }

        // Handel the Phone view Acivity view Based on the data
        binding.ivphone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel: +917815081125"))
            startActivity(intent)
        }







    }

    // assine rating based on the flat type
    fun assineRating (flatType: String){
        val rating = binding.scoreTxt
        when (flatType) {
            "Fully Furnished" -> {
                rating.text = "5"
            }
            "Semi Furnished" -> {
                rating.text = "4"
            }
            "Non Furnished" -> {
                rating.text = "3"
            }
            "Studio" -> {
                rating.text = "2"
            }
            else -> {
                rating.text = "3.5"
            }
        }
    }
}
