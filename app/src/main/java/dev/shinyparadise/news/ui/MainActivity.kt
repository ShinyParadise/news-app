package dev.shinyparadise.news.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dev.shinyparadise.news.app.NewsApplication
import dev.shinyparadise.news.app.di.AppComponent
import dev.shinyparadise.news.ui.screens.main.MainViewModel
import dev.shinyparadise.news.ui.theme.NewsTheme
import dev.shinyparadise.news.utils.ViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mainViewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    private val component: AppComponent by lazy {
        (application as NewsApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)

        enableEdgeToEdge()
        setContent {
            NewsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NewsApp(viewModel = mainViewModel)
                    }
                }
            }
        }
    }
}
