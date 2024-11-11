package com.anshuman.uniroomie.Modles

import android.graphics.Bitmap


data class UserProfile(
    val personalDetails: PersonalDetails = PersonalDetails(),
    val flatOwnership: FlatOwnership = FlatOwnership(),
    val flatAddress: FlatAddress = FlatAddress(),
    val flatImages: FlatImages = FlatImages(),
    val flatDetails: FlatDetails = FlatDetails(),
    val userImage: UserImage = UserImage(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)), // Default 1x1 Bitmap
    val UserPreferences: UserPreferences = UserPreferences()
)