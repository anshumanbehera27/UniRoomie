package com.anshuman.uniroomie.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.anshuman.uniroomie.Adapter.FlatAdapter
import com.anshuman.uniroomie.Modles.FlatImages
import com.anshuman.uniroomie.Modles.FlatItem
import com.anshuman.uniroomie.Modles.UserProfile

import com.anshuman.uniroomie.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var flatAdapter: FlatAdapter
    private val flatList = mutableListOf<FlatItem>()
    private val TAG = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout and initialize ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance()
        databaseRef = database.getReference("users")






        // Initialize the RecyclerView adapter and set it to the RecyclerView
        flatAdapter = FlatAdapter(flatList)
        binding.recommendedView.layoutManager = LinearLayoutManager(requireContext())
        binding.recommendedView.adapter = flatAdapter
        binding.recommendedView.setHasFixedSize(true)

        // Fetch data from Firebase
        fetchFlatData()

        binding.nearView.layoutManager = LinearLayoutManager(requireContext())
        binding.nearView.adapter = flatAdapter
        binding.nearView.setHasFixedSize(true)

        return binding.root
    }

    private fun fetchFlatData() {
        // Attach ValueEventListener to the Firebase database
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                flatList.clear()

                // Iterate through each user profile in the Firebase snapshot
                for (userSnapshot in snapshot.children) {
                    val userProfile = userSnapshot.getValue(UserProfile::class.java)

                    // Ensure userProfile is not null
                    userProfile?.let { profile ->
                        // Convert image list from Firebase if exists
                        val flatImages = userSnapshot.child("flatDetails").child("images").let {
                            convertArrayListToFlatImages(it)
                        }

                        // Map UserProfile to FlatItem
                        val flatItem = FlatItem(
                            flatImages = profile.flatImages.images.takeIf { it.isNotEmpty() } ?: listOf("default_image_uri"), // Add a default if no images

                            flatType = profile.flatDetails.flatType,
                            rentAmount = profile.flatAddress.rentAmount.toString(),
                            houseName = profile.personalDetails.userName,
                            address = profile.flatAddress.address,
                            size = profile.flatDetails.size,
                            occupancy = profile.flatDetails.occupied,

                        )

                        // Add the mapped FlatItem to the list
                        flatList.add(flatItem)
                    } ?: Log.w(TAG, "UserProfile is null or incomplete")
                }

                // Notify the adapter that the data has changed
                flatAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Log the error if the Firebase data fetch is cancelled or fails
                Log.e(TAG, "Failed to read value: ${error.message}")
            }
        })
    }

    // Custom method to convert ArrayList from Firebase to FlatImages
    private fun convertArrayListToFlatImages(dataSnapshot: DataSnapshot): FlatImages? {
        val imagesList = mutableListOf<String>()

        // Check if the snapshot contains data and is of ArrayList type
        if (dataSnapshot.exists() && dataSnapshot.value is List<*>) {
            val dataList = dataSnapshot.value as List<*>
            for (item in dataList) {
                if (item is String) {
                    imagesList.add(item)  // Add image URI to the list
                }
            }
        }

        // Return a new FlatImages object with the list of images
        return if (imagesList.isNotEmpty()) FlatImages(imagesList) else null
    }
}
