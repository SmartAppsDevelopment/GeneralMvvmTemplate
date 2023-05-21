package com.example.templatesampleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.templatesampleapp.R
import com.example.templatesampleapp.databinding.LatestQuotesItemBinding
import com.example.templatesampleapp.model.ui.UiQuoteModel

/**
 * @author Umer Bilal
 * Created 5/17/2023 at 7:42 PM
 */
class QuoteStaggersAdapter(val itemClick:((Int,UiQuoteModel)->Unit)?=null) :
    ListAdapter<UiQuoteModel, QuoteStaggersAdapter.QuoteStaggersAdapterHolder>(
        QuoteStaggersAdapterDiffCallback()
    ) {

    class QuoteStaggersAdapterHolder(val binding: LatestQuotesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteStaggersAdapterHolder {
        val inlfate = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<LatestQuotesItemBinding>(
            inlfate,
            R.layout.latest_quotes_item,
            parent,
            false
        )
        return QuoteStaggersAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteStaggersAdapterHolder, position: Int) {
        val htmldata = HtmlCompat.fromHtml(
            "" + getItem(position).quote,
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
        holder.binding.txtCategoryName.text = htmldata
        holder.binding.mcvQuote.setOnClickListener {
            itemClick?.invoke(holder.adapterPosition,getItem(holder.adapterPosition))
        }
    }


}


class QuoteStaggersAdapterDiffCallback : DiffUtil.ItemCallback<UiQuoteModel>() {

    override fun areItemsTheSame(oldItem: UiQuoteModel, newItem: UiQuoteModel): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: UiQuoteModel, newItem: UiQuoteModel): Boolean {
        return oldItem.equals(newItem)
    }
}

