package com.capstone.carakamobile.ui.about

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.carakamobile.R
import com.capstone.carakamobile.databinding.FragmentAboutBinding
import com.capstone.carakamobile.databinding.FragmentHomeBinding
import com.capstone.carakamobile.ui.setting.SettingActivity
import com.google.firebase.auth.FirebaseAuth

class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(layoutInflater)
        val view = binding.root

        binding.buttonSetting.setOnClickListener {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}