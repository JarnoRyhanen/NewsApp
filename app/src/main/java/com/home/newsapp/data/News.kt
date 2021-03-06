package com.home.newsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    @PrimaryKey val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val publishedAt: String
) {
    val formattedDate: String
    get() = String.format("${publishedAt.dropLast(14)} ${publishedAt.drop(11).dropLast(8)}")
}

