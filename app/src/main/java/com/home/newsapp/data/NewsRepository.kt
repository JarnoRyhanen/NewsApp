package com.home.newsapp.data

import androidx.room.withTransaction
import com.home.newsapp.api.NewsApi
import com.home.newsapp.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: NewsApi,
    private val dataBase: NewsDataBase
) {
    private val dao = dataBase.newsDao()

    fun getNews(searchQuery: MutableStateFlow<String>, preferencesFlow: Flow<FilterPreferences>) = networkBoundResource(
        query = {
            val newsFlow = combine(
                searchQuery,
                preferencesFlow
            ) { query, filterPreferences ->
                Pair(query, filterPreferences)
            }.flatMapLatest { (query, filterPreferences) ->
                    dao.getNews(query, filterPreferences.sortOrder)
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

