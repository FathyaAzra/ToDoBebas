package com.example.todobebas;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.todobebas.databinding.ActivityTugasBinding;
import com.example.todobebas.ui.tugas.TugasViewModel;

public class TugasActivity extends AppCompatActivity {

    // Binding untuk mengakses elemen UI di activity_tugas.xml
    private ActivityTugasBinding binding;
    private TugasViewModel tugasViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Inisialisasi binding untuk activity_tugas.xml
        binding = ActivityTugasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inisialisasi ViewModel
        tugasViewModel = new ViewModelProvider(this).get(TugasViewModel.class);

        // Contoh penggunaan ViewModel dengan LiveData
        // tugasViewModel.getText().observe(this, text -> binding.textView.setText(text));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null; // Hapus binding untuk menghindari kebocoran memori
    }
}
