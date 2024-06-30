package com.berfinilik.shopapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.berfinilik.shopapp.databinding.ActivityUserinfoBinding
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

        // Kullanıcı bilgilerini Firestore'dan alıp gösterme
        fetchUserInfo()
        setVariable()

        binding.buttonGuncelle.setOnClickListener {
            updateAyakkabiNumarasi()
        }


    }

    private fun fetchUserInfo() {
        // Kullanıcının UID'sine erişilebilir olduğunu varsayalım
        val userUid = auth.currentUser?.uid

        if (userUid != null) {
            db.collection("users").document(userUid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        // TextView'ları kullanıcı bilgileriyle doldur
                        binding.adTextView.text = document.getString("Ad")
                        binding.soyadTextView.text = document.getString("Soyad")
                        binding.cepTelTextView.text = document.getString("Cep Telefonu")
                        binding.emailTextView.text = document.getString("E-mail")
                        binding.ayakkabiNoEditText.setText(document.getLong("Ayakkabı Numarası")?.toString())
                    }
                }
                .addOnFailureListener { exception ->
                    // Hata durumunda kullanıcıyı bilgilendir
                    Log.d(TAG, "Firestore'dan veri alınırken hata oluştu: $exception")
                    Toast.makeText(this, "Kullanıcı bilgileri alınamadı.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun updateFirestore(updates: HashMap<String, Long>, successMessage: String) {
        // Kullanıcının UID'sine erişilebilir olduğunu varsayalım
        val userUid = auth.currentUser?.uid

        if (userUid != null) {
            db.collection("users").document(userUid)
                .update(updates as Map<String, Any>)
                .addOnSuccessListener {
                    // Güncelleme başarılı olduğunda kullanıcıyı bilgilendir
                    Log.d(TAG, successMessage)
                    Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show()

                    // TextView'ları güncellemek isterseniz burada yapabilirsiniz, ancak otomatik olarak güncellenecektir
                }
                .addOnFailureListener { exception ->
                    // Hata durumunda kullanıcıyı bilgilendir
                    Log.d(TAG, "Veri güncellenirken hata oluştu: $exception")
                    Toast.makeText(this, "Veri güncellenemedi.", Toast.LENGTH_SHORT).show()
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
                "Ayakkabı Numarası" to yeniAyakkabiNumarasi.toLong()
            )

            updateFirestore(updates, "Ayakkabı numarası güncellendi.")
        }

        builder.setNegativeButton("İptal") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }
}