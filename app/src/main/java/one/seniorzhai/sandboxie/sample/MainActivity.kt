package one.seniorzhai.sandboxie.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.setMargins
import one.seniorzhai.sandboxie.Sandboxie

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val str = """
     <column color="#EFEFEF">
        <image
            height="320"
            url="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1612138888,1794405442&amp;fm=26&amp;gp=0.jpg" />
        <row cross="center" padding="24">    
            <text text="【数据：Bitwise10加密指数基金首日交易约合1400万美元】" flex="1"/>
            <image
                height="120"
                width="120"
                url="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1612138888,1794405442&amp;fm=26&amp;gp=0.jpg" />
        </row>
        <text
            padding="24"
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
    }
}