package com.anshuman.uniroomie.Fragements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.anshuman.uniroomie.Modles.PersonalDetails
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.ViewModels.UserProfileViewModel
import com.anshuman.uniroomie.databinding.FragmentBasicDeatils1Binding


class PersonalDetailsFragment1 : Fragment() {

    private var _binding: FragmentBasicDeatils1Binding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserProfileViewModel
   // private lateinit var userDetailsViewModel: UserDetailsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBasicDeatils1Binding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(UserProfileViewModel::class.java)
      //  userDetailsViewModel = ViewModelProvider(requireActivity()).get(UserDetailsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up gender spinner options
        val genderOptions = listOf("Male", "Female")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.genderSpinner.adapter = adapter

        var selectedGender: String? = null
        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedGender = genderOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedGender = null
            }
        }

        // "Next" button click handler
        binding.btnNext.setOnClickListener {
            val userName = binding.etname.text.toString()
            val dob = binding.dd.text.toString() + binding.mm.text.toString() + binding.yyyy.text.toString()
            val gender = selectedGender ?: return@setOnClickListener





            // Create PersonalDetails object
            val personalDetails = PersonalDetails(userName, dob, gender)

            // Update the ViewModel
            viewModel.updatePersonalDetails(personalDetails)

       //     userDetailsViewModel.saveUserDetails(name = userName, email = "anshuman@gmail.com", phone = "7815081125", address = "Dharamgarh kalahandi", bio = "Student A Lovely professional University")



//            val intent = Intent(requireContext(), TestActivity::class.java)
//            startActivity(intent)

            // Navigate to the next fragment
         findNavController().navigate(R.id.action_basicDeatilsFragment1_to_flatFragment2)
        }

        // Set up back button functionality
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
