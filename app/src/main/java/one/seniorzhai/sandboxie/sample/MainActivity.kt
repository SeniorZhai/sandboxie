package one.seniorzhai.sandboxie.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.setMargins
import one.seniorzhai.sandboxie.Sandboxie
import one.seniorzhai.sandboxie.dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val str = """
     <column color="#FFFFFF" round="8">
        <image height="120" 
            url="https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=81837432,2519168038&amp;fm=11&amp;gp=0.jpg" />
        <row gravity="center_vertical" padding="12">    
            <text text="【数据：Bitwise10加密指数基金首日交易约合1400万美元】" flex="1" right="8"/>
            <image
                width="32"
                height="32"
                url="https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1709644436,2739756540&amp;fm=26&amp;gp=0.jpg" />
        </row>
        <text
            padding="12"
            color="#CCCCCC"
            size="11"
            text="12月10日消息，欧盟委员会CrowdFundingStakeholder小组顾问、欧洲区块链协会主席MichaelGebert在接受专访时表示" />
    </column>
        """


        findViewById<ViewGroup>(R.id.root).addView(
            Sandboxie(this, str).build(),
            FrameLayout.LayoutParams(900, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                setMargins(30)
            })

        val str1 = """
<column
    color="#FFFFFF"
    round="8">
    <row
        gravity="top"
        padding="12">
        <column flex="1">
            <text
                text="【数据：Bitwise10加密指数基金首日交易约合1400万美元】"
                flex="1" />
            <text
                color="#CCCCCC"
                size="11"
                text="12月10日消息，欧盟委员会CrowdFundingStakeholder小组顾问、欧洲区块链协会主席MichaelGebert在接受专访时表示" />
        </column>
        <image
            left="8"
            width="32"
            height="32"
            url="https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1709644436,2739756540&amp;fm=26&amp;gp=0.jpg" />
    </row>
    <row
        gravity="top"
        padding="12">
        <column flex="1">
            <text
                text="【数据：Bitwise10加密指数基金首日交易约合1400万美元】"
                flex="1" />
            <text
                color="#CCCCCC"
                size="11"
                text="12月10日消息，欧盟委员会CrowdFundingStakeholder小组顾问、欧洲区块链协会主席MichaelGebert在接受专访时表示" />
        </column>
        <image
            left="8"
            width="32"
            height="32"
            url="https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1709644436,2739756540&amp;fm=26&amp;gp=0.jpg" />
    </row>
    <row
        gravity="top"
        padding="12">
        <column flex="1">
            <text
                text="【数据：Bitwise10加密指数基金首日交易约合1400万美元】"
                flex="1" />
            <text
                color="#CCCCCC"
                size="11"
                text="12月10日消息，欧盟委员会CrowdFundingStakeholder小组顾问、欧洲区块链协会主席MichaelGebert在接受专访时表示" />
        </column>
        <image
            left="8"
            width="32"
            height="32"
            url="https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1709644436,2739756540&amp;fm=26&amp;gp=0.jpg" />
    </row>

</column>
        """
        findViewById<ViewGroup>(R.id.root).addView(
            Sandboxie(this, str1).build(),
            FrameLayout.LayoutParams(900, FrameLayout.LayoutParams.WRAP_CONTENT).apply {
                topMargin = 10.dp.toInt()
            })
    }
}