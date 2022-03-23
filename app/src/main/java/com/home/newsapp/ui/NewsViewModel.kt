package com.home.newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.newsapp.api.NewsApi
import com.home.newsapp.data.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    api: NewsApi
) : ViewModel() {

    private val newsLiveData = MutableLiveData<List<News>>()
    val news: LiveData<List<News>> = newsLiveData

    init {
        viewModelScope.launch {
            val news = api.getNews()
            newsLiveData.value = news
        }
    }
}