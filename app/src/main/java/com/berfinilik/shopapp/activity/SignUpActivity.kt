package com.berfinilik.shopapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.berfinilik.shopapp.databinding.ActivitySignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

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
                Snackbar.make(binding.root, "Lütfen tüm alanları doldurun", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != repassword) {
                Snackbar.make(binding.root, "Parolalar eşleşmiyor", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Snackbar.make(binding.root, "Kayıt başarılı.Giriş yapabilirsiniz.", Snackbar.LENGTH_SHORT).show()
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                    } else {
                        Snackbar.make(binding.root, "Kayıt başarısız.", Snackbar.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
