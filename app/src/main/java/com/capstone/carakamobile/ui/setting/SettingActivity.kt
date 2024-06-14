package com.capstone.carakamobile.ui.setting

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.carakamobile.HomeActivity
import com.capstone.carakamobile.R
import com.capstone.carakamobile.SignInActivity
import com.capstone.carakamobile.User
import com.capstone.carakamobile.databinding.ActivitySettingBinding
import com.capstone.carakamobile.databinding.CustomDialogBoxBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var database: DatabaseReference
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance().getReference("users")

        getDatabase()

        binding.settingLogout.setOnClickListener {
            showCustomDialog()
        }

        binding.buttonBack.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun getDatabase() {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            currentUser.email?.let { email ->
                // Update TextViews with user email
                val emailTextView = findViewById<TextView>(R.id.set_email)
                emailTextView.text = email
            }
            currentUser.displayName?.let { displayName ->
                // Update TextViews with user display name
                val nameTextView = findViewById<TextView>(R.id.set_username)
                nameTextView.text = displayName
            }
        }
//        binding.setUsername.text = ${user?.displayName}
//        Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()

    }

    private fun showCustomDialog() {
        val binding = CustomDialogBoxBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(binding.root)
            .create()

        binding.btnDialogSure.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
            dialog.dismiss()
        }

        binding.btnDialogCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}