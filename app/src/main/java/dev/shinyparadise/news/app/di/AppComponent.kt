package dev.shinyparadise.news.app.di

import dagger.Component
import dev.shinyparadise.news.app.di.modules.NetworkModule
import dev.shinyparadise.news.app.di.modules.ViewModelModule
import dev.shinyparadise.news.ui.MainActivity

@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}
