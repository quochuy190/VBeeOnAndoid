package com.vsm.ambientmode.ui.timer


import androidx.recyclerview.widget.DiffUtil
import com.vn.vbeeon.R
import com.vn.vbeeon.domain.model.ObjHtmlData
import com.vn.vbeeon.domain.model.User
import com.vsm.ambientmode.app.base.BaseAdapter


class UserAdapter(onClick: (position: Int, item: User) -> Unit) : BaseAdapter<User>(onClick) {

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_user
    }

    override fun getItemForPosition(position: Int): User {
        return mListData[position]
    }

    override fun getDiffCallback(oldList: List<User>, newListData: List<User>): DiffUtil.Callback {
        return UserDiffCallback(oldList, newListData)
    }
}