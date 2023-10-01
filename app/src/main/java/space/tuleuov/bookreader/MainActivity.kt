package space.tuleuov.bookreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import space.tuleuov.bookreader.ui.component.BooksListPreview
import space.tuleuov.bookreader.ui.component.PdfViewerPage
import space.tuleuov.bookreader.ui.component.SearchViewPreview
import space.tuleuov.bookreader.ui.component.UserPreview
import space.tuleuov.bookreader.ui.theme.BookReaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookReaderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        UserPreview()
                        Spacer(modifier = Modifier.height(60.dp))
                        SearchViewPreview()
                        Spacer(modifier = Modifier.height(30.dp))

                        BooksListPreview()
                    }
                }
            }
        }
    }
}

