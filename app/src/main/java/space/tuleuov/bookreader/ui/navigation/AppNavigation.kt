package space.tuleuov.bookreader.ui.navigation

import android.os.Environment
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import space.tuleuov.bookreader.books.model.BookViewModel
import space.tuleuov.bookreader.ui.component.BookDetail
import space.tuleuov.bookreader.ui.filemanager.FileManagerContent
import space.tuleuov.bookreader.ui.screens.MainPage

@Composable
fun AppNavigation() {
    val rootDirectory = Environment.getExternalStorageDirectory()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainPage") {
        composable("mainPage") { MainPage(navController) }
        composable("bookDetails/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")
            // Здесь передайте bookId в BookDetails и отобразите информацию о книге
            if (bookId != null) {
                BookDetail(bookId, viewModel = BookViewModel(), navController)
            }
        }
        composable(
            "fileManager/{directoryPath}",
            arguments = listOf(navArgument("directoryPath") { type = NavType.StringType; defaultValue = null; nullable = true})
        ) { backStackEntry ->
            println("Ошибка здесь 1")
            val directoryPath = backStackEntry.arguments?.getString("directoryPath")
            println("Ошибка здесь 2, $directoryPath, ${rootDirectory.path}")
            if (directoryPath == null) {
                println("Ошибка здесь 3")
                FileManagerContent(rootDirectory.path, navController)
            } else {
                println("Ошибка здесь 4")
                FileManagerContent(directoryPath, navController)
            }

        }




    }
}
