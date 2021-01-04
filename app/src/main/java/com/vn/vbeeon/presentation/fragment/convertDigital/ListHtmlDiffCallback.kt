package com.vn.vbeeon.presentation.fragment.convertDigital

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.vn.vbeeon.domain.model.ObjHtmlData
import timber.log.Timber

class HtmlDiffCallback(
    private val oldHtmlList: List<ObjHtmlData?>,
    private val newHtmlList: List<ObjHtmlData?>): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldHtmlList[oldItemPosition] == null || newHtmlList[newItemPosition] == null) {
            return false
        }
        return oldHtmlList[oldItemPosition]?.id == newHtmlList[newItemPosition]?.id &&
                oldHtmlList[oldItemPosition]?.content == newHtmlList[newItemPosition]?.content&&
                oldHtmlList[oldItemPosition]?.title == newHtmlList[newItemPosition]?.title
    }

    override fun getOldListSize(): Int = oldHtmlList.size

    override fun getNewListSize(): Int = newHtmlList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldHtmlList[oldItemPosition] == null) {
            return newHtmlList[newItemPosition] == null
        }
        return oldHtmlList[oldItemPosition]?.content == newHtmlList[newItemPosition]?.content
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        Timber.d("Selected change")
        return Bundle().apply {
            putBoolean("KEY_SELECTED_CHANGE", true)
        }
    }
}