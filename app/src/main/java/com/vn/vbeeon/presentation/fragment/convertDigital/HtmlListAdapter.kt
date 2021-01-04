package com.vn.vbeeon.presentation.fragment.convertDigital

import androidx.recyclerview.widget.DiffUtil
import com.vn.vbeeon.R
import com.vn.vbeeon.domain.model.ObjHtmlData
import com.vn.vbeeon.presentation.base.MyBaseAdapter


class TimerAdapter(onClick: (position: Int, item: ObjHtmlData) -> Unit) : MyBaseAdapter<ObjHtmlData>(onClick) {

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_list_html
    }

    override fun getItemForPosition(position: Int): ObjHtmlData {
        return mListData[position]
    }

    override fun getDiffCallback(oldList: List<ObjHtmlData>, newListData: List<ObjHtmlData>): DiffUtil.Callback {
        return HtmlDiffCallback(oldList, newListData)
    }
}