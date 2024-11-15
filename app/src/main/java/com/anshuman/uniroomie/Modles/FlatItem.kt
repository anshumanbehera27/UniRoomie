package com.anshuman.uniroomie.Modles

import java.io.Serializable

data class FlatItem(
    val flatImages: FlatImages?= null,  // List to hold multiple image URIs
    val flatType: String = "",
    val rentAmount: String = "",
    val houseName: String = "",
    val address: String = "",
    val size: Int = 0,
    val occupancy: Int = 0
) : Serializable
