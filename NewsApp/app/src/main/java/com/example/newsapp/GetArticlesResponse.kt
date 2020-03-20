package com.example.newsapp

import com.google.gson.annotations.SerializedName

data class GetArticlesResponse(
    @SerializedName("articles") val articles: List<Article>
)
