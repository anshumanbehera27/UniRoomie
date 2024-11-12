package com.anshuman.uniroomie.Screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anshuman.uniroomie.Modles.PersonalDetails
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.ViewModels.SharedViewModel
import com.anshuman.uniroomie.ViewModels.UserProfileViewModel

class TestActivity : AppCompatActivity() {

    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        // Initialize SharedViewModel
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        val btnAddMovie = findViewById<Button>(R.id.btnAddMovie)
        val tvName = findViewById<TextView>(R.id.textViewName)
        val tvDob = findViewById<TextView>(R.id.textViewDob)
        val tvGender = findViewById<TextView>(R.id.textViewGender)

        // Set click listener to add personal details
        btnAddMovie.setOnClickListener {
            // Creating a Deatils object with sample data
            val anshu = PersonalDetails("Anshuman", "10/20/1990", "Male")
            // Update ViewModel with the new details
            sharedViewModel.updatePersonalDetails(anshu)

            // Navigate to MainActivity2 to show the data
            val intent = Intent(this, test2::class.java)
            startActivity(intent)
        }


        sharedViewModel.personalDetails.observe(this) { details ->
            // Update the TextViews with the received data

            tvName.text = "Name: ${details.userName}"
            tvDob.text = "DOB: ${details.dob}"
            tvGender.text = "Gender: ${details.gender}"
        }
    }








    }




