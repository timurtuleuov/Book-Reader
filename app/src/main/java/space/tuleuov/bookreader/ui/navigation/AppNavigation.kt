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
import space.tuleuov.bookreader.ui.reader.fb2reader.FB2Book
import space.tuleuov.bookreader.ui.reader.fb2reader.parseFB2
import space.tuleuov.bookreader.ui.reader.readerview.readerUI
import space.tuleuov.bookreader.ui.screens.MainPage
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

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
            val directoryPath = backStackEntry.arguments?.getString("directoryPath")
            if (directoryPath == null) {
                FileManagerContent(rootDirectory.path, navController)
            } else {
                FileManagerContent(directoryPath, navController)
            }

        }
        composable("readFile/{bookPath}") { backStackEntry ->

            println("Ошибка 1")
            val bookPath = backStackEntry.arguments?.getString("bookPath")
            println("Ошибка 2")

            if (bookPath != null) {
                val file = File(bookPath)
                val inputStream = FileInputStream(file)
                val book = parseFB2(inputStream)
                if (book != null) {
                    readerUI(book, navController)
                }
            } else {
                println("Ошибка 4")
                // Обработка ошибки, если bookPath == null
            }
        }

    }
}

