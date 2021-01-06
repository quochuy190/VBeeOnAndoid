package com.vsm.ambientmode.ui.timer

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.vn.vbeeon.domain.model.Device
import com.vn.vbeeon.domain.model.ObjHtmlData
import timber.log.Timber

class AddDeviceDiffCallback(
    private val oldTimers: List<Device?>,
    private val newTimers: List<Device?>): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldTimers[oldItemPosition] == null || newTimers[newItemPosition] == null) {
            return false
        }
        return oldTimers[oldItemPosition]?.id == newTimers[newItemPosition]?.id
    }

    override fun getOldListSize(): Int = oldTimers.size

    override fun getNewListSize(): Int = newTimers.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldTimers[oldItemPosition] == null) {
            return newTimers[newItemPosition] == null
        }
        return oldTimers[oldItemPosition]?.name == newTimers[newItemPosition]?.name
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        Timber.d("Selected change")
        return Bundle().apply {
            putBoolean("KEY_SELECTED_CHANGE", true)
        }
    }
}