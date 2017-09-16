package com.inshorts.inshortshackathon.helpers

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * @author : Kaustubh Deshmukh
 * @email : kaustubh.deshmukh27@gmail.com
 */

abstract class InshortsScrollListener : RecyclerView.OnScrollListener {

    val TAG = "#InshortsScrollListen"
    var layoutManager: LinearLayoutManager? = null

    constructor(manager: LinearLayoutManager) {
        layoutManager = manager
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        val visibleItemCount: Int = layoutManager?.childCount!!
        val totalItemCount: Int = layoutManager?.itemCount!!
        val firstVisibleCount: Int = layoutManager?.findFirstVisibleItemPosition()!!
        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleCount) >= totalItemCount
                    && firstVisibleCount >= 0) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean
}
