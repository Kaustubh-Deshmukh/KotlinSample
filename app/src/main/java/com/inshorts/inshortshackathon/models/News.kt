package com.inshorts.inshortshackathon.models

import android.os.Parcel
import android.os.Parcelable
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
                val TIMESTAMP: Long) : Parcelable {

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(ID)
        dest?.writeString(TITLE)
        dest?.writeString(URL)
        dest?.writeString(PUBLISHER)
        dest?.writeString(CATEGORY)
        dest?.writeString(HOSTNAME)
        dest?.writeLong(TIMESTAMP)
    }

    protected constructor(parcelIn: Parcel) : this (
            parcelIn.readInt(),
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readLong()
    )

    override fun describeContents() = 0

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = object : Parcelable.Creator<News> {
            override fun createFromParcel(i: Parcel): News {
                return News(i)
            }

            override fun newArray(size: Int): Array<News?> {
                return arrayOfNulls(size)
            }
        }
    }

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
