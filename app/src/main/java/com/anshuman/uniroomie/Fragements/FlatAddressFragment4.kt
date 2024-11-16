package com.anshuman.uniroomie.Fragements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anshuman.uniroomie.Modles.FlatAddress
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.ViewModels.UserProfileViewModel
import com.anshuman.uniroomie.databinding.FragmentFlatInfoBinding


class FlatAddressFragment4 : Fragment() {

    private var _binding: FragmentFlatInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserProfileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlatInfoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(UserProfileViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener { navigateToNextFragment() }
        binding.back.setOnClickListener { findNavController().popBackStack() }
    }

    private fun navigateToNextFragment() {
        val flatNo = binding.inputFlatNo.text.toString().trim()
        val area = binding.inputArea.text.toString().trim()
        val addInfo = binding.inputAddInfo.text.toString().trim()
        val address = "$flatNo, $area, $addInfo"

        // Validate each field individually
        var isValid = true
        if (flatNo.isEmpty()) {
            binding.inputFlatNo.error = "Flat number is required"
            isValid = false
        } else {
            binding.inputFlatNo.error = null
        }

        if (area.isEmpty()) {
            binding.inputArea.error = "Area is required"
            isValid = false
        } else {
            binding.inputArea.error = null
        }

        if (addInfo.isEmpty()) {
            binding.inputAddInfo.error = "Additional info is required"
            isValid = false
        } else {
            binding.inputAddInfo.error = null
        }

        if (!isValid) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Parse and validate monthly amount
        val monthlyAmount = try {
            binding.inputMonthlyAmount.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            binding.inputMonthlyAmount.error = "Invalid amount"
            return
        }
        binding.inputMonthlyAmount.error = null
        // Update for room DB Data base



        // Update ViewModel and navigate
        val flatAddress = FlatAddress(address, monthlyAmount)
        viewModel.updateFlatAddress(flatAddress)
        findNavController().navigate(R.id.action_flatInfoFragment_to_flatImagesFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
