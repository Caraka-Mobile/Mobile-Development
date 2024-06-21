package com.capstone.carakamobile.ui.camera

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.capstone.carakamobile.databinding.FragmentCameraBinding
import com.capstone.carakamobile.ml.ModelC
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File

class CameraFragment : Fragment() {
    private lateinit var binding: FragmentCameraBinding

    private lateinit var bitmap: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(layoutInflater)
        val view = binding.root

        binding.cameraBtn.isEnabled = true

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA),102)
        } else {
            binding.cameraBtn.isEnabled = true
        }

        binding.cameraBtn.setOnClickListener {
            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i,101)
        }

        val labels = requireActivity().application.assets.open("labels.txt").bufferedReader().readLines()

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(150, 150, ResizeOp.ResizeMethod.BILINEAR))
            .add(NormalizeOp(0.0f, 1.0f))
            .build()

        binding.selectBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }
            startActivityForResult(intent, 100)
        }

        binding.analyzeBtn.setOnClickListener {
            if (::bitmap.isInitialized) {
                var tensorImage = TensorImage(DataType.FLOAT32)
                tensorImage.load(bitmap)
                tensorImage = imageProcessor.process(tensorImage)

                val model = ModelC.newInstance(requireContext())

                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
                inputFeature0.loadBuffer(tensorImage.buffer)

                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

                val maxIdx = outputFeature0.indices.maxByOrNull { outputFeature0[it] } ?: -1

                if (maxIdx != -1) {
                    binding.resView.text = "The letter above is : \n(${labels[maxIdx]})"
                }

                model.close()
            }
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.data != null) {
            val uri: Uri? = data.data
            bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            binding.imageView.setImageBitmap(bitmap)
        }
        if (requestCode == 101) {
            var pic : Bitmap? = data?.getParcelableExtra<Bitmap>("data")
            binding.imageView.setImageBitmap(pic)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 102 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            binding.imageView.isEnabled = true
        }
    }
}