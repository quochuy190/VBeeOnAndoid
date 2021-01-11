package com.vsm.ambientmode.app.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vn.vbeeon.common.extensions.handleFocusChange
import com.vn.vbeeon.common.extensions.setOnClickAction


abstract class BaseAdapter<T>(
    private val onClick: (position: Int, item: T) -> Unit,
    private val onFocus: ((position: Int) -> Unit)? = null
) : RecyclerView.Adapter<BaseAdapter<T>.MyViewHolder>() {
    protected val mListData = mutableListOf<T>()
    fun setData(newListData: List<T>) {
        if (newListData.isEmpty()) {
            mListData.clear()
            notifyDataSetChanged()
            return
        }
        val diffResult = DiffUtil.calculateDiff(getDiffCallback(mListData, newListData))
        diffResult.dispatchUpdatesTo(this)
        mListData.clear()
        mListData.addAll(newListData)
    }

    abstract fun getDiffCallback(oldList: List<T>, newListData: List<T>): DiffUtil.Callback
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: T = getItemForPosition(position)
        holder.itemView.setOnClickAction { onClick(position, item) }
        holder.bind(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else {
            val item: T = getItemForPosition(position)
            holder.itemView.setOnClickAction { onClick(position, item) }
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int = mListData.size
    override fun getItemViewType(position: Int): Int = getLayoutIdForPosition(position)
    abstract fun getLayoutIdForPosition(position: Int): Int
    abstract fun getItemForPosition(position: Int): T
    inner class MyViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            itemView.handleFocusChange(itemView.context) {
                onFocus?.invoke(adapterPosition)
            }
            binding.setVariable(BR.data, item)
            binding.executePendingBindings()
        }
    }
}