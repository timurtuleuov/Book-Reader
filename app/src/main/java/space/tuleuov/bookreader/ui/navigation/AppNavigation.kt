package space.tuleuov.bookreader.ui.navigation

import android.app.Application
import android.os.Environment
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import space.tuleuov.bookreader.books.model.BookViewModel
import space.tuleuov.bookreader.hyphe.Haaivin
import space.tuleuov.bookreader.ui.authorization.LoginScreen
import space.tuleuov.bookreader.ui.authorization.RegisterScreen
import space.tuleuov.bookreader.ui.authorization.UserPreferences
import space.tuleuov.bookreader.ui.component.BookDetail
import space.tuleuov.bookreader.ui.filemanager.FileManagerContent
import space.tuleuov.bookreader.ui.reader.fb2reader.parseFB2
import space.tuleuov.bookreader.ui.reader.readerview.readerUI
import space.tuleuov.bookreader.ui.screens.MainPage
import java.io.File
import java.io.FileInputStream


@Composable
fun AppNavigation(haaivin: Haaivin) {
    val app = LocalContext.current.applicationContext as Application
    val userPreferences =  UserPreferences(app)
    val savedUser = userPreferences.getUser()
    val rootDirectory = Environment.getExternalStorageDirectory()
    val navController = rememberNavController()
    val context  = LocalContext.current
    var startDestination = if (savedUser == null) {
        "login"
    }
    else {
        "mainPage"
    }
    NavHost(navController = navController, startDestination = startDestination) {

        composable("login") { LoginScreen(navController)}
        composable("registration"){ RegisterScreen(navController)}
        composable("mainPage") { MainPage(navController, app = app) }
        composable("bookDetails/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")
            if (bookId != null) {
                BookDetail(bookId, viewModel = BookViewModel(app), navController)
            }
        }
        composable(
            "fileManager/{directoryPath}",
            arguments = listOf(navArgument("directoryPath") { type = NavType.StringType; defaultValue = null; nullable = true})
        ) { backStackEntry ->
            val directoryPath = backStackEntry.arguments?.getString("directoryPath")
            if (directoryPath == null) {
                FileManagerContent(rootDirectory.path, navController, app = app)
            } else {
                FileManagerContent(directoryPath, navController, app = app)
            }

        }
        composable("readFile/{bookPath}") { backStackEntry ->

            val bookPath = backStackEntry.arguments?.getString("bookPath")

            if (bookPath != null) {
                val file = File(bookPath)
                val inputStream = FileInputStream(file)
                val book = parseFB2(inputStream, context)
                if (book != null) {
                    readerUI(book, navController, haaivin)
                }
            } else {
                //If bookPath doesn't exist
            }
        }

    }
}

