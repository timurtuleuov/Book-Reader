package space.tuleuov.bookreader.ui.reader.fb2reader

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

fun readFB2File() {
}

fun parseFB2(inputStream: InputStream): FB2Book {
    val factory = XmlPullParserFactory.newInstance()
    factory.isNamespaceAware = false
    val xpp = factory.newPullParser()

    xpp.setInput(inputStream, null)

    var currentTag: String? = null
    var title: String? = null
    var body = StringBuilder()
    val authors = mutableListOf<String>()

    while (xpp.eventType != XmlPullParser.END_DOCUMENT) {
        when (xpp.eventType) {
            XmlPullParser.START_TAG -> {
                currentTag = xpp.name
            }
            XmlPullParser.TEXT -> {
                val text = xpp.text
                when (currentTag) {
                    "book-title" -> title = text

                    "first-name", "middle-name", "last-name" -> authors.add(text)
                }
                if (currentTag == "p" || currentTag == "empty-line") {
                    body.append(text)
                }
            }
        }
        xpp.next()
    }

    return FB2Book(title.toString(), authors, body.toString())
}


data class FB2Book(val title: String, val authors: MutableList<String>, val body: String)

