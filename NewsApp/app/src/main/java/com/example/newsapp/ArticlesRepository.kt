package com.example.newsapp

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ArticlesRepository {
    private val api: Api
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun getWSJArticles(onSuccess:(articles:List<Article>) -> Unit, onError: () -> Unit) {
        api.getWSJArticles()
            .enqueue(object : Callback<GetArticlesResponse> {
                override fun onResponse(call: Call<GetArticlesResponse>, response: Response<GetArticlesResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.articles)
                        } else {
                            onError.invoke()
                        }
                    }
                    else{
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetArticlesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

}