package com.example.ashirbekova_nurlan_it2_2001_03052023.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ashirbekova_nurlan_it2_2001_03052023.databinding.FragmentListProductBinding
import com.example.ashirbekova_nurlan_it2_2001_03052023.retrofit.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ListProductFragment : Fragment() {
    private lateinit var binding: FragmentListProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListProductBinding.inflate(layoutInflater)

        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://catfact.ninja").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val mainApi = retrofit.create(MainApi::class.java)
        var searchText: EditText = binding.t1
        var text1 = binding.t2
        var text2 = binding.t3

        binding.button2.setOnClickListener(){
            CoroutineScope(Dispatchers.IO).launch {
            val list = mainApi.getAllFacts()
                activity?.runOnUiThread{
                    var t1: String = ""
                    var t2: String = ""
                    for(i in 0..(list.data.size-1)){
                        //Log.d("My", list.data[i].country)
                        if(list.data[i].country == searchText.text.toString()){
                            Log.d("My", "find")
                            t1 = list.data[i].country
                            t2= list.data[i].breed
                        }


                    }

                    if(t1==""){
                        text1.text = "Not found"
                        text2.text = "Not found"
                    }else{
                        text1.text = t1
                        text2.text = t2
                    }


                }
            }
        }
        binding.button1.setOnClickListener(){
            text1.text = ""
            text2.text = ""
            binding.t1.setText("search")
        }



    }


}