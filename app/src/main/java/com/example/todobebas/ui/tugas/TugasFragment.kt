package com.example.todobebas.ui.tugas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todobebas.databinding.FragmentTugasBinding

class TugasFragment : Fragment() {

    // Binding untuk mengakses elemen UI di fragment_tugas.xml
    private var _binding: FragmentTugasBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // ViewModel untuk fragment ini
        val tugasViewModel = ViewModelProvider(this).get(TugasViewModel::class.java)

        // Inisialisasi binding
        _binding = FragmentTugasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Contoh penggunaan ViewModel dengan LiveData (komentarnya tetap untuk referensi)
//        val textView: TextView = binding.textHome
//        tugasViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Menghapus binding saat fragment dihancurkan untuk mencegah kebocoran memori
        _binding = null
    }
}