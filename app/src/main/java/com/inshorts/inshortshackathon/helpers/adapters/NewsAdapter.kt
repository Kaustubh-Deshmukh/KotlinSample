package com.inshorts.inshortshackathon.helpers.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inshorts.inshortshackathon.R
import com.inshorts.inshortshackathon.helpers.ClickListener
import com.inshorts.inshortshackathon.models.News
import kotlinx.android.synthetic.main.recycler_item_news.view.*

/**
 * @author : Kaustubh Deshmukh
 * @email : kaustubh.deshmukh27@gmail.com
 */

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    val TAG: String = "###NewsAdapter"
    var list: MutableList<News> = arrayListOf()
    var listener: ClickListener? = null

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.itemView?.newsTitle?.text = list[position].TITLE
        holder?.itemView?.newsPublisher?.text = list[position].PUBLISHER
        holder?.itemView?.setOnClickListener { view: View? ->
            listener?.onClick(position, list[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent?.context)
                .inflate(R.layout.recycler_item_news, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun append(subList: MutableList<News>) {
        list.addAll(subList)
    }

    fun clear() {
        list.clear()
    }

    fun setOnClickListener(l: ClickListener) {
        listener = l
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
