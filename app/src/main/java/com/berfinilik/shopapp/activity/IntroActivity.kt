package com.berfinilik.shopapp.activity

import android.content.Intent
import android.os.Bundle
import com.berfinilik.shopapp.databinding.ActivityIntroBinding

class  IntroActivity : BaseActivity() {
    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this@IntroActivity, SignInActivity::class.java))
        }

        binding.signUptv.setOnClickListener {
            startActivity(Intent(this@IntroActivity, SignUpActivity::class.java))

        }

    }
}