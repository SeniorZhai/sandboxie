package one.seniorzhai.sandboxie

import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class XmlReader {

    val factory = XmlPullParserFactory.newInstance().apply {
        isNamespaceAware = true
    }
    val xmlParser = factory.newPullParser()

    fun setXml(xml: String) {
        xmlParser.setInput(StringReader(xml))
    }

    fun getNextEvent(): Int {
        return xmlParser.next()
    }

    fun getNextText(): String {
        return xmlParser.nextText()
    }

    fun getEventType(): Int {
        return xmlParser.eventType
    }

    fun getName(): String? {

        return xmlParser.name
    }

    fun getText(): String? {
        return xmlParser.text
    }

    fun attributeCount(): Int {
        return xmlParser.attributeCount
    }

    fun getAttributeName(i: Int): String? {
        return xmlParser.getAttributeName(i)
    }

    fun getAttributeValue(name: String): String? {
        return xmlParser.getAttributeValue(null, name)
    }

    fun getDepth() = xmlParser.depth
}