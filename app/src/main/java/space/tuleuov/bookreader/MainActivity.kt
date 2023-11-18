package space.tuleuov.bookreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import space.tuleuov.bookreader.authorization.AuthViewModel
import space.tuleuov.bookreader.authorization.AuthViewModelFactory
import space.tuleuov.bookreader.db.Database
import space.tuleuov.bookreader.db.dao.UserDao
import space.tuleuov.bookreader.db.repositiry.UserRepository
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

