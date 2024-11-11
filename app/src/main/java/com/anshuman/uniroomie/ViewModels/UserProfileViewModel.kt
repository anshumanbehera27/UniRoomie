package com.anshuman.uniroomie.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anshuman.uniroomie.Modles.FlatAddress
import com.anshuman.uniroomie.Modles.FlatDetails
import com.anshuman.uniroomie.Modles.FlatImages
import com.anshuman.uniroomie.Modles.FlatOwnership
import com.anshuman.uniroomie.Modles.PersonalDetails
import com.anshuman.uniroomie.Modles.UserImage
import com.anshuman.uniroomie.Modles.UserPreferences
import com.anshuman.uniroomie.Modles.UserProfile

class UserProfileViewModel:ViewModel() {
    private val _userProfile = MutableLiveData(UserProfile())
    val userProfile: LiveData<UserProfile> get() = _userProfile

    fun updatePersonalDetails(details: PersonalDetails) {
        _userProfile.value = _userProfile.value?.copy(personalDetails = details)
    }

    fun updateFlatOwnership(ownership: FlatOwnership) {
        _userProfile.value = _userProfile.value?.copy(flatOwnership = ownership)
    }

    fun updateFlatAddress(address: FlatAddress) {
        _userProfile.value = _userProfile.value?.copy(flatAddress = address)
    }

    fun updateFlatImages(images: FlatImages) {
        _userProfile.value = _userProfile.value?.copy(flatImages = images)

        // Upload to Fire base
        //uploadImagesToFirebase(images.images)

    }

    fun updateFlatDetails(details: FlatDetails) {
        _userProfile.value = _userProfile.value?.copy(flatDetails = details)
    }

    fun updateUserImage(image: UserImage) {
        _userProfile.value = _userProfile.value?.copy(userImage = image)
    }

    fun updateUserPreferences(preferences: UserPreferences) {
        _userProfile.value = _userProfile.value?.copy(UserPreferences = preferences)
    }

    // TODO  Upload images to Firebase Storage

//    //
//    private fun uploadImagesToFirebase(images: List<Bitmap>) {
//        images.forEachIndexed { index, bitmap ->
//            val imageRef = storageRef.child("flat_images/${System.currentTimeMillis()}_$index.jpg")
//            val byteArray = convertBitmapToByteArray(bitmap)
//
//            val uploadTask = imageRef.putBytes(byteArray)
//            uploadTask.addOnSuccessListener { taskSnapshot ->
//                taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
//                    // Handle image URL (store it in your database or whatever you need)
//                    Log.d("FirebaseUpload", "Image uploaded: ${uri.toString()}")
//                    // You can store the URL in Firebase Realtime Database or Firestore if needed
//                }
//            }.addOnFailureListener { exception ->
//                Log.e("FirebaseUpload", "Upload failed: ${exception.message}")
//            }
//        }
//    }
//
//    // Convert Bitmap to ByteArray for uploading
//    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
//        return byteArrayOutputStream.toByteArray()
//    }
}