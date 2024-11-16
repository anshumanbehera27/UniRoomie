package com.anshuman.uniroomie.Fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.anshuman.uniroomie.Screens.LoginScreen

import com.anshuman.uniroomie.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth


class profile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.topAppBar.title = "Profile"


        // Optional: Set up logout button
        binding.btnlogout.setOnClickListener {
           logout()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun logout() {
        // Sign out from Firebase
        FirebaseAuth.getInstance().signOut()

        // Navigate to the LoginActivity
        val intent = Intent(activity, LoginScreen::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clears the back stack
        startActivity(intent)
    }


}
