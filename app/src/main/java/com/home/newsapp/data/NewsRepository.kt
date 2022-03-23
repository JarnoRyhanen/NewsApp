package com.home.newsapp.data

import androidx.room.withTransaction
import com.home.newsapp.api.NewsApi
import com.home.newsapp.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: NewsApi,
    private val dataBase: NewsDataBase
) {
    private val dao = dataBase.newsDao()

   fun getNews() = networkBoundResource(
       query = {
               dao.getAllNews()
       },
       fetch = {
               delay(2000)
           api.getNews()
       },
       saveFetchResult = { listOfNews ->
           dataBase.withTransaction {
               dao.deleteAllNews()
               dao.insertNews(listOfNews)
           }
       }
   )
}