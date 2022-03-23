package com.home.newsapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [News::class], version = 1)
abstract class NewsDataBase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}