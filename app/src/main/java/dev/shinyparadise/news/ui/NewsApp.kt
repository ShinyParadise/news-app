package dev.shinyparadise.news.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.shinyparadise.news.app.navigation.Routes
import dev.shinyparadise.news.ui.screens.article.ArticleScreen
import dev.shinyparadise.news.ui.screens.main.MainScreen
import dev.shinyparadise.news.ui.screens.main.MainViewModel

@Composable
fun NewsApp(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel,
) {
    NavHost(navController = navController, startDestination = Routes.main) {
        composable(Routes.main) {
            MainScreen(
                viewModel = viewModel,
                onArticleClick = {
                    viewModel.articleClicked(navController, it)
                }
            )
        }

        composable(Routes.article) {
            ArticleScreen(viewModel = viewModel)
        }
    }
}
