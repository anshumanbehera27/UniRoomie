package com.anshuman.uniroomie.Modles

data class UserPreferences(
    val drink: Boolean = false,
    val smoke: Boolean = false,
    val workout: Boolean = false,
    val nonVegetarian: Boolean = false
)
