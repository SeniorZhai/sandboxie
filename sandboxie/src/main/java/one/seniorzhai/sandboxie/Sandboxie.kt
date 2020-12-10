package one.seniorzhai.sandboxie

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import org.xmlpull.v1.XmlPullParser
import java.util.Stack

class Sandboxie(val context: Context, val str: String) {
    companion object {
        private const val TAG = "Sandboxie"
    }

    private val xmlReader = XmlReader()

    fun build(): View {
        xmlReader.setXml(str)
        var eventType = xmlReader.getEventType()
        val containerStack = Stack<LinearLayout>()
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_DOCUMENT -> {
                    Log.d(TAG, "Start document")
                }
                XmlPullParser.START_TAG -> {
                    when {
                        xmlReader.getName() == Tag.row.name -> {
                            LinearLayout(context).apply {
                                orientation = HORIZONTAL
                                val color =
                                    Color.parseColor(
                                        xmlReader.getAttributeValue("color") ?: "#00000000"
                                    )
                                background = ColorDrawable(color)
                                xmlReader.getAttributeValue("cross")?.let {
                                    when (it) {
                                        "center" -> gravity = Gravity.CENTER_VERTICAL
                                    }
                                }
                                xmlReader.getAttributeValue("padding")?.toInt()?.let {
                                    setPadding(it)
                                }
                                if (containerStack.isNotEmpty()) {
                                    containerStack.lastElement().addView(
                                        this,
                                        LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                                    )
                                }
                                containerStack.push(this)
                            }
                        }
                        xmlReader.getName() == Tag.column.name -> {
                            LinearLayout(context).apply {
                                orientation = VERTICAL
                                val color =
                                    Color.parseColor(
                                        xmlReader.getAttributeValue("color") ?: "#00000000"
                                    )
                                background = ColorDrawable(color)
                                xmlReader.getAttributeValue("cross")?.let {
                                    when (it) {
                                        "center" -> gravity = Gravity.CENTER_HORIZONTAL
                                    }
                                }
                                xmlReader.getAttributeValue("padding")?.toInt()?.let {
                                    setPadding(it)
                                }
                                if (containerStack.isNotEmpty()) {
                                    containerStack.lastElement().addView(
                                        this,
                                        LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                                    )
                                }
                                containerStack.push(this)
                            }
                        }
                        xmlReader.getName() == Tag.image.name -> {
                            ImageView(context).apply {
                                val width =
                                    xmlReader.getAttributeValue("width")?.toInt() ?: MATCH_PARENT
                                val height =
                                    xmlReader.getAttributeValue("height")?.toInt() ?: WRAP_CONTENT
                                val url = xmlReader.getAttributeValue("url")
                                containerStack.lastElement()
                                    .addView(this, LinearLayout.LayoutParams(width, height))
                                Log.d(TAG, "$url $width $height")
                                Glide.with(context).load(url).centerCrop().into(this)
                            }
                        }
                        xmlReader.getName() == Tag.text.name -> {
                            TextView(context).apply {
                                containerStack.lastElement()
                                    .addView(
                                        this,
                                        LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                                            .apply {
                                                xmlReader.getAttributeValue("flex")?.toFloat()
                                                    ?.let { flex ->
                                                        weight = flex
                                                    }
                                            }
                                    )
                                xmlReader.getAttributeValue("padding")?.toInt()?.let {
                                    setPadding(it)
                                }
                                xmlReader.getAttributeValue("size")?.toFloat()?.let {
                                    this.setTextSize(TypedValue.COMPLEX_UNIT_SP, it)
                                }
                                xmlReader.getAttributeValue("color")?.let {
                                    this.setTextColor(Color.parseColor(it))
                                }
                                this.text = xmlReader.getAttributeValue("text")
                            }
                        }
                    }
                    Log.d(TAG, "START ${xmlReader.getName()}")
                }
                XmlPullParser.END_TAG -> {
                    when {
                        xmlReader.getName() == Tag.row.name -> {
                            if (containerStack.size > 1) {
                                containerStack.pop()
                            }
                        }
                        xmlReader.getName() == Tag.column.name -> {
                            if (containerStack.size > 1) {
                                containerStack.pop()
                            }
                        }

                    }
                    Log.d(TAG, "END ${xmlReader.getName()}")
                }
                XmlPullParser.TEXT -> {
                    Log.d(TAG, "Text ${xmlReader.getText()}")
                }
            }
            eventType = xmlReader.getNextEvent()
        }
        return containerStack.pop()
    }

    enum class Tag {
        row, column, text, image
    }
}