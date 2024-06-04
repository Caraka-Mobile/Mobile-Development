package com.capstone.carakamobil2

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.capstone.carakamobil2.databinding.ActivityHomeBinding
import com.capstone.carakamobil2.ui.about.AboutFragment
import com.capstone.carakamobil2.ui.camera.CameraFragment
import com.capstone.carakamobil2.ui.home.HomeFragment
import com.capstone.carakamobil2.ui.paint.PaintFragment
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        firebaseAuth = FirebaseAuth.getInstance()

        val textView = findViewById<TextView>(R.id.name)
        val user = firebaseAuth.currentUser

        if (user != null) {
            val userName = user.displayName
            textView.text = "Welcome, " + userName
        } else {
            // Handle the case where the user is not signed in
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.bottom_home -> replaceFragment(HomeFragment())
                R.id.bottom_camera -> replaceFragment(CameraFragment())
                R.id.bottom_paint -> replaceFragment(PaintFragment())
                R.id.bottom_about -> replaceFragment(AboutFragment())

                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}