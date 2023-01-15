package com.example.challenge.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge.R
import com.example.challenge.data.request.SendReelsRequest
import com.example.challenge.databinding.ActivitySendVideoBinding
import com.example.challenge.network.RetrofitBuilder
import okhttp3.MediaType
import retrofit2.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

class SendVideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySendVideoBinding
    private var category = "댄스"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySendVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val path = intent.getStringExtra("path")
        val auth = intent.getStringExtra("auth")

        binding.sendReels.setOnClickListener {
            val file = File(path!!)
//            video/*
            val requestFile = RequestBody.create("video/*".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)


            RetrofitBuilder().sendReelsService.sendReels(auth!!, SendReelsRequest(binding.titleEditText.text.toString(), category), body).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@SendVideoActivity, "릴스를 성공적으로 업로드했습니다.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SendVideoActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Log.d("TAG", "onResponse: ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t as Exception}", t.cause)
                }

            })
        }

        binding.thumbNailSendVideo.setImageBitmap(createThumbnail(path, this))
        binding.exitSend.setOnClickListener { finish() }
        setCategory()
    }

    private fun setCategory() {
        val dance = binding.dance
        val sing = binding.sing
        val cook = binding.cook
        val play = binding.play
        val other = binding.other
        binding.dance.setOnClickListener {
            category = dance.text.toString()
            dance.setBackgroundResource(R.drawable.tab_select_bg)
            sing.setBackgroundResource(R.drawable.tab_bg)
            cook.setBackgroundResource(R.drawable.tab_bg)
            play.setBackgroundResource(R.drawable.tab_bg)
            other.setBackgroundResource(R.drawable.tab_bg)

            dance.setTextColor(Color.WHITE)
            sing.setTextColor(Color.BLACK)
            cook.setTextColor(Color.BLACK)
            play.setTextColor(Color.BLACK)
            other.setTextColor(Color.BLACK)
        }

        binding.sing.setOnClickListener {
            category = sing.text.toString()
            sing.setBackgroundResource(R.drawable.tab_select_bg)
            dance.setBackgroundResource(R.drawable.tab_bg)
            cook.setBackgroundResource(R.drawable.tab_bg)
            play.setBackgroundResource(R.drawable.tab_bg)
            other.setBackgroundResource(R.drawable.tab_bg)

            dance.setTextColor(Color.BLACK)
            sing.setTextColor(Color.WHITE)
            cook.setTextColor(Color.BLACK)
            play.setTextColor(Color.BLACK)
            other.setTextColor(Color.BLACK)
        }

        binding.cook.setOnClickListener {
            category = cook.text.toString()
            cook.setBackgroundResource(R.drawable.tab_select_bg)
            dance.setBackgroundResource(R.drawable.tab_bg)
            sing.setBackgroundResource(R.drawable.tab_bg)
            play.setBackgroundResource(R.drawable.tab_bg)
            other.setBackgroundResource(R.drawable.tab_bg)

            cook.setTextColor(Color.WHITE)
            sing.setTextColor(Color.BLACK)
            dance.setTextColor(Color.BLACK)
            play.setTextColor(Color.BLACK)
            other.setTextColor(Color.BLACK)
        }

        binding.play.setOnClickListener {
            category = play.text.toString()
            play.setBackgroundResource(R.drawable.tab_select_bg)
            dance.setBackgroundResource(R.drawable.tab_bg)
            sing.setBackgroundResource(R.drawable.tab_bg)
            cook.setBackgroundResource(R.drawable.tab_bg)
            other.setBackgroundResource(R.drawable.tab_bg)

            play.setTextColor(Color.WHITE)
            dance.setTextColor(Color.BLACK)
            cook.setTextColor(Color.BLACK)
            sing.setTextColor(Color.BLACK)
            other.setTextColor(Color.BLACK)
        }

        binding.other.setOnClickListener {
            category = other.text.toString()
            other.setBackgroundResource(R.drawable.tab_select_bg)
            dance.setBackgroundResource(R.drawable.tab_bg)
            sing.setBackgroundResource(R.drawable.tab_bg)
            cook.setBackgroundResource(R.drawable.tab_bg)
            play.setBackgroundResource(R.drawable.tab_bg)

            other.setTextColor(Color.WHITE)
            dance.setTextColor(Color.BLACK)
            cook.setTextColor(Color.BLACK)
            sing.setTextColor(Color.BLACK)
            play.setTextColor(Color.BLACK)
        }
    }

    private fun createThumbnail(path: String?, context: Context): Bitmap? {
        var mediaMetadataRetriever: MediaMetadataRetriever? = null
        var bitmap: Bitmap? = null

        try {
            mediaMetadataRetriever = MediaMetadataRetriever()

            val file = File(path!!)
            mediaMetadataRetriever.setDataSource(context, Uri.fromFile(file))
            bitmap = mediaMetadataRetriever.getFrameAtTime(1000000, MediaMetadataRetriever.OPTION_CLOSEST)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mediaMetadataRetriever?.release()
        }

        return bitmap
    }
}