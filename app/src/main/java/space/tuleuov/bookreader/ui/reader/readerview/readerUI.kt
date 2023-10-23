package space.tuleuov.bookreader.ui.reader.readerview

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import space.tuleuov.bookreader.ui.reader.fb2reader.FB2Book


fun readerUI(book: FB2Book) {
    println(book.title)
    for (line in book.body.lines()){
        println(line)
    }
}