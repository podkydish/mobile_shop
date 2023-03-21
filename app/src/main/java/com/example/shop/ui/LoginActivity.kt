package com.example.shop.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shop.databinding.LoginActivityBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

    fun toMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}