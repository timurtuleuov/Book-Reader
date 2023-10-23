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

    var title: String? = null
    var body = ""

    var isTitle = false

    while (xpp.eventType != XmlPullParser.END_DOCUMENT) {
        when (xpp.eventType) {
            XmlPullParser.START_TAG -> {
                when (xpp.name) {
                    "title" -> {
                        isTitle = true
                    }
                }
            }
            XmlPullParser.TEXT -> {
                if (isTitle) {
                    title = xpp.text
                } else {
                    body += xpp.text
                }
            }
            XmlPullParser.END_TAG -> {
                if (xpp.name == "title") {
                    isTitle = false
                }
            }
        }
        xpp.next()
    }

    return FB2Book(title, body)
}

data class FB2Book(val title: String?, val body: String)


