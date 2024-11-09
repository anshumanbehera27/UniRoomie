package com.anshuman.uniroomie.Screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.anshuman.uniroomie.MainActivity
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.databinding.ActivityWelcomeScreenBinding

class WelcomeScreen : AppCompatActivity() {
    lateinit var binding: ActivityWelcomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            val intent = Intent(this, SignUpScreen::class.java)
            startActivity(intent)

        }

    }
}