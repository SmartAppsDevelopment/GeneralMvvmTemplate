package com.example.templatesampleapp.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.templatesampleapp.R
import com.example.templatesampleapp.databinding.ItemLayoutQuoteModelBinding
import com.example.templatesampleapp.model.ui.UiQuoteModel

/**
 * @author Umer Bilal
 * Created 5/17/2023 at 7:42 PM
 */
// class CategoryBaseQuotesAdpter : BaseQuickAdapter<UiQuoteModel,BaseViewHolder<UiQuoteModel,ItemLayoutQuoteModelBinding>>() {
//    override fun onBindViewHolder(
//        holder: BaseViewHolder<UiQuoteModel, ItemLayoutQuoteModelBinding>,
//        position: Int,
//        item: UiQuoteModel?
//    ) {
//        return DataBindingHolder(
//            DataBindingUtil.inflate<ItemLayoutQuoteModelBinding>(layoutInflater,
//                R.layout.item_layout_quote_model,parent,false))
//
//    }
//
//    override fun onCreateViewHolder(
//        context: Context,
//        parent: ViewGroup,
//        viewType: Int
//    ): BaseViewHolder<UiQuoteModel, ItemLayoutQuoteModelBinding> {
//        val layoutInflater=LayoutInflater.from(parent.context).inflate(R.layout.item_layout_quote_model,parent,)
//        return DataBindingHolder(DataBindingUtil.inflate<ItemLayoutQuoteModelBinding>(layoutInflater,R.layout.item_layout_quote_model,parent,false))
//
//    }
//
//
//}

class CategoryQuotesAdpter(val callBack:(UiQuoteModel)->Unit) :
    ListAdapter<UiQuoteModel, CategoryQuotesAdpter.mViewHolder>(TemperatureModelDiffCallback()) {

        class mViewHolder(val binding:ItemLayoutQuoteModelBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        val inlfate=LayoutInflater.from(parent.context)
        val binding=DataBindingUtil.inflate<ItemLayoutQuoteModelBinding>(inlfate,R.layout.item_layout_quote_model,parent,false)
        return mViewHolder(binding)
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
       holder.binding.model=getItem(position)
        val res = getItem(position).cat_image!!
        val bitmap = BitmapFactory.decodeByteArray(res, 0, res.size)
        holder.binding.ivCategoryimg.setImageBitmap(bitmap)
        holder.binding.txtQuotesCount.isVisible=false
        holder.binding.cvcontainer.setOnClickListener {

            callBack.invoke(getItem(position))
        }
    }

}



class TemperatureModelDiffCallback : DiffUtil.ItemCallback<UiQuoteModel>() {

    override fun areItemsTheSame(oldItem: UiQuoteModel, newItem: UiQuoteModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UiQuoteModel, newItem: UiQuoteModel): Boolean {
        return oldItem.id == newItem.id && oldItem.id == newItem.id
    }
}

