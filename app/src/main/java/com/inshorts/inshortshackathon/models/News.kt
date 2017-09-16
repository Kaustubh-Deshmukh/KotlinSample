package com.inshorts.inshortshackathon.models

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Reader

/**
 * @author : Kaustubh Deshmukh
 * @email : kaustubh.deshmukh27@gmail.com
 */

data class News(val ID: Int, val TITLE: String, val URL: String,
                val PUBLISHER: String, val CATEGORY: String, val HOSTNAME: String,
                val TIMESTAMP: Long) {

    /**
     * I didn't write the following lines of code,
     * However fixes issue from the library
     * See <a href https://github.com/kittinunf/Fuel/issues/233 />
     */
    class Deserializer : ResponseDeserializable<News> {
        override fun deserialize(reader: Reader) = Gson().fromJson(reader, News::class.java)
    }

    class ListDeserializer : ResponseDeserializable<MutableList<News>> {
        override fun deserialize(reader: Reader): MutableList<News> {
            val type = object : TypeToken<MutableList<News>>() {}.type
            return Gson().fromJson(reader, type)
        }
    }

}
