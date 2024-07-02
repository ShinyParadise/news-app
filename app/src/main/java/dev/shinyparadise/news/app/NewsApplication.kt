package dev.shinyparadise.news.app

import android.app.Application
import dev.shinyparadise.news.app.di.AppComponent
import dev.shinyparadise.news.app.di.DaggerAppComponent

class NewsApplication : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}
