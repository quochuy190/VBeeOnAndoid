package com.vsm.ambientmode.ui.timer


import androidx.recyclerview.widget.DiffUtil
import com.vn.vbeeon.R
import com.vn.vbeeon.domain.model.ObjHtmlData
import com.vsm.ambientmode.app.base.BaseAdapter


class HtmlAdapter(onClick: (position: Int, item: ObjHtmlData) -> Unit) : BaseAdapter<ObjHtmlData>(onClick) {

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_html
    }

    override fun getItemForPosition(position: Int): ObjHtmlData {
        return mListData[position]
    }

    override fun getDiffCallback(oldList: List<ObjHtmlData>, newListData: List<ObjHtmlData>): DiffUtil.Callback {
        return HtmlDiffCallback(oldList, newListData)
    }
}