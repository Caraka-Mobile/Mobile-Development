package com.capstone.carakamobile.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.carakamobile.databinding.FragmentHomeBinding
import com.capstone.carakamobile.ui.quiz.QuizListAdapter
import com.capstone.carakamobile.ui.quiz.QuizModel
import com.capstone.carakamobile.ui.setting.SettingActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var quizModelList : MutableList<QuizModel>
    private lateinit var adapter: QuizListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root

        auth = Firebase.auth

        verifyUsher()

        binding.buttonSetting.setOnClickListener {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }
        quizModelList = mutableListOf()
        getDataFromFirebase()

        return view
    }

    private fun verifyUsher(){
        val user = Firebase.auth.currentUser

        user?.let {
            val name = it.displayName
            binding.nameText.text = "Welcome mas,\n$name"
        }
    }

    private fun setupRecyclerView(){
        binding.progressBar.visibility = View.GONE
        adapter = QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataFromFirebase(){
        binding.progressBar.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference.child("quizzes")
//            .limitToFirst(3)
            .get()
            .addOnSuccessListener { dataSnapshot->
                if(dataSnapshot.exists()){
                    for (snapshot in dataSnapshot.children){
                        val quizModel = snapshot.getValue(QuizModel::class.java)
                        if (quizModel != null) {
                            quizModelList.add(quizModel)
                        }
                    }
                }
                setupRecyclerView()
            }


    }
}