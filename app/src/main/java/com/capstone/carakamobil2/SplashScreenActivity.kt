package com.capstone.carakamobil2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.carakamobil2.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
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