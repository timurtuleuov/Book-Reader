package space.tuleuov.bookreader.ui.reader.fb2reader

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toFile
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.File
import java.io.InputStream
import java.io.OutputStream



data class Chapter(val title: String, val content: StringBuilder)

data class FB2Book(val title: String, val authors: List<String>, val img: String?, val annotation: List<String>, val body: String, val chapters: List<Chapter>)
fun imageBitmapFromBytes(encodedImageData: ByteArray): ImageBitmap {
    return BitmapFactory.decodeByteArray(encodedImageData, 0, encodedImageData.size).asImageBitmap()
}

fun parseFB2(inputStream: InputStream, context: Context): FB2Book? {
    val factory = XmlPullParserFactory.newInstance()
    factory.isNamespaceAware = false
    val xpp = factory.newPullParser()
    xpp.setInput(inputStream, null)

    var inBinary = false
    var binaryId: String? = null
    var binaryContent: StringBuilder? = null


    var annotation = mutableListOf<String>()
    var currentTag: String? = null
    var title = mutableListOf<String>()
    var body = StringBuilder()
    val authors = mutableListOf<String>()
    val chapters = mutableListOf<Chapter>()
    var currentChapterTitle: String? = null
    var currentChapterContent: StringBuilder? = null
    var img = mutableListOf<String>()

    while (xpp.eventType != XmlPullParser.END_DOCUMENT) {
        when (xpp.eventType) {
            XmlPullParser.START_TAG -> {
                currentTag = xpp.name
                if (currentTag == "title") {
                    currentChapterTitle = ""
                    currentChapterContent = StringBuilder()
                    currentChapterContent.append("\t")
                }
            }
            XmlPullParser.TEXT -> {
                val text = xpp.text
                when (currentTag) {
                    "book-title", "book-name" -> title.add(text)

                    "first-name", "last-name" -> authors.add(text.trim())

                    "annotation" -> annotation.add(text)

                    "p" -> {
                        body.append(text)
                        if (currentChapterContent != null) {
                            currentChapterContent.append(text)
                        }
                    }

                    "empty-line" -> {
                        body.append("\n")
                        if (currentChapterContent != null) {
                            currentChapterContent.append("\n")
                        }
                    }
                }
            }
            XmlPullParser.END_TAG -> {
                if (xpp.name == "title") {
                    currentChapterTitle?.let { title ->
                        currentChapterContent?.let { content ->
                            val contentLines = content.lines()
                            val firstLine = contentLines.first()
                            val chapterContent = content.delete(0, content.indexOf(firstLine) + firstLine.length + 1)
                            chapters.add(Chapter(contentLines[0], chapterContent))
                        }
                    }
                } else if (inBinary && xpp.name == "binary") {
                    inBinary = false

                    val binaryData = binaryContent?.toString()?.toByteArray()
                    if (binaryData != null && binaryId != null) {
                        val imageBitmap = imageBitmapFromBytes(binaryData)
                        img.add(imageBitmap.toString())
                    }

                }
            }
        }
        xpp.next()
    }

    return title?.let { FB2Book(it[0], authors, img.toString(), annotation, body.toString().trim(), chapters) }
}


