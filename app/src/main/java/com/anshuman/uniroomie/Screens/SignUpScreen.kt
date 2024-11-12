package com.anshuman.uniroomie.Screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anshuman.uniroomie.MainActivity
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.databinding.ActivitySignUpScreenBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpScreen : AppCompatActivity() {
    lateinit var binding: ActivitySignUpScreenBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance()

        // Sign-up button click listener
        binding.btnSignUp.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etUserEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etPassword2.text.toString().trim()

            // Validate if all fields are filled and passwords match
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proceed with Firebase Authentication if validation is successful
            signUpUser(email, password)
        }

        // Redirect to Login page
        binding.tvloginpage.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            Toast.makeText(this, "Redirecting to Login page", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to sign up the user with Firebase Authentication
    private fun signUpUser(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // User is successfully registered
                    val user = mAuth.currentUser
                    Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()

                    // Navigate to the onboarding activity after successful sign-up
                    val intent = Intent(this, UserOnboardingActivity::class.java)
                    startActivity(intent)
                    finish()  // Finish SignUpScreen activity so user can't navigate back to it
                } else {
                    // Sign-up failed, show an error message
                    Toast.makeText(this, "Sign Up Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
