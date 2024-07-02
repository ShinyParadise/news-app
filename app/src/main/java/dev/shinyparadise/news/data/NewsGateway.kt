package dev.shinyparadise.news.data

import dev.shinyparadise.news.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsGateway {
    @GET("/v2/top-headlines")
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    suspend fun getTopHeadlines(
        @Query("q") q: String,
        @Query("country") country: String = "ru",
        @Query("category") category: String = "technology",
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1
    ): HeadlinesResponse
}
