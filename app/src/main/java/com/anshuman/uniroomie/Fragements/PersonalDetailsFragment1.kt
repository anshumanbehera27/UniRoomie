package com.anshuman.uniroomie.Fragements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.anshuman.uniroomie.Modles.PersonalDetails
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.ViewModels.UserProfileViewModel
import com.anshuman.uniroomie.databinding.FragmentBasicDeatils1Binding


class PersonalDetailsFragment1 : Fragment() {

  private var _binding: FragmentBasicDeatils1Binding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBasicDeatils1Binding.inflate(inflater, container, false)





        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // write the logic for the Spinner  so we can select the Gender
        // Step 1: Define options and set up adapter for the gender spinner
        val genderOptions = listOf("Male", "Female")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.genderSpinner.adapter = adapter

        // Step 2: Handle spinner selection
        var selectedGender: String? = null
        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedGender = genderOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedGender = null
            }
        }


        val userName = binding.etname.text.toString()
        val dob = binding.dd.text.toString() + binding.mm.text.toString() + binding.yyyy.text.toString()


        val personalDetails = PersonalDetails(userName, dob, selectedGender ?: "")
        viewModel.updatePersonalDetails(personalDetails)

        // Redrict to the next fragement
        binding.btnNext.setOnClickListener{
            findNavController().navigate(R.id.action_basicDeatilsFragment1_to_flatFragment2)


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}