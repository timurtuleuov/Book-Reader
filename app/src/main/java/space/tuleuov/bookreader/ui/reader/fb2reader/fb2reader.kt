package space.tuleuov.bookreader.ui.reader.fb2reader

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

fun readFB2File() {
}

data class Chapter(val title: String, val content: StringBuilder)

data class FB2Book(val title: String, val authors: List<String>, val body: String, val chapters: List<Chapter>)

fun parseFB2(inputStream: InputStream): FB2Book? {
    val factory = XmlPullParserFactory.newInstance()
    factory.isNamespaceAware = false
    val xpp = factory.newPullParser()
    xpp.setInput(inputStream, null)

    var currentTag: String? = null
    var title: String? = null
    var body = StringBuilder()
    val authors = mutableListOf<String>()
    val chapters = mutableListOf<Chapter>()
    var currentChapterTitle: String? = null
    var currentChapterContent: StringBuilder? = null

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
                    "book-title" -> title = text

                    "first-name", "middle-name", "last-name" -> authors.add(text)

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
                }

            }
        }
        xpp.next()
    }

    return title?.let { FB2Book(it, authors, body.toString().trim(), chapters) }
}


