package com.anshuman.uniroomie.Fragements

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anshuman.uniroomie.Modles.UserImage
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.ViewModels.UserProfileViewModel
import com.anshuman.uniroomie.databinding.FragmentUserPictureBinding

class UserImageFragment : Fragment() {

    private var _binding: FragmentUserPictureBinding? = null
    private val binding get() = _binding!!

    private lateinit var userProfileViewModel: UserProfileViewModel

    private val PICK_IMAGE_REQUEST = 1
    private val TAKE_PICTURE_REQUEST = 2
    private var selectedImageBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        userProfileViewModel = ViewModelProvider(requireActivity()).get(UserProfileViewModel::class.java)

        // Open Gallery on image input click
        binding.imageprofileinput.setOnClickListener {
            openGallery()
        }

        // Handle Next button click
        binding.next.setOnClickListener {
            if (selectedImageBitmap != null) {
                // Save the selected image (Bitmap) to UserImage
                val userImage = UserImage(selectedImageBitmap!!)
                userProfileViewModel.updateUserImage(userImage)
                Toast.makeText(requireContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_userPictureFragment_to_userPersonalInfoFragment)
            } else {
                Toast.makeText(requireContext(), "Please select an image", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle Back button click
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, TAKE_PICTURE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    val imageUri = data?.data
                    binding.imageprofileinput.setImageURI(imageUri) // Display the selected image
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
                }
                TAKE_PICTURE_REQUEST -> {
                    val photo = data?.extras?.get("data") as Bitmap
                    binding.imageprofileinput.setImageBitmap(photo) // Display the captured image
                    selectedImageBitmap = photo
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
