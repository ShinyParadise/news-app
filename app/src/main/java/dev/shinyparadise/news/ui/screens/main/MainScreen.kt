package dev.shinyparadise.news.ui.screens.main

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.shinyparadise.news.data.Article
import dev.shinyparadise.news.ui.theme.NewsTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val uiState = viewModel.uiState.collectAsState()

    MainScreenContent(
        modifier = modifier,
        uiState = uiState.value,
        uiFlow = MainUiFlow(
            onSearchQueryChange = viewModel::changeSearchQuery,
            onSearch = viewModel::search
        )
    )
}

class MainUiFlow(
    val onSearchQueryChange: (String) -> Unit = {},
    val onSearch: () -> Unit = {},
)

@Composable
private fun MainScreenContent(
    modifier: Modifier = Modifier,
    uiState: MainViewModel.MainUiState,
    uiFlow: MainUiFlow = MainUiFlow()
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            TextField(
                value = uiState.searchQuery, onValueChange = uiFlow.onSearchQueryChange
            )
        }

        items(items = uiState.articles) {
            NewsItem(
                article = it,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(horizontal = 10.dp)
            )
        }
    }
}

@Composable
private fun NewsItem(
    modifier: Modifier = Modifier,
    article: Article
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .clickable {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(article.url)
                )
                context.startActivity(intent)
            }
    ) {
        Column {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
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
