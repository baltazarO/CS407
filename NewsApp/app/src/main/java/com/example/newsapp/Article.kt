package com.example.newsapp

import com.google.gson.annotations.SerializedName
import java.util.*

data class Article(
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val urlToImage: String,
    @SerializedName("publishedAt") val publishedAt: Date,
    @SerializedName("content") val content: String
)
