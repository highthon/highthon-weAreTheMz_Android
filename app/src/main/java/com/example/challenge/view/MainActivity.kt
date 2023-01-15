package com.example.challenge.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.challenge.R
import com.example.challenge.databinding.ActivityMainBinding
import com.example.challenge.view.screen.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_linear, HomeFragment().apply {
            arguments = Bundle().apply {
                Log.d("TAG", "setNavigation: ${intent.getStringExtra("auth")}")
                putString("auth", intent.getStringExtra("auth"))
            }
        }).commit()
        setNavigation()
    }

    private fun setNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_linear, HomeFragment().apply {
                        arguments = Bundle().apply {
                            Log.d("TAG", "setNavigation: ${intent.getStringExtra("auth")}")
                            putString("auth", intent.getStringExtra("auth"))
                        }
                    }).commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.shorts -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_linear, ShortsFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.chat -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_linear, ChatFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_linear, ProfileFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        binding.floatingButton.setOnClickListener {
            Log.d("TAG", "setNavigation auth: ${intent.getStringExtra("auth")}")
            startActivity(Intent(this, RecordActivity::class.java)
                .putExtra("token", intent.getStringExtra("auth")))

        }
    }
}