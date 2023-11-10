package space.tuleuov.bookreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import space.tuleuov.bookreader.hyphe.Haaivin
import space.tuleuov.bookreader.hyphe.HunspellDictionary

import space.tuleuov.bookreader.ui.navigation.AppNavigation
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
                    val hyphRu: Haaivin by lazy {
                        Haaivin(listOf(
                            HunspellDictionary("ruhyph") { assets.open("hyph_ru_RU.dic") },
                        ))
                    }
                    AppNavigation(hyphRu)
                }
            }
        }
    }
}

