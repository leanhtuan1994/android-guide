package com.zkuzan.guild

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zkuzan.guildpage.GuidePage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay?.setOnClickListener {
            GuidePage.load(intArrayOf(R.drawable.guide_page_1, R.drawable.guide_page_2, R.drawable.guide_page_3, R.drawable.guide_page_4))
                .pageDoneDrawableResource(R.drawable.btn_done)
                .showSkip(true)
                .lastPageHideSkip(true)
                .onGuidePageChangeCallback(object: GuidePage.OnGuidePageChangeCallback {
                    override fun onPageDone(skip: Boolean) {

                    }
                })
                .start(this)

        }
    }
}
