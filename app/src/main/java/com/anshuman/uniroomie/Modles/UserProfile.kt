package com.anshuman.uniroomie.Modles

import android.graphics.Bitmap


data class UserProfile(
    val personalDetails: PersonalDetails = PersonalDetails(),
    val flatOwnership: FlatOwnership = FlatOwnership(),
    val flatAddress: FlatAddress = FlatAddress(),
  val flatImages: FlatImages = FlatImages(),
  //  val flatImages: FlatImages? = null,
    val flatDetails: FlatDetails = FlatDetails(),
    val userImage: UserImage = UserImage(),
    val UserPreferences: UserPreferences = UserPreferences()
)