package com.home.newsapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
public class NewsApplication : Application() {

    private val context: Context = this

  public fun getContext(): Context {
        return context
    }
}