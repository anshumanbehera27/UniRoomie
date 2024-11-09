package com.anshuman.uniroomie.Screens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.anshuman.uniroomie.MainActivity
import com.anshuman.uniroomie.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splsh_screen)

        // App The 30 Sec loop on This Screen
        Handler().postDelayed({
            // Start the main activity after the splash screen
            startActivity(Intent(this, WelcomeScreen::class.java))
            finish()  // Close the SplashActivity
        }, 3000)  // Duration of splash screen in milliseconds
    }

    }
