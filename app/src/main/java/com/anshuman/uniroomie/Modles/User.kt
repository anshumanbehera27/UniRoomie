package com.anshuman.uniroomie.Modles

data class User(
    val userId: String = "",  // To store the user's unique ID
    val flatAddress: FlatAddress = FlatAddress(),
    val flatDetails: FlatDetails = FlatDetails(),
    val flatOwnership: FlatOwnership = FlatOwnership(),
    val personalDetails: PersonalDetails = PersonalDetails(),
    val userPreferences: UserPreferences = UserPreferences(),
    val flatImages: List<String> = emptyList()
)