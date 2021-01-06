package com.vsm.ambientmode.ui.timer


import androidx.recyclerview.widget.DiffUtil
import com.vn.vbeeon.R
import com.vn.vbeeon.domain.model.Device
import com.vn.vbeeon.domain.model.ObjHtmlData
import com.vsm.ambientmode.app.base.BaseAdapter


class DeviceAddNewAdapter(onClick: (position: Int, item: Device) -> Unit) : BaseAdapter<Device>(onClick) {

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_device_new
    }

    override fun getItemForPosition(position: Int): Device {
        return mListData[position]
    }

    override fun getDiffCallback(oldList: List<Device>, newListData: List<Device>): DiffUtil.Callback {
        return AddDeviceDiffCallback(oldList, newListData)
    }
}