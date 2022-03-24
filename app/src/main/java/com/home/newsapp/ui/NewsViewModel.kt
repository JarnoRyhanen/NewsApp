package com.home.newsapp.ui

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.home.newsapp.data.News
import com.home.newsapp.data.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    app: Application,
    repository: NewsRepository
) : AndroidViewModel(app) {

    private val context = app.applicationContext
    val sortOrder = MutableStateFlow(SortOrder.BY_TITLE)

    val searchQuery = MutableStateFlow("")
    val news = repository.getNews(searchQuery, sortOrder).asLiveData()

    fun onNewsClicked(news: News) = viewModelScope.launch {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(context, intent, null)
    }
}

enum class SortOrder {
    BY_TITLE, BY_NEWSSITE
}
