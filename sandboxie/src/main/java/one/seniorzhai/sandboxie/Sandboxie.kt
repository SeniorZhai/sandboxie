package one.seniorzhai.sandboxie

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
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
                                configContainer(this, xmlReader, containerStack)
                            }
                        }
                        xmlReader.getName() == Tag.column.name -> {
                            LinearLayout(context).apply {
                                orientation = VERTICAL
                                configContainer(this, xmlReader, containerStack)
                            }
                        }
                        xmlReader.getName() == Tag.image.name -> {
                            ImageView(context).apply {
                                configImage(this, xmlReader, containerStack)
                            }
                        }
                        xmlReader.getName() == Tag.text.name -> {
                            TextView(context).apply {
                                configText(this, xmlReader, containerStack)
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

    private fun configImage(
        imageView: ImageView, xmlReader: XmlReader,
        containerStack: Stack<LinearLayout>
    ) {
        imageView.apply {
            val width =
                xmlReader.getAttributeValue(Property.width.name)?.toSize ?: MATCH_PARENT
            val height =
                xmlReader.getAttributeValue(Property.height.name)?.toSize ?: WRAP_CONTENT
            val url = xmlReader.getAttributeValue("url")
            containerStack.lastElement().addView(this, LinearLayout.LayoutParams(width, height))
            Glide.with(context).load(url).centerCrop().into(this)
        }
    }

    private fun configText(
        textView: TextView, xmlReader: XmlReader,
        containerStack: Stack<LinearLayout>
    ) {
        textView.apply {
            val width =
                xmlReader.getAttributeValue(Property.width.name)?.toSize ?: MATCH_PARENT
            val height =
                xmlReader.getAttributeValue(Property.height.name)?.toSize ?: WRAP_CONTENT

            xmlReader.getAttributeValue(Property.padding.name)?.toSize?.let {
                setPadding(it)
            }
            xmlReader.getAttributeValue(Property.size.name)?.toFloat()?.let {
                this.setTextSize(TypedValue.COMPLEX_UNIT_SP, it)
            }
            xmlReader.getAttributeValue("color")?.let {
                this.setTextColor(Color.parseColor(it))
            }
            containerStack.lastElement()
                .addView(
                    this,
                    LinearLayout.LayoutParams(width, height)
                        .apply {
                            xmlReader.getAttributeValue(Property.flex.name)?.toFloat()
                                ?.let { flex ->
                                    weight = flex
                                }
                        }
                )
            this.text = xmlReader.getAttributeValue(Property.text.name)
        }
    }

    private fun configContainer(
        linearLayout: LinearLayout,
        xmlReader: XmlReader,
        containerStack: Stack<LinearLayout>
    ) {
        linearLayout.apply {
            val color =
                Color.parseColor(
                    xmlReader.getAttributeValue("color") ?: "#00000000"
                )
            background = ColorDrawable(color)
            xmlReader.getAttributeValue("round")?.toSize?.let {
                this.round(it)
            }
            xmlReader.getAttributeValue(Property.gravity.name)?.let {
                when (it) {
                    "center" -> gravity = Gravity.CENTER
                    "top" -> gravity = Gravity.TOP
                    "bottom" -> gravity = Gravity.BOTTOM
                    "center_vertical" -> gravity = Gravity.CENTER_VERTICAL
                    "center_horizontal" -> gravity = Gravity.CENTER_HORIZONTAL
                }
            }
            xmlReader.getAttributeValue(Property.padding.name)?.toSize?.let {
                setPadding(it)
            }
            if (containerStack.isNotEmpty()) {
                containerStack.lastElement().addView(
                    this,
                    LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                        xmlReader.getAttributeValue(Property.flex.name)?.toFloat()
                            ?.let { flex ->
                                weight = flex
                            }
                    }
                )
            }
            containerStack.push(this)
        }
    }
}