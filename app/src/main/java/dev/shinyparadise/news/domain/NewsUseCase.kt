package dev.shinyparadise.news.domain

import dev.shinyparadise.news.data.Article
import dev.shinyparadise.news.data.NewsGateway
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val newsGateway: NewsGateway
) {
    suspend fun getNews(q: String): List<Article> {
        val response = newsGateway.getTopHeadlines(q)
        return response.articles
    }
}
