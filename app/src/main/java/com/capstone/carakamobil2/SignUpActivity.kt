package com.capstone.carakamobil2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.carakamobil2.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carakamobile-default-rtdb.firebaseio.com/")
//        database = FirebaseDatabase.getInstance().getReference("https://carakamobile-default-rtdb.firebaseio.com")
        firebaseAuth = FirebaseAuth.getInstance()


        binding.signupTextView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        binding.signupButton.setOnClickListener {
            val username = binding.signupUsername.text.toString()
            val email = binding.signupEmail.text.toString()
            val pass = binding.signupPass.text.toString()
            val confirmpass = binding.signupConfirmpass.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmpass.isNotEmpty()){
                if (pass == confirmpass){
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful){
                            databaseRef.child("users").child(email).child("username").setValue(username)
                            databaseRef.child("users").child(email).child("email").setValue(email)
                            databaseRef.child("users").child(email).child("pass").setValue(pass)

                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)

                            Toast.makeText(this, "User registered succesfully", Toast.LENGTH_SHORT).show()
                        } else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}