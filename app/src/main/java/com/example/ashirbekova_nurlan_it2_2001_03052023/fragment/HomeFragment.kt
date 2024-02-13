package com.example.ashirbekova_nurlan_it2_2001_03052023.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ashirbekova_nurlan_it2_2001_03052023.databinding.FragmentHomeBinding
import com.example.ashirbekova_nurlan_it2_2001_03052023.retrofit.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit= Retrofit.Builder()
            .baseUrl("https://catfact.ninja").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val mainApi = retrofit.create(MainApi::class.java)



        binding.getbtn.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val list = mainApi.getFact()

                activity?.runOnUiThread {
                    binding.apply {
                        factText.text = list.fact
                    }
                }
            }

        }

        binding.resetbtn.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                activity?.runOnUiThread {
                    binding.apply {
                        factText.text = "Please, press GET button for getting fact!"
                    }
                }
            }

        }
    }



}