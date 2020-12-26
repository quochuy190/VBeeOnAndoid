package com.vn.vbeeon.utils

import android.content.Context
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 25-December-2020
 * Time: 22:45
 * Version: 1.0
 */
class LocalJSONParser {
    companion object {
        fun inputStreamToString(inputStream: InputStream): String {
            try {
                val bytes = ByteArray(inputStream.available())
                inputStream.read(bytes, 0, bytes.size)
                return String(bytes)
            } catch (e: IOException) {
                return ""
            }
        }
    }
}
