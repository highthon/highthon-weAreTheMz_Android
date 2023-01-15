package com.example.challenge.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.challenge.R
import com.example.challenge.data.request.SignUpRequest
import com.example.challenge.databinding.ActivitySignUpBinding
import com.example.challenge.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        binding.signUpBtn.setOnClickListener {
            val email = binding.emailSignUp.text.toString()
            val password = binding.passwordSignUp.text.toString()
            val name = binding.nickNameSignUp.text.toString()

            if (password == binding.passwordCheckSignUp.text.toString()) {
                RetrofitBuilder().signUpService.signUp(
                    SignUpRequest(email, password, name)
                ).enqueue(object :Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.e("TAG", "onFailure: ${t as Exception}", t.cause)
                    }

                })
            }
        }

        setContentView(binding.root)
    }
}