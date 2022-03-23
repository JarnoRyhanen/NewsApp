package com.home.newsapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsDao {

    @Query("Select * FROM news")
    fun getAllNews(): Flow<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<News>)

    @Query("DELETE FROM news")
    suspend fun deleteAllNews()
}