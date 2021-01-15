package com.vn.vbeeon.presentation.adapter.deviceHome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vn.vbeeon.R
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.domain.model.Device
import kotlinx.android.synthetic.main.item_device_local.view.*
import kotlinx.android.synthetic.main.item_device_new.view.*


class DeviceLocalAdapter(
    val itemClick: (Device) -> Unit,
    val deleteClick: (Device) -> Unit,
    val onClickDetail: (item: Device) -> Unit
) : RecyclerView.Adapter<DeviceLocalAdapter.ItemViewHoder>() {
    var frames: List<Device> = mutableListOf()

    inner class ItemViewHoder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                itemClick(frames[layoutPosition])
            }
            itemView.imgDeleteDevice.setOnSafeClickListener {
                deleteClick(frames[layoutPosition])
            }
        }

        fun bindData(device: Device) {
           // itemView.tvDeviceCategoryNew.text = device.categoryName
            itemView.tvDeviceName.text = device.name
            itemView.tvDeviceStatus.text = "Mất kết nối"
            itemView.tvConnectBT.text = "BMPM-TUHT-BMPM-TUHT-BMPM"
            if (device.intSource != 0) {
                Glide.with(itemView.context).load(device.intSource).into(itemView.imgDevice)
            } else
                Glide.with(itemView.context).load(device.intSource).into(itemView.imgDevice)
        }
    }

//    fun addData(device`: Device) {
//        frames.add(dashboad)
//        notifyItemChanged(frames.size - 1)
//    }

    fun setData(data:List<Device>) {
        frames = data
        notifyDataSetChanged()
    }

    fun updateData(todo: Device) {
        notifyItemChanged(frames.indexOf(todo))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHoder {
        val holder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_device_local, parent, false)
        return ItemViewHoder(holder)
    }

    override fun getItemCount(): Int {
        return frames.size
    }

    override fun onBindViewHolder(holder: ItemViewHoder, position: Int) {
        holder.bindData(frames[position])

    }

}