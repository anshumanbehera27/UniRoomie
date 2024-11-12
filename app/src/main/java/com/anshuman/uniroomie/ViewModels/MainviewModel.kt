package com.anshuman.uniroomie.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anshuman.uniroomie.Modles.PersonalDetails

class SharedViewModel : ViewModel() {
    // LiveData to hold the personal details
    var personalDetails = MutableLiveData<PersonalDetails>()

    fun updatePersonalDetails(details: PersonalDetails) {
        personalDetails.value = details
    }
}


