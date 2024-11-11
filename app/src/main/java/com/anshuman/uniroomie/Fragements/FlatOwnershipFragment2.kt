package com.anshuman.uniroomie.Fragements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anshuman.uniroomie.Modles.FlatOwnership
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.ViewModels.UserProfileViewModel
import com.anshuman.uniroomie.databinding.FragmentFlat2Binding


class FlatOwnershipFragment2 : Fragment() {
    private lateinit var _binding :FragmentFlat2Binding
    private val binding get() = _binding!!
    private lateinit var viewModel: UserProfileViewModel
    private var hasFlat: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlat2Binding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(UserProfileViewModel::class.java)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val next = binding.btnNext
        val back = binding.back
        val yesButton = binding.YesButton
        val noButton = binding.NoButton

        yesButton.setOnClickListener {
            // Change color of the buttons
            yesButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.pink))
            noButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color2))



            // Store the user's selection
            hasFlat = true
            updateFlatOwnershipInViewModel()
        }
        noButton.setOnClickListener {
            // Change color of the buttons
            noButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.pink))
            yesButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color2))


            // Store the user's selection
            hasFlat = false
            updateFlatOwnershipInViewModel()
        }

        back.setOnClickListener {
            findNavController().popBackStack()
        }

        next.setOnClickListener {
            if (hasFlat == true){
                findNavController().navigate(R.id.action_flatFragment2_to_flatInfoFragment)
            }else{
                findNavController().navigate(R.id.action_flatFragment2_to_userPictureFragment)
            }

        }
    }
    private fun updateFlatOwnershipInViewModel() {
        // Create an object with the value of hasFlat and update the ViewModel
        val flatOwnership = FlatOwnership(hasFlat ?: false)
        viewModel.updateFlatOwnership(flatOwnership)
    }




}