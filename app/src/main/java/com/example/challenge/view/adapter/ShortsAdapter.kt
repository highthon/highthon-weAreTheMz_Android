package com.example.challenge.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge.databinding.ItemReelsBinding

class ShortsAdapter(private val context: Context, private val list: List<String>): RecyclerView.Adapter<ShortsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemReelsBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private val binding: ItemReelsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }
}