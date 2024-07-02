package dev.shinyparadise.news.ui.screens.main

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dev.shinyparadise.news.app.navigation.Routes
import dev.shinyparadise.news.data.Article
import dev.shinyparadise.news.domain.NewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
class MainViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val context: Context,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    data class MainUiState(
        val articles: List<Article> = emptyList(),
        val searchQuery: String = "",
        val selectedArticle: Article? = null
    )

    init {
        viewModelScope.launch { updateNews() }
    }

    fun search() {
        viewModelScope.launch { updateNews() }
    }

    fun changeSearchQuery(q: String) {
        _uiState.value = _uiState.value.copy(searchQuery = q)
    }

    fun articleClicked(navController: NavHostController, article: Article) {
        _uiState.value = _uiState.value.copy(selectedArticle = article)
        navController.navigate(Routes.article)
    }

    private suspend fun updateNews() {
        try {
            val news = newsUseCase.getNews(uiState.value.searchQuery)
            withContext(Dispatchers.Main) {
                _uiState.value = _uiState.value.copy(articles = news)
            }
        } catch (e: Exception) {
            Log.d(TAG, "search: $e")
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Не получилось загрузить новости", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
