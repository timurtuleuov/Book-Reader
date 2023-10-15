package space.tuleuov.bookreader.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import space.tuleuov.bookreader.books.model.BookViewModel
import space.tuleuov.bookreader.ui.component.BookDetail
import space.tuleuov.bookreader.ui.screens.MainPage

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainPage") {
        composable("mainPage") { MainPage(navController) }
        composable("bookDetails/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")
            // Здесь передайте bookId в BookDetails и отобразите информацию о книге
            if (bookId != null) {
                BookDetail(bookId, viewModel = BookViewModel())
            }
        }

    }

}