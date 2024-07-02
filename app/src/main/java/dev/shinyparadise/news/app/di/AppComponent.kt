package dev.shinyparadise.news.app.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dev.shinyparadise.news.app.di.modules.NetworkModule
import dev.shinyparadise.news.app.di.modules.ViewModelModule
import dev.shinyparadise.news.ui.MainActivity

@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
