package com.example.challenge.view.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge.data.response.ReelsListResponseItem
import com.example.challenge.databinding.ItemVideoBinding
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class ReelsListAdapter(private val context: Context, private val list: List<ReelsListResponseItem>): RecyclerView.Adapter<ReelsListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val binding: ItemVideoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(reelsListResponseItem: ReelsListResponseItem) {
            binding.itemThumbNail.setImageBitmap(createThumbnail(reelsListResponseItem.videoUrl))
        }
    }

    private fun createThumbnail(path: String?): Bitmap? {
        return try {
            val url = URL(path)
            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}