package com.capstone.carakamobile

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.capstone.carakamobile.databinding.ActivityHomeBinding
import com.capstone.carakamobile.ui.about.AboutFragment
import com.capstone.carakamobile.ui.home.HomeFragment
import com.capstone.carakamobile.ui.whiteboard.WhiteboardFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        // Load the default fragment
        loadFragment(HomeFragment())

        bottomNavigationView.setOnItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.bottom_home -> fragment = HomeFragment()
                R.id.bottom_about -> fragment = AboutFragment()
                R.id.bottom_paint -> fragment = WhiteboardFragment()
            }
            if (fragment != null) {
                loadFragment(fragment)
                true
            } else {
                false
            }
        }

        firebaseAuth = FirebaseAuth.getInstance()
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}