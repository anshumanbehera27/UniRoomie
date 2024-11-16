package com.anshuman.uniroomie.Screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anshuman.uniroomie.MainActivity
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.databinding.ActivityLoginScreenBinding
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {

    lateinit var binding: ActivityLoginScreenBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance()

        // Set up the login button click listener
        binding.btnlogin.setOnClickListener {
            val email = binding.etloginEmail.text.toString().trim()
            val password = binding.etLoginPassword.text.toString().trim()

            // Validate email and password
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Attempt login with Firebase Authentication
            loginUser(email, password)

            finish()
        }
    }

    private fun loginUser(email: String, password: String) {
        // Sign in with email and password
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login successful
                    val user = mAuth.currentUser
                    if (user != null) {
                        // Redirect to MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                     //   finish() // Optional: Close the login screen after successful login
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle login failure
                    Toast.makeText(this, "Authentication Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
