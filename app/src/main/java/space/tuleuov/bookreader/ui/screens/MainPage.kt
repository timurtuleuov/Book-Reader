package space.tuleuov.bookreader.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import space.tuleuov.bookreader.ui.component.BooksListPreview
import space.tuleuov.bookreader.ui.component.UserPreview

@Composable
fun MainPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserPreview()
        Spacer(modifier = Modifier.height(60.dp))

        Spacer(modifier = Modifier.height(20.dp))

        BooksListPreview()

    }
}