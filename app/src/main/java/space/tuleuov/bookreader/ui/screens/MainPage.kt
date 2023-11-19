package space.tuleuov.bookreader.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import space.tuleuov.bookreader.books.model.BookViewModel
import space.tuleuov.bookreader.ui.component.BooksListPreview
import space.tuleuov.bookreader.ui.component.UserPreview

@Composable
fun MainPage(navController: NavController, app: Application) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserPreview(app)
        Spacer(modifier = Modifier.height(60.dp))

        Spacer(modifier = Modifier.height(20.dp))

        BooksListPreview(navController, viewModel = BookViewModel(app))

    }
}