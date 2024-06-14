package com.capstone.carakamobile.ui.whiteboard

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
import com.capstone.carakamobile.ui.whiteboard.WhiteboardView.Companion.colorList
import com.capstone.carakamobile.ui.whiteboard.WhiteboardView.Companion.currentBrush
import com.capstone.carakamobile.ui.whiteboard.WhiteboardView.Companion.pathList

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

        val redBtn = binding.redColor
        val blackBtn = binding.blackColor
        val blueBtn = binding.blueColor
        val eraserBtn = binding.eraser

        redBtn.setOnClickListener {
            paintBrush.color = Color.RED
            currentColor(paintBrush.color)
            Toast.makeText(requireContext(), "Clicked Red Button", Toast.LENGTH_SHORT).show()
        }

        blackBtn.setOnClickListener {
            paintBrush.color = Color.BLACK
            currentColor(paintBrush.color)
            Toast.makeText(requireContext(), "Clicked Black Button", Toast.LENGTH_SHORT).show()
        }

        blueBtn.setOnClickListener {
            paintBrush.color = Color.BLUE
            currentColor(paintBrush.color)
            Toast.makeText(requireContext(), "Clicked Blue Button", Toast.LENGTH_SHORT).show()
        }

        eraserBtn.setOnClickListener {
            pathList.clear()
            colorList.clear()
            path.reset()
            Toast.makeText(requireContext(), "Clicked Eraser Button", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun currentColor(color: Int) {
        currentBrush = color
        path = Path()
    }
}