package one.seniorzhai.sandboxie

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.bumptech.glide.Glide
import org.xmlpull.v1.XmlPullParser
import java.util.Stack

class Sandboxie(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    companion object {
        private const val TAG = "Sandboxie"
    }

    init {
        val str = """
     <column>
    <image
        width="300"
        height="300"
        url="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1612138888,1794405442&amp;fm=26&amp;gp=0.jpg" />
    <text text="【数据：Bitwise10加密指数基金首日交易约合1400万美元】据Skew数据显示，加密资产管理公司BitwiseAssetManagement的Bitwise10加密指数基金（BITW）昨日开始在场外交易，开盘首日交易46.5万股，约合1400万美元。
        此前报道，Bitwise10加密指数基金已正式面向美国投资者公开发行，并在美国证券交易场外市场（OTCMarkets）旗下OTCQX挂牌交易，代码为BITW。该指数基金成立于2017年11月，跟踪BTC、ETH、LTC和其他市值排名前10位的加密货币，此前一直非公开发行。" />
    <row>
        <text text="
            【数据：Bitwise10加密指数基金首日交易约合1400万美元】据Skew数据显示，加密资产管理公司BitwiseAssetManagement的Bitwise10加密指数基金（BITW）昨日开始在场外交易，开盘首日交易46.5万股，约合1400万美元。
            此前报道，Bitwise10加密指数基金已正式面向美国投资者公开发行，并在美国证券交易场外市场（OTCMarkets）旗下OTCQX挂牌交易，代码为BITW。该指数基金成立于2017年11月，跟踪BTC、ETH、LTC和其他市值排名前10位的加密货币，此前一直非公开发行。" />
        <image
            width="300"
            height="300"
            url="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1612138888,1794405442&amp;fm=26&amp;gp=0.jpg" />
        <text text="欧洲区块链协会主席：监管机构承认稳定币是一种加密资产】12月10日消息，欧盟委员会CrowdFunding
            Stakeholder小组顾问、欧洲区块链协会主席Michael
            Gebert在接受专访时表示，关于监管机构正在关注稳定币，加密社区最初的反应是监管机构想要监管稳定币。就此我们已与欧盟委员会进行了交谈，监管机构并不反对将稳定币作为一种加密资产。他们是从货币和竞争性政策的角度进行研究，只是想确保他们现有的货币政策和竞争性政策不会被否决。这意味着将在现有结构和基础上构建解决方案。与此同时，针对拟议中的法规是否会促进更多与欧元挂钩的稳定货币产生，Michael
            Gebert表示创建过程不会更容易，从货币的角度上看，会更加困难更加耗时和复杂。（BeInCrypto）" />
    </row>
</column>
        """

        val xmlReader = XmlReader()

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
                            if (containerStack.isEmpty()) {
                                a = HORIZONTAL
                            } else {
                                LinearLayout(context).apply {
                                    a = HORIZONTAL
                                    addView(this, LayoutParams(WRAP_CONTENT, WRAP_CONTENT))
                                    containerStack.push(this)
                                }
                            }
                        }
                        xmlReader.getName() == Tag.column.name -> {
                            if (containerStack.isEmpty()) {
                                a = VERTICAL
                            } else {
                                LinearLayout(context).apply {
                                    a = VERTICAL
                                    addView(this, LayoutParams(WRAP_CONTENT, WRAP_CONTENT))
                                    containerStack.push(this)
                                }
                            }
                        }
                        xmlReader.getName() == Tag.image.name -> {
                            ImageView(context).apply {
                                val width =
                                    xmlReader.getAttributeValue("width")?.toInt() ?: WRAP_CONTENT
                                val height =
                                    xmlReader.getAttributeValue("height")?.toInt() ?: WRAP_CONTENT
                                val url = xmlReader.getAttributeValue("url")
                                if (containerStack.isEmpty()) {
                                    addView(this, LayoutParams(width, height))
                                } else {
                                    containerStack.lastElement()
                                        .addView(this, LayoutParams(width, height))
                                }
                                Log.d(TAG, "$url $width $height")
                                Glide.with(context).load(url).into(this)
                            }
                        }
                        xmlReader.getName() == Tag.text.name -> {
                            TextView(context).apply {
                                if (containerStack.isEmpty()) {
                                    addView(this, LayoutParams(WRAP_CONTENT, WRAP_CONTENT))
                                } else {
                                    containerStack.lastElement()
                                        .addView(this, LayoutParams(WRAP_CONTENT, WRAP_CONTENT))
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
                            if (containerStack.isNotEmpty()) {
                                containerStack.pop()
                            }
                        }
                        xmlReader.getName() == Tag.column.name -> {
                            if (containerStack.isNotEmpty()) {
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
    }

    private var a: Int = -1
        set(@LinearLayoutCompat.OrientationMode value) {
            if (field == -1) {
                field = value
                orientation = field
                Log.d(TAG,"orientation $orientation")
            }
        }

    enum class Tag {
        row, column, text, image
    }
}