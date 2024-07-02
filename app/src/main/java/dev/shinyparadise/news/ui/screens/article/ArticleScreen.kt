package dev.shinyparadise.news.ui.screens.article

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.shinyparadise.news.ui.screens.main.MainViewModel

@Composable
fun ArticleScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
) {
    val uiState = viewModel.uiState.collectAsState()

    ArticleScreenContent(modifier = modifier, uiState = uiState.value)
}

@Composable
private fun ArticleScreenContent(
    modifier: Modifier = Modifier,
    uiState: MainViewModel.MainUiState
) {
    val article = uiState.selectedArticle!!
    val ctx = LocalContext.current

    Box(modifier
        .fillMaxSize()
        .padding(10.dp)
    ) {
        Column {
            Text(text = article.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(5.dp))

            article.urlToImage?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .padding(bottom = 5.dp)
                )
            }

            Text(text = article.author, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(10.dp))

            article.content?.let { Text(text = it, style = MaterialTheme.typography.bodyMedium) }
        }

        FloatingActionButton(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                ctx.startActivity(intent)
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = "open in browser")
        }
    }
}
