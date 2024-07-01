package com.berfinilik.shopapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.berfinilik.shopapp.databinding.ActivityUserinfoBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserinfoBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val TAG = "UserInfoActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        fetchUserInfo()
        setVariable()

        binding.btnAyakkabiNoGuncelle.setOnClickListener {
            updateAyakkabiNumarasi()
        }
        binding.btnMailGuncelle.setOnClickListener {
            updateEmail()
        }

        binding.btnCepGuncelle.setOnClickListener {
            updateCepTelefonu()
        }
    }

    private fun fetchUserInfo() {
        val userUid = auth.currentUser?.uid

        if (userUid != null) {
            db.collection("users").document(userUid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        binding.adTextView.text = document.getString("Ad")
                        binding.soyadTextView.text = document.getString("Soyad")
                        binding.cepTelTextView.text = document.getString("Cep Telefonu")
                        binding.emailTextView.text = document.getString("E-mail")
                        binding.ayakkabiNoEditText.setText(document.getString("Ayakkabı Numarası"))
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Firestore'dan veri alınırken hata oluştu: $exception")
                    Snackbar.make(binding.root, "Kullanıcı bilgileri alınamadı.", Snackbar.LENGTH_SHORT).show()
                }
        }
    }

    private fun updateFirestore(updates: HashMap<String, String>, successMessage: String) {
        val userUid = auth.currentUser?.uid

        if (userUid != null) {
            db.collection("users").document(userUid)
                .update(updates as Map<String, Any>)
                .addOnSuccessListener {
                    Log.d(TAG, successMessage)
                    Snackbar.make(binding.root, successMessage, Snackbar.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Veri güncellenirken hata oluştu: $exception")
                    Snackbar.make(binding.root, "Veri güncellenemedi", Snackbar.LENGTH_SHORT).show()
                }
        }
    }

    private fun setVariable() {
        binding.back.setOnClickListener { finish() }
    }

    private fun updateAyakkabiNumarasi() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ayakkabı Numarası Güncelle")
        builder.setMessage("Yeni ayakkabı numarasını girin:")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        builder.setPositiveButton("Güncelle") { dialog, which ->
            val yeniAyakkabiNumarasi = input.text.toString()
            binding.ayakkabiNoEditText.setText(yeniAyakkabiNumarasi)

            val updates = hashMapOf(
                "Ayakkabı Numarası" to yeniAyakkabiNumarasi
            )

            updateFirestore(updates, "Ayakkabı numarası güncellendi.")
        }

        builder.setNegativeButton("İptal") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun updateEmail() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("E-mail Güncelle")
        builder.setMessage("Yeni E-mail adresinizi girin:")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        builder.setView(input)

        builder.setPositiveButton("Güncelle") { dialog, which ->
            val yeniEmail = input.text.toString()
            binding.emailTextView.text = yeniEmail

            val updates = hashMapOf(
                "E-mail" to yeniEmail
            )

            updateFirestore(updates, "E-mail adresi güncellendi.")
        }

        builder.setNegativeButton("İptal") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun updateCepTelefonu() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cep Telefonu Güncelle")
        builder.setMessage("Yeni cep telefonu numaranızı girin:")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_PHONE
        builder.setView(input)

        builder.setPositiveButton("Güncelle") { dialog, which ->
            val yeniCepTelefonu = input.text.toString()
            binding.cepTelTextView.text = yeniCepTelefonu

            val updates = hashMapOf(
                "Cep Telefonu" to yeniCepTelefonu
            )

            updateFirestore(updates, "Cep telefonu numarası güncellendi.")
        }

        builder.setNegativeButton("İptal") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }
}