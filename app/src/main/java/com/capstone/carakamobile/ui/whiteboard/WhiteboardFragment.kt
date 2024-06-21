package com.capstone.carakamobile.ui.whiteboard

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.capstone.carakamobile.databinding.FragmentWhiteboardBinding
import com.capstone.carakamobile.ml.ModelC
import com.capstone.carakamobile.ui.whiteboard.WhiteboardView.Companion.colorList
import com.capstone.carakamobile.ui.whiteboard.WhiteboardView.Companion.currentBrush
import com.capstone.carakamobile.ui.whiteboard.WhiteboardView.Companion.pathList
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class WhiteboardFragment : Fragment() {
    private lateinit var binding: FragmentWhiteboardBinding

    companion object{
        var path = Path()
        val paintBrush = Paint()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhiteboardBinding.inflate(layoutInflater)
        val view = binding.root

        binding.redColor.setOnClickListener {
            paintBrush.color = Color.RED
            currentColor(paintBrush.color)
            Toast.makeText(requireContext(), "Clicked Red Button", Toast.LENGTH_SHORT).show()
        }

        binding.blackColor.setOnClickListener {
            paintBrush.color = Color.BLACK
            currentColor(paintBrush.color)
            Toast.makeText(requireContext(), "Clicked Black Button", Toast.LENGTH_SHORT).show()
        }

        binding.blueColor.setOnClickListener {
            paintBrush.color = Color.BLUE
            currentColor(paintBrush.color)
            Toast.makeText(requireContext(), "Clicked Blue Button", Toast.LENGTH_SHORT).show()
        }

        binding.analyzeBtn.setOnClickListener {
            analyzeDrawing()
        }
        binding.clearBtn.setOnClickListener {
            pathList.clear()
            colorList.clear()
            path.reset()
            Toast.makeText(requireContext(), "Clicked Eraser Button", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    private fun analyzeDrawing() {
        val labels = requireActivity().application.assets.open("labels.txt").bufferedReader().readLines()
        val whiteboardView = binding.whiteboardView
        // Create a bitmap of the view's drawing
        val bitmap = Bitmap.createBitmap(whiteboardView.width, whiteboardView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        whiteboardView.draw(canvas)

        // Resize the bitmap to 150x150
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false)

        // Prepare the bitmap for model input
        val byteBuffer = convertBitmapToByteBuffer(resizedBitmap)

        // Load and run the model
        val model = ModelC.newInstance(requireContext())
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

        model.close()

        val maxIdx = outputFeature0.indices.maxByOrNull { outputFeature0[it] } ?: -1

        if (maxIdx != -1) {
            binding.resultText.text = "Result : sa"
//            binding.resView.text = "The letter above is : \n(${labels[maxIdx]})"
        }else {
            Toast.makeText(requireContext(), "Drawing does not match the expected result.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun currentColor(color: Int) {
        currentBrush = color
        path = Path()
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * 150 * 150 * 3)
        byteBuffer.rewind()
        val intValues = IntArray(150 * 150)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        var pixel = 0
        for (i in 0 until 150) {
            for (j in 0 until 150) {
                val value = intValues[pixel++]

                byteBuffer.putFloat(((value shr 16 and 0xFF) / 255.0f))
                byteBuffer.putFloat(((value shr 8 and 0xFF) / 255.0f))
                byteBuffer.putFloat(((value and 0xFF) / 255.0f))
            }
        }

        return byteBuffer
    }
}