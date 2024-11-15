package com.anshuman.uniroomie.Modles

import android.graphics.Bitmap
import java.io.Serializable


data class UserProfile(
    val personalDetails: PersonalDetails = PersonalDetails(),
    val flatOwnership: FlatOwnership = FlatOwnership(),
    val flatAddress: FlatAddress = FlatAddress(),
  val flatImages: FlatImages = FlatImages(),

    val flatDetails: FlatDetails = FlatDetails(),
    val userImage: UserImage = UserImage(),
    val UserPreferences: UserPreferences = UserPreferences()
): Serializable