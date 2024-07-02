package dev.shinyparadise.news.app

import android.app.Application
import dev.shinyparadise.news.app.di.DaggerAppComponent

class NewsApp : Application() {
    val component by lazy {
        DaggerAppComponent.create()
    }
}
