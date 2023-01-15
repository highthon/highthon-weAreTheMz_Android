package com.example.challenge.view.screen

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.challenge.R
import com.example.challenge.data.response.ReelsListResponse
import com.example.challenge.databinding.FragmentHomeBinding
import com.example.challenge.network.RetrofitBuilder
import com.example.challenge.view.adapter.HomePickAdapter
import com.example.challenge.view.adapter.ReelsListAdapter
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        // textView 부분 색상 변경
        val ssb = SpannableStringBuilder(binding.mzPickText.text.toString())
        ssb.setSpan(ForegroundColorSpan(Color.parseColor("#641dff")),0 , 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.mzPickText.text = ssb

//        initReelsListRecycler()

        val list = arrayListOf(R.drawable.r, R.drawable.r, R.drawable.r, R.drawable.r, R.drawable.r)
        binding.viewPagerReelsRecycler.adapter = HomePickAdapter(list)
        binding.viewPagerReelsRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.reelsListPagerRecycler.adapter = HomePickAdapter(list)
        binding.reelsListPagerRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        return binding.root
    }

    private fun initReelsListRecycler() {
        val auth = arguments?.getString("auth")!!
        Log.d("TAG", "initReelsListRecycler: $auth")
        RetrofitBuilder().reelsListService.getReelsList(auth)
            .enqueue(object :Callback<ReelsListResponse> {
                override fun onResponse(call: Call<ReelsListResponse>, response: Response<ReelsListResponse>) {
                    if (response.isSuccessful) {
                        Log.d("TAG", "onResponse: ${response.body()?.list!!}")
                        val adapter = ReelsListAdapter(requireContext(), response.body()?.list!!)

                        binding.reelsListPagerRecycler.adapter = adapter
                        binding.reelsListPagerRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    }
                }

                override fun onFailure(call: Call<ReelsListResponse>, t: Throwable) {
                    Log.e("ERROR", "onFailure: ${t as Exception}", t.cause)
                }

            })
    }
}