package com.example.challenge.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.challenge.data.request.SignInRequest
import com.example.challenge.data.response.SignInResponse
import com.example.challenge.databinding.ActivitySignInBinding
import com.example.challenge.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goSignUpBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {
            RetrofitBuilder().signInService.signIn(SignInRequest(binding.email.text.toString(), binding.password.text.toString()))
                .enqueue(object :Callback<SignInResponse> {
                    override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                        if (response.isSuccessful) {
                            startActivity(Intent(this@SignInActivity, MainActivity::class.java)
                                .putExtra("auth", response.body()?.accessToken))
                        }
                    }

                    override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                        Log.e("ERROR", "onFailure: ${t as Exception}", t.cause)
                    }
                })
        }
    }
}