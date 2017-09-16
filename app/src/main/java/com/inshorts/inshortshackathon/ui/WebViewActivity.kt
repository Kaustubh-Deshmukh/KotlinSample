package com.inshorts.inshortshackathon.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.inshorts.inshortshackathon.R
import com.inshorts.inshortshackathon.models.News
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val news = intent.getParcelableExtra<News>("news")
        requireNotNull(news)
        webView.loadUrl(news.URL)
    }
}
