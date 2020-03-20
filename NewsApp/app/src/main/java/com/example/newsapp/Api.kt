package com.example.newsapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("v2/everything")
    fun getWSJArticles(
        @Query("domains") domains: String = "wsj.com",
        @Query("apiKey") apiKey: String = "fbc2fcbb6994460d8c3a58ebd142c38d"
    ): Call<GetArticlesResponse>
}

//https://newsapi.org/v2/everything?domains=wsj.com&apiKey=API_KEY