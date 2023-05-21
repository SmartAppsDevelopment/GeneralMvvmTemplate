package com.example.templatesampleapp.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.templatesampleapp.R
import com.example.templatesampleapp.databinding.LatestAuthorItemBinding
import com.example.templatesampleapp.databinding.LatestQuotesItemBinding
import com.example.templatesampleapp.model.ui.UiQuoteModel

/**
 * @author Umer Bilal
 * Created 5/17/2023 at 7:42 PM
 */
class AuthorStaggersAdapter(val callBack:(UiQuoteModel)->Unit) :
    ListAdapter<UiQuoteModel, AuthorStaggersAdapter.AuthorStaggersAdapterHolder>(
        AuthorStaggersAdapterDiffCallback()
    ) {

    class AuthorStaggersAdapterHolder(val binding: LatestAuthorItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorStaggersAdapterHolder {
        val inlfate = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<LatestAuthorItemBinding>(
            inlfate,
            R.layout.latest_author_item,
            parent,
            false
        )
        return AuthorStaggersAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: AuthorStaggersAdapterHolder, position: Int) {
        val res = getItem(position).cat_image!!
        val bitmap = BitmapFactory.decodeByteArray(res, 0, res.size)
        holder.binding.imgAuthor.setImageBitmap(bitmap)
        holder.binding.txtAuthorName.text=getItem(position).quote
        holder.binding.txtQuotesCount.text=getItem(position).category
        holder.binding.cardview.setOnClickListener {
            callBack(getItem(position))
        }
    }

}


class AuthorStaggersAdapterDiffCallback : DiffUtil.ItemCallback<UiQuoteModel>() {

    override fun areItemsTheSame(oldItem: UiQuoteModel, newItem: UiQuoteModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UiQuoteModel, newItem: UiQuoteModel): Boolean {
        return oldItem.id == newItem.id && oldItem.id == newItem.id
    }
}

