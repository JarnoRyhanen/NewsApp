package com.home.newsapp.data

import androidx.room.withTransaction
import com.home.newsapp.api.NewsApi
import com.home.newsapp.ui.SortOrder
import com.home.newsapp.util.networkBoundResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

private const val TAG = "NewsRepository"

class NewsRepository @Inject constructor(
    private val api: NewsApi,
    private val dataBase: NewsDataBase
) {
    private val dao = dataBase.newsDao()


    fun getNews(query: MutableStateFlow<String>, sortOrder: MutableStateFlow<SortOrder>) = networkBoundResource(
        query = {
            val newsFlow = combine(
                query,
                sortOrder
            ) { query, sortOrder ->
                Pair(query, sortOrder)
            }.flatMapLatest { (query, sortOrder) ->
                    dao.getNews(query, sortOrder)
                }
            newsFlow
        },
        fetch = {
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

