package com.anshuman.uniroomie.Fragements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.anshuman.uniroomie.Modles.FlatDetails
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.ViewModels.UserProfileViewModel
import com.anshuman.uniroomie.databinding.FragmentFlatInfo2Binding

class FlatDetailsFragment5 : Fragment() {
    private var _binding: FragmentFlatInfo2Binding? = null
    private val binding get() = _binding!!

    private lateinit var userProfileViewModel: UserProfileViewModel

    // Declare variables at the class level
    private var flatType: String = ""
    private var flatSize: Int = 0
    private var capacity: Int = 0
    private var occupied: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlatInfo2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel
        userProfileViewModel = ViewModelProvider(requireActivity()).get(UserProfileViewModel::class.java)

        // Radio Group to get Flat Type (Fully furnished, Semi furnished, Non-furnished)
        binding.radioInfo.setOnCheckedChangeListener { _, checkedId ->
            flatType = when (checkedId) {
                R.id.fullyFurnished -> "Fully Furnished"
                R.id.semiFurnished -> "Semi Furnished"
                R.id.nonFurnished -> "Non Furnished"
                else -> ""
            }
        }

        // EditText to get Size of the Flat
        binding.inputSize.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val sizeText = binding.inputSize.text.toString()
                if (sizeText.isNotEmpty()) {
                    flatSize = sizeText.toInt()
                }
            }
        }

        // EditText to get Capacity (How many people occupy the flat)
        binding.inputCapacity.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val capacityText = binding.inputCapacity.text.toString()
                if (capacityText.isNotEmpty()) {
                    capacity = capacityText.toInt()
                }
            }
        }

        // Slider to get Occupied Capacity (How many people occupy the flat)
        binding.occupiedSlider.addOnChangeListener { _, value, _ ->
            occupied = value.toInt()
        }

        // Handle Next button click
        binding.btnNext.setOnClickListener {
            // Create FlatDetails object with updated values
            val flatDetails = FlatDetails(flatType, flatSize, capacity, occupied)
            userProfileViewModel.updateFlatDetails(flatDetails)
            Toast.makeText(requireContext(), "Flat Details Updated", Toast.LENGTH_SHORT).show()

            // Navigate to the next fragment
            findNavController().navigate(R.id.action_flatInfoFragment2_to_userPictureFragment)
        }

        // Handle Back button click
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
