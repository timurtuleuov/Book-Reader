package space.tuleuov.bookreader

import android.app.Application
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import space.tuleuov.bookreader.db.entity.Book
import space.tuleuov.bookreader.hyphe.Haaivin
import space.tuleuov.bookreader.hyphe.HunspellDictionary

import space.tuleuov.bookreader.ui.navigation.AppNavigation
import space.tuleuov.bookreader.ui.theme.BookReaderTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookReaderTheme {
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

