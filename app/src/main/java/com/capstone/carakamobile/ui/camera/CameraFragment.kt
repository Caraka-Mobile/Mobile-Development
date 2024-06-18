package com.capstone.carakamobile.ui.camera

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.capstone.carakamobile.databinding.FragmentCameraBinding

class CameraFragment : Fragment() {
    private lateinit var binding: FragmentCameraBinding
    private lateinit var bitmap: Bitmap
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(layoutInflater)
        val view = binding.root

        binding.clickMe.setOnClickListener {
            val intent = Intent(activity, CameraActivity::class.java)
            startActivity(intent)
        }

//        binding.selectBtn.setOnClickListener {
//            val intent = Intent()
//            intent.setAction(Intent.ACTION_GET_CONTENT)
//            intent.setType("image/*")
//            startActivity(intent,100)
//        }
        return view
    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == 100){
//            var url = data?.data;
//            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,url)
//            imageView.setImageBitmap(bitmap)
//        }
//    }
}