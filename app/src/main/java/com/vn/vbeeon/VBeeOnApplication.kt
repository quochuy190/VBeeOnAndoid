package com.vn.vbeeon
import androidx.multidex.MultiDexApplication

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:00
 * Version: 1.0
 */
class VBeeOnApplication : MultiDexApplication() {
    companion object {
        lateinit var instance: VBeeOnApplication
            private set
    }

}