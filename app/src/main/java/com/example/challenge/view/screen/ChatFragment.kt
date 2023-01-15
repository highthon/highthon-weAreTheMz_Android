package com.example.challenge.view.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge.R
import com.example.challenge.data.response.ChatResponse
import com.example.challenge.databinding.FragmentChatBinding
import com.example.challenge.view.adapter.ChatAdapter

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)

        val list = arrayListOf<ChatResponse>()
        list.add(ChatResponse("김지수", "나에게 HYPE BOY 챌린지 보냈어요!"))
        list.add(ChatResponse("유동은", "나에게 새삥 챌린지 보냈어요!"))
        list.add(ChatResponse("이도화", "나에게 Rush hour 챌린지 보냈어요!"))

        binding.chatRecyclerView.adapter = ChatAdapter(requireContext(), list)
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }
}