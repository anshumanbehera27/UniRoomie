package com.anshuman.uniroomie.Fragements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anshuman.uniroomie.R


class PersonalDetailsFragment1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    // Add The gender Datils Here
//        val genderSpinner: Spinner = view.findViewById(R.id.genderSpinner)
//        val genderOptions = arrayOf("Male", "Female")
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        genderSpinner.adapter = adapter

        return inflater.inflate(R.layout.fragment_basic_deatils1, container, false)
    }


}