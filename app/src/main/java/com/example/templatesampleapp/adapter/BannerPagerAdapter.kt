package com.example.templatesampleapp.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.templatesampleapp.R
import com.example.templatesampleapp.model.QuotesModel

class BannerPagerAdapter(
    private val context: Context,
    private val quotesDayList: ArrayList<QuotesModel>
) : PagerAdapter() {
    lateinit var imageLayout: View
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return quotesDayList.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        imageLayout = inflater.inflate(R.layout.view_pager_items, view, false)
        var pos = position + 1
        assert(imageLayout != null)
        val imageView = imageLayout
            .findViewById<ImageView>(R.id.img_view_item)
        val txtBannerText = imageLayout.findViewById<TextView>(R.id.txt_banner_quote)
        val txtQuotesDay = imageLayout.findViewById<TextView>(R.id.txt_quote_day)
        val topicsModel = quotesDayList[position]
        if (topicsModel.quotes_image != null) {
            val res = topicsModel.quotes_image
            val bitmap = BitmapFactory.decodeByteArray(res, 0, res!!.size)
            imageView.setImageBitmap(bitmap)
        }
        if (topicsModel.quotes_name != null) {
            txtBannerText.text = topicsModel.quotes_name
        }
        txtQuotesDay.text = "Best Quotes #" + pos++
        view.addView(imageLayout, 0)
        imageLayout.setOnClickListener(View.OnClickListener {

        })
        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}
    override fun saveState(): Parcelable? {
        return null
    }

}