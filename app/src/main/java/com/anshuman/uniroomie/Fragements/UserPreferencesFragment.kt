package com.anshuman.uniroomie.Fragements

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anshuman.uniroomie.MainActivity
import com.anshuman.uniroomie.Modles.UserPreferences
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.ViewModels.UserProfileViewModel
import com.anshuman.uniroomie.databinding.FragmentUserPersonalInfoBinding

class UserPreferencesFragment : Fragment() {
    private var _binding: FragmentUserPersonalInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var userProfileViewModel: UserProfileViewModel

    private var drink: Boolean = false
    private var smoke: Boolean = false
    private var workout: Boolean = false
    private var nonVegetarian: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserPersonalInfoBinding.inflate(inflater, container, false)
        userProfileViewModel = ViewModelProvider(requireActivity()).get(UserProfileViewModel::class.java)

        // Set button listeners for drink preference
        binding.drinkYes.setOnClickListener {
            updateButtonState(binding.drinkYes, binding.drinkNo, "drink", true)
        }
        binding.drinkNo.setOnClickListener {
            updateButtonState(binding.drinkNo, binding.drinkYes, "drink", false)
        }

        // Set button listeners for smoke preference
        binding.smokeYes.setOnClickListener {
            updateButtonState(binding.smokeYes, binding.smokeNo, "smoke", true)
        }
        binding.smokeNo.setOnClickListener {
            updateButtonState(binding.smokeNo, binding.smokeYes, "smoke", false)
        }

        // Set button listeners for workout preference
        binding.workoutYes.setOnClickListener {
            updateButtonState(binding.workoutYes, binding.workoutNo, "workout", true)
        }
        binding.workoutNo.setOnClickListener {
            updateButtonState(binding.workoutNo, binding.workoutYes, "workout", false)
        }

        // Set button listeners for non-vegetarian preference
        binding.nonVegetarianYes.setOnClickListener {
            updateButtonState(binding.nonVegetarianYes, binding.nonVegetarianNo, "nonVegetarian", true)
        }
        binding.nonVegetarianNo.setOnClickListener {
            updateButtonState(binding.nonVegetarianNo, binding.nonVegetarianYes, "nonVegetarian", false)
        }

        // Handle Next button click
        binding.btnNext.setOnClickListener {
            // Update preferences in the ViewModel
            val userPreferences = UserPreferences(drink, smoke, workout, nonVegetarian)
            userProfileViewModel.updateUserPreferences(userPreferences)

            // Navigate to the next screen
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)


        }

        // Handle back button click
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun updateButtonState(selectedButton: View, unselectedButton: View, preferenceKey: String, value: Boolean) {
        // Change the background of the selected button
        selectedButton.setBackgroundColor(resources.getColor(R.color.pink))

        // Reset the background color of the unselected button
        unselectedButton.setBackgroundColor(resources.getColor(R.color.color2))

        // Update the value for the selected preference
        when (preferenceKey) {
            "drink" -> drink = value
            "smoke" -> smoke = value
            "workout" -> workout = value
            "nonVegetarian" -> nonVegetarian = value
        }
    }
}
