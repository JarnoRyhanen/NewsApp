package com.home.newsapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsDao {

    fun getNews(query: String, sortOrder: SortOrder): Flow<List<News>> =
        when(sortOrder){
            SortOrder.BY_TITLE -> getNewsSortedByTitle(query)
            SortOrder.BY_NEWSSITE -> getNewsSortedByNewsSite(query)
        }

    @Query("SELECT * FROM news WHERE " +
            " title LIKE '%' || :searchQuery ||'%' ORDER BY publishedAt DESC")
    fun getNewsSortedByTitle(searchQuery: String): Flow<List<News>>

    @Query("SELECT * FROM news WHERE " +
            " newsSite LIKE '%' || :searchQuery ||'%' ORDER BY publishedAt DESC")
    fun getNewsSortedByNewsSite(searchQuery: String): Flow<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<News>)

    @Query("DELETE FROM news")
    suspend fun deleteAllNews()
}