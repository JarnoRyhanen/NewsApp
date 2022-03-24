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
import com.home.newsapp.data.PreferencesManager
import com.home.newsapp.data.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    app: Application,
    repository: NewsRepository,
    private val preferencesManager: PreferencesManager
) : AndroidViewModel(app) {

    private val context = app.applicationContext

    val searchQuery = MutableStateFlow("")
    private val preferencesFlow = preferencesManager.preferencesFlow

    val news = repository.getNews(searchQuery ,preferencesFlow).asLiveData()

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onNewsClicked(news: News) = viewModelScope.launch {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(context, intent, null)
    }
}