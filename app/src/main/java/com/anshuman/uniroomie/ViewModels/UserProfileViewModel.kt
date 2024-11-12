package com.anshuman.uniroomie.ViewModels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anshuman.uniroomie.Modles.FlatAddress
import com.anshuman.uniroomie.Modles.FlatDetails
import com.anshuman.uniroomie.Modles.FlatImages
import com.anshuman.uniroomie.Modles.FlatOwnership
import com.anshuman.uniroomie.Modles.PersonalDetails
import com.anshuman.uniroomie.Modles.UserPreferences
import com.anshuman.uniroomie.Modles.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class UserProfileViewModel : ViewModel() {

    // LiveData properties for each section of the user profile
    private val _userProfile = MutableLiveData(UserProfile())
    val userProfile: LiveData<UserProfile> get() = _userProfile
    val personalDetails = MutableLiveData<PersonalDetails>()
    val flatOwnership = MutableLiveData<FlatOwnership>()
    val flatAddress = MutableLiveData<FlatAddress>()
    val flatDetails = MutableLiveData<FlatDetails>()
    val userPreferences = MutableLiveData<UserPreferences>()

    private val _flatImages = MutableLiveData<FlatImages>()
    val flatImages: LiveData<FlatImages> get() = _flatImages

    // Firebase instances
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().getReference("users")
    private val storage = FirebaseStorage.getInstance().getReference("user_images")

    // Initialize LiveData with an empty FlatImages instance
    init {
        _flatImages.value = FlatImages()
    }

    // Update each part of the user profile
    fun updatePersonalDetails(details: PersonalDetails) {
        personalDetails.value = details
    }

    fun updateFlatOwnership(ownership: FlatOwnership) {
        flatOwnership.value = ownership
    }

    fun updateFlatAddress(address: FlatAddress) {
        flatAddress.value = address
    }

    fun updateFlatDetails(details: FlatDetails) {
        flatDetails.value = details
    }

    fun updateUserPreferences(preferences: UserPreferences) {
        userPreferences.value = preferences
    }

    fun updateFlatImages(images: FlatImages) {
        _flatImages.value = images
    }

    // Upload all user data to Firebase
    fun uploadUserData(onComplete: (Boolean) -> Unit) {
        val userId = firebaseAuth.currentUser?.uid ?: UUID.randomUUID().toString()
        val userRef = database.child(userId)

        // First, upload the images and get their URLs
        val imagesList = flatImages.value?.images ?: mutableListOf()
        uploadImages(imagesList) { imageUrls ->
            if (imageUrls.isEmpty() && imagesList.isNotEmpty()) {
                onComplete(false) // Error in image upload
                return@uploadImages
            }

            // Update user profile data with the uploaded image URLs
            _userProfile.value = _userProfile.value?.copy(flatImages = FlatImages(imageUrls.toMutableList()))

            // Prepare data map for Firebase Database
            val userProfileData = mapOf(
                "personalDetails" to personalDetails.value,
                "flatOwnership" to flatOwnership.value,
                "flatAddress" to flatAddress.value,
                "flatDetails" to flatDetails.value,
                "userPreferences" to userPreferences.value,
                "flatImages" to imageUrls
            )

            // Upload the combined data to Firebase Realtime Database
            userRef.setValue(userProfileData)
                .addOnSuccessListener { onComplete(true) }
                .addOnFailureListener { onComplete(false) }
        }
    }

    // Upload images and return their URLs
    private fun uploadImages(
        imageUris: List<String>,
        onComplete: (List<String>) -> Unit
    ) {
        if (imageUris.isEmpty()) {
            onComplete(emptyList())
            return
        }

        val uploadedUrls = mutableListOf<String>()
        var uploadCount = 0

        imageUris.forEach { uri ->
            val imageRef = storage.child("${UUID.randomUUID()}.jpg")
            val fileUri = Uri.parse(uri)

            imageRef.putFile(fileUri)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { url ->
                        uploadedUrls.add(url.toString())
                        uploadCount++

                        // Check if all images are uploaded
                        if (uploadCount == imageUris.size) {
                            onComplete(uploadedUrls)
                        }
                    }
                }
                .addOnFailureListener {
                    onComplete(emptyList()) // In case of error, return empty list
                    return@addOnFailureListener
                }
        }
    }
}
