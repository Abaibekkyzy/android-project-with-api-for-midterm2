package com.example.ashirbekova_nurlan_it2_2001_03052023.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface MainApi {

    @GET("breeds")
    suspend fun getAllFacts(): Breeds

    @GET("fact")
    suspend fun getFact(): Cat
}