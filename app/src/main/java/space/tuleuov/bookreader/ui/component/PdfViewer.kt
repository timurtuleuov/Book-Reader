package space.tuleuov.bookreader.ui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.rememberHorizontalPdfReaderState
import com.rizzi.bouquet.HorizontalPDFReader
import java.io.File

@Composable
fun PdfViewerPage() {
    val url = "http://www.belousenko.com/books/xix/gogol_1_1960.pdf"
    val pdfState = rememberHorizontalPdfReaderState(
        resource = ResourceType.Remote(
            url = url,
            headers = hashMapOf("headerKey" to "headerValue")
        ),
        isZoomEnable = true
    )
    HorizontalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )

}

