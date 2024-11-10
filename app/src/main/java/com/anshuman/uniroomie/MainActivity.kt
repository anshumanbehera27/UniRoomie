package com.anshuman.uniroomie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.anshuman.uniroomie.Fragements.Home
import com.anshuman.uniroomie.Fragements.chart
import com.anshuman.uniroomie.Fragements.favorite
import com.anshuman.uniroomie.Fragements.profile
import com.anshuman.uniroomie.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit private var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(Home())
        // Add the Bottom Nav to the main Activity
        val bottomNavigationView = binding.bottomNavigation

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home ->{
                    replaceFragment(Home())
                    true
                }
                R.id.favroite -> {
                    replaceFragment(favorite())
                    true
                }
                R.id.chart-> {
                    replaceFragment(chart())
                    true
                }
                R.id.profile ->{
                    replaceFragment(profile())
                    true
                }
                else -> false
            }

        }




    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }
}