package dev.shinyparadise.news.ui.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.shinyparadise.news.data.Article
import dev.shinyparadise.news.ui.theme.NewsTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onArticleClick: (Article) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()

    MainScreenContent(
        modifier = modifier,
        uiState = uiState.value,
        uiFlow = MainUiFlow(
            onSearchQueryChange = viewModel::changeSearchQuery,
            onSearch = viewModel::search,
            onArticleClick = onArticleClick
        )
    )
}

class MainUiFlow(
    val onSearchQueryChange: (String) -> Unit = {},
    val onSearch: () -> Unit = {},
    val onArticleClick: (Article) -> Unit = {},
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainScreenContent(
    modifier: Modifier = Modifier,
    uiState: MainViewModel.MainUiState,
    uiFlow: MainUiFlow = MainUiFlow()
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                TextField(
                    value = uiState.searchQuery,
                    onValueChange = uiFlow.onSearchQueryChange,
                    singleLine = true,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(horizontal = 10.dp)
                    ,
                    trailingIcon = {
                        IconButton(onClick = { uiFlow.onSearch() }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    },
                )
            }
        }

        items(items = uiState.articles) {
            NewsItem(
                article = it,
                uiFlow = uiFlow,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(10.dp)
            )
        }
    }
}

@Composable
private fun NewsItem(
    modifier: Modifier = Modifier,
    uiFlow: MainUiFlow,
    article: Article
) {
    Card(
        modifier = modifier
            .clickable { uiFlow.onArticleClick(article) }
    ) {
        Column {
            article.urlToImage?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier.size(60.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = article.title, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview
@Composable
private fun MainScreen_Preview() {
    NewsTheme {
        MainScreenContent(uiState = MainViewModel.MainUiState())
    }
}
