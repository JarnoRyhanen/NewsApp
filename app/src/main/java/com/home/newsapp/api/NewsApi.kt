package com.home.newsapp.api

import com.home.newsapp.data.News
import retrofit2.http.GET


interface NewsApi {

    companion object {
        const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"
    }

    @GET("articles/?_limit=500")
    suspend fun getNews(): List<News>
}