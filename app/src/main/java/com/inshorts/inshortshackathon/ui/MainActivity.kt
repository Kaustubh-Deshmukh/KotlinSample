package com.inshorts.inshortshackathon.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.github.kittinunf.fuel.Fuel
import com.inshorts.inshortshackathon.R
import com.inshorts.inshortshackathon.helpers.ClickListener
import com.inshorts.inshortshackathon.helpers.InshortsScrollListener
import com.inshorts.inshortshackathon.helpers.adapters.NewsAdapter
import com.inshorts.inshortshackathon.helpers.constants.Constants
import com.inshorts.inshortshackathon.models.News
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author : Kaustubh Deshmukh
 * @email : kaustubh.deshmukh27@gmail.com
 */

class MainActivity : AppCompatActivity() {

    val TAG: String = "##MainActivity##"
    val PER_PAGE = 10
    var adapter: NewsAdapter? = null
    var list: MutableList<News> = arrayListOf()
    var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NewsAdapter()
        newsList.adapter = adapter
        newsList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        newsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

            }
        })
        newsList.itemAnimator = DefaultItemAnimator()
        newsList.addOnScrollListener(object : InshortsScrollListener(newsList.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                Log.d(TAG, "Fetching more news in loadMoreItems")
                fetchNews(currentPage)
            }

            override fun isLastPage(): Boolean {

                if (currentPage >= (list.size / 10) - 1) {
                    return true
                }
                return false
            }

            override fun isLoading(): Boolean {
                return swipeLayout.isRefreshing
            }
        })
        adapter?.setOnClickListener(object : ClickListener {
            override fun onClick(pos: Int, news: News) {
                val intent = Intent(applicationContext, WebViewActivity::class.java)
                intent.putExtra("news", news)
                startActivity(intent)
            }
        })
        swipeLayout.isRefreshing = false
        swipeLayout.setOnRefreshListener {
            list.clear()
            adapter?.clear()
            fetchNews(1)
        }
    }

    override fun onResume() {
        super.onResume()
        if (list.size == 0) {
            fetchNews(1)
        }
    }

    override fun onPause() {
        super.onPause()
    }

    fun fetchNews(page: Int) {
        if (swipeLayout.isRefreshing) {
            return
        }
        Log.d(TAG, "Fetching news...")
        swipeLayout.isRefreshing = true
        if (list.size > 0) {
            val from = (page - 1) * 10
            val to = (page * 10) - 1
            Log.d(TAG, "Done fetching news from " + from.toString() + " to " + to.toString())
            adapter?.append(list.subList(from, to))
            // to fix "notifyDataSetChanged Cannot call this method in a scroll callback"
            newsList.post { adapter?.notifyDataSetChanged() }
            swipeLayout.isRefreshing = false
            currentPage++
            return
        }
        Fuel.get(Constants.NEWS_URL).responseObject(News.ListDeserializer()) {
            _, _, result ->
            list = result.component1() as MutableList<News>
            adapter?.append(result.component1()?.subList(0, 9) as MutableList<News>)
            adapter?.notifyDataSetChanged()
            swipeLayout.isRefreshing = false
            currentPage++
        }
    }
}
