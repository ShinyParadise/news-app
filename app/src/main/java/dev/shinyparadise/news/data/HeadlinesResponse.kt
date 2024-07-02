package dev.shinyparadise.news.data

import kotlinx.serialization.Serializable

@Serializable
data class HeadlinesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
