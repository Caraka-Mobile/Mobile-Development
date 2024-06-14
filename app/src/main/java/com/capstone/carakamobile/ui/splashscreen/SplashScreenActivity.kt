package com.capstone.carakamobile.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.capstone.carakamobile.SignInActivity
import com.capstone.carakamobile.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.note.alpha = 0f
        binding.note.animate().setDuration(3000).alpha(1f).withEndAction{
            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}