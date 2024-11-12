package com.anshuman.uniroomie.Screens

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.anshuman.uniroomie.Modles.PersonalDetails
import com.anshuman.uniroomie.Modles.UserImage
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.R.id.upimage
import com.anshuman.uniroomie.ViewModels.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class test2 : AppCompatActivity() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)

        // Initialize Firebase
        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = firebaseStorage.reference
        firebaseDatabase = FirebaseDatabase.getInstance()

        // Initialize SharedViewModel
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        // Upload image to Firebase Storage and get the URL
      //  val imageView = findViewById<ImageView>(R.id.upimage) // Add an ImageView in XML to show the image

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val imageReference = storageReference.child("images/${userId}/profile_picture.jpg")

        // For simplicity, we simulate image upload here, usually you can use image pickers
        val imageUri = "app/src/main/res/drawable/house_1.jpg"

        imageReference.putFile(Uri.parse(imageUri))
            .addOnSuccessListener {
                // Image uploaded successfully, get the download URL
                imageReference.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    uploadDataToFirebase(imageUrl)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadDataToFirebase(imageUrl: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val userRef = firebaseDatabase.getReference("users").child(userId)

            // Retrieve personal details from ViewModel
            sharedViewModel.personalDetails.observe(this) { details ->
                // Prepare the data to be uploaded
                val personalDetails = PersonalDetails(details.userName, details.dob, details.gender)
                val userImage = UserImage(imageUrl)

                // Upload both personal details and image URL
                userRef.child("personalDetails").setValue(personalDetails)
                userRef.child("userImage").setValue(userImage)

                Toast.makeText(this, "Data uploaded successfully", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }
}
