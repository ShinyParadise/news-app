package dev.shinyparadise.news.ui.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.shinyparadise.news.data.Article
import dev.shinyparadise.news.domain.NewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    data class MainUiState(
        val articles: List<Article> = emptyList(),
        val searchQuery: String = ""
    )

    init {
        viewModelScope.launch {
            updateNews()
        }
    }

    fun search() {
        viewModelScope.launch {
            try {
                updateNews()
            } catch (e: Exception) {
                Log.d(TAG, "search: $e")
            }
        }
    }

    private suspend fun updateNews() {
        val news = newsUseCase.getNews(uiState.value.searchQuery)
        withContext(Dispatchers.Main) {
            _uiState.value = _uiState.value.copy(articles = news)
        }
    }

    fun changeSearchQuery(q: String) {
        _uiState.value = _uiState.value.copy(searchQuery = q)
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}
