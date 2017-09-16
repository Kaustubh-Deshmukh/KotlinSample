package com.inshorts.inshortshackathon.helpers

import com.inshorts.inshortshackathon.models.News

/**
 * @author : Kaustubh Deshmukh
 * @email : kaustubh.deshmukh27@gmail.com
 */

interface ClickListener {
    fun onClick(pos:Int, news: News)
}
