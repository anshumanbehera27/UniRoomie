package com.anshuman.uniroomie.Fragements

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anshuman.uniroomie.Modles.FlatImages
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.ViewModels.UserProfileViewModel
import com.anshuman.uniroomie.databinding.FragmentFlatImagesBinding

class FlatImagesFragment3 : Fragment() {
    private var _binding: FragmentFlatImagesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserProfileViewModel
    private val imageViews: List<ImageView> by lazy {
        listOf(binding.image1, binding.image2, binding.image3, binding.image4)
    }
    private val selectedImages: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlatImagesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(UserProfileViewModel::class.java)

        // Set up click listeners for each image view
        imageViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                selectImageFromGallery(index)
            }
        }

        binding.btnNext.setOnClickListener {
            if (selectedImages.isNotEmpty()) {
                val flatImages = FlatImages(selectedImages)
                viewModel.updateFlatImages(flatImages)
                findNavController().navigate(R.id.action_flatImagesFragment_to_flatInfoFragment2)
            }
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { handleImageResult(it, selectedImageIndex) }
    }

    private var selectedImageIndex: Int = 0

    private fun selectImageFromGallery(index: Int) {
        selectedImageIndex = index
        imagePickerLauncher.launch("image/*")
    }

    private fun handleImageResult(uri: Uri, index: Int) {
        try {
            // Display the selected image in the ImageView
            imageViews[index].setImageURI(uri)

            // Convert Uri to String (path)
            val uriString = uri.toString()

            // Add to selectedImages list
            if (selectedImages.size <= 3) { // To limit to 4 images
                selectedImages.add(uriString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
