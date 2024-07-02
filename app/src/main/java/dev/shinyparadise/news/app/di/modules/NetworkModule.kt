package dev.shinyparadise.news.app.di.modules

import dagger.Module
import dagger.Provides
import dev.shinyparadise.news.data.NewsGateway
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
class NetworkModule {
    @Provides
    fun provideNewsGateway(): NewsGateway  {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(Json.asConverterFactory(
                "application/json".toMediaType()
            ))
            .build()
            .create(NewsGateway::class.java)
    }
}
