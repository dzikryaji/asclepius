package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("top-headlines?country=id&category=health&apiKey=${BuildConfig.API_KEY}")
    fun getNews(): Call<NewsResponse>
}