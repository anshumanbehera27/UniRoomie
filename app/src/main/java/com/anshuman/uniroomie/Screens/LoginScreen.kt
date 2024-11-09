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
import com.anshuman.uniroomie.databinding.ActivityLoginScreenBinding

class LoginScreen : AppCompatActivity() {
    lateinit var binding: ActivityLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)


        setContentView(binding.root)

    binding.btnlogin.setOnClickListener {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
    }

    }
}