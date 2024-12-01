package com.example.todobebas.ui.tugas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todobebas.databinding.ActivityMainBinding
import com.example.todobebas.databinding.ActivityNavigationBinding
import com.example.todobebas.databinding.FragmentTugasBinding

class TugasFragment : Fragment() {

    private var _binding: FragmentTugasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    private val _binding: FragmentTugasBinding by lazy {
//        FragmentTugasBinding.inflate(layoutInflater)
//    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val tugasViewModel =
            ViewModelProvider(this).get(TugasViewModel::class.java)

        _binding = FragmentTugasBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        tugasViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}