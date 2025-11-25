package com.example.contoh_sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    
    // Deklarasi variabel untuk SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    
    // Deklarasi variabel untuk UI components
    private lateinit var etNama: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var btnSimpan: Button
    private lateinit var btnMuat: Button
    private lateinit var btnHapus: Button
    private lateinit var tvHasil: TextView
    
    // Konstanta untuk key SharedPreferences
    companion object {
        private const val PREF_NAME = "UserPreferences"
        private const val KEY_NAMA = "nama"
        private const val KEY_EMAIL = "email"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        
        // Inisialisasi UI components
        initViews()
        
        // Set click listeners
        setupListeners()
        
        // Muat data saat aplikasi dibuka (jika ada)
        loadDataOnStart()
    }
    
    private fun initViews() {
        etNama = findViewById(R.id.etNama)
        etEmail = findViewById(R.id.etEmail)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnMuat = findViewById(R.id.btnMuat)
        btnHapus = findViewById(R.id.btnHapus)
        tvHasil = findViewById(R.id.tvHasil)
    }
    
    private fun setupListeners() {
        btnSimpan.setOnClickListener {
            simpanData()
        }
        
        btnMuat.setOnClickListener {
            muatData()
        }
        
        btnHapus.setOnClickListener {
            hapusData()
        }
    }
    
    // Fungsi untuk menyimpan data ke SharedPreferences
    private fun simpanData() {
        val nama = etNama.text.toString().trim()
        val email = etEmail.text.toString().trim()
        
        if (nama.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Nama dan Email tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Simpan data menggunakan SharedPreferences Editor
        editor.putString(KEY_NAMA, nama)
        editor.putString(KEY_EMAIL, email)
        editor.apply() // atau editor.commit()
        
        Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
        
        // Bersihkan input fields
        etNama.text?.clear()
        etEmail.text?.clear()
    }
    
    // Fungsi untuk memuat data dari SharedPreferences
    private fun muatData() {
        val nama = sharedPreferences.getString(KEY_NAMA, "")
        val email = sharedPreferences.getString(KEY_EMAIL, "")
        
        if (nama.isNullOrEmpty() && email.isNullOrEmpty()) {
            tvHasil.text = "Tidak ada data tersimpan"
            Toast.makeText(this, "Tidak ada data tersimpan", Toast.LENGTH_SHORT).show()
        } else {
            val hasil = "Data Tersimpan:\n\nNama: $nama\nEmail: $email"
            tvHasil.text = hasil
            Toast.makeText(this, "Data berhasil dimuat!", Toast.LENGTH_SHORT).show()
        }
    }
    
    // Fungsi untuk menghapus data dari SharedPreferences
    private fun hapusData() {
        editor.remove(KEY_NAMA)
        editor.remove(KEY_EMAIL)
        editor.apply()
        
        // Atau hapus semua data:
        // editor.clear()
        // editor.apply()
        
        tvHasil.text = "Data telah dihapus"
        etNama.text?.clear()
        etEmail.text?.clear()
        
        Toast.makeText(this, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show()
    }
    
    // Fungsi untuk memuat data saat aplikasi dibuka
    private fun loadDataOnStart() {
        val nama = sharedPreferences.getString(KEY_NAMA, "")
        val email = sharedPreferences.getString(KEY_EMAIL, "")
        
        if (!nama.isNullOrEmpty() && !email.isNullOrEmpty()) {
            val hasil = "Data Tersimpan:\n\nNama: $nama\nEmail: $email"
            tvHasil.text = hasil
        }
    }
}