package com.anshuman.uniroomie.Screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.anshuman.uniroomie.MainActivity
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.databinding.ActivitySignUpScreenBinding

class SignUpScreen : AppCompatActivity() {
    lateinit var binding: ActivitySignUpScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = ActivitySignUpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
        }

        binding.tvloginpage.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            Toast.makeText(this, "Redrict to Login page", Toast.LENGTH_SHORT).show()
        }

    }
}