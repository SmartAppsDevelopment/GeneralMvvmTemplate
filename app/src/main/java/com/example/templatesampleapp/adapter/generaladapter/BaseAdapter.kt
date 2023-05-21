package com.example.templatesampleapp.adapter.generaladapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @author Umer Bilal
 * Created 5/19/2023 at 5:59 PM
 */
class BaseAdapterCustom<T>: RecyclerView.Adapter<BaseViewHolderCustom<T>>(){
    var listOfItems:MutableList<T>? = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var expressionViewHolderBinding: ((T, ViewBinding) -> Unit)? = null
    var expressionOnCreateViewHolder:((ViewGroup)->ViewBinding)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolderCustom<T> {
        return expressionOnCreateViewHolder?.let { it(parent) }?.let { BaseViewHolderCustom(it, expressionViewHolderBinding!!) }!!
    }

    override fun onBindViewHolder(holder: BaseViewHolderCustom<T>, position: Int) {
        holder.bind(listOfItems!![position])
    }

    override fun getItemCount(): Int {
        return listOfItems!!.size
    }
}