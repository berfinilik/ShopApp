package com.berfinilik.shopapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.berfinilik.shopapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.signupbtn.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val repassword = binding.repassword.text.toString()

            if (email.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != repassword) {
                Toast.makeText(this, "Parolalar eşleşmiyor.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Kayıt başarılı!Giriş yapabilirsiniz.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                    } else {
                        val error = (task.exception as? FirebaseAuthException)?.message ?: "Bilinmeyen bir hata oluştu."
                        Toast.makeText(this, "Kayıt başarısız: $error", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
