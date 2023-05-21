package com.example.templatesampleapp.ui.fragments.sliderfragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.templatesampleapp.R
import com.example.templatesampleapp.adapter.generaladapter.BaseAdapterCustom
import com.example.templatesampleapp.base.BaseFragment
import com.example.templatesampleapp.databinding.ContentPagerItemConstrainBinding
import com.example.templatesampleapp.databinding.FragmentQuotesliderBinding
import com.example.templatesampleapp.helper.ViewPager2PageTranFormer
import com.example.templatesampleapp.helper.copyText
import com.example.templatesampleapp.helper.shareText
import com.example.templatesampleapp.helper.showToastTemp
import com.example.templatesampleapp.model.ui.UiQuoteModel
import com.example.templatesampleapp.model.ui.UiQuoteModelDB
import com.example.templatesampleapp.ui.fragments.home.CommonViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Umer Bilal
 * Created 4/1/2023 at 3:49 PM
 */

@AndroidEntryPoint
class QuoteSliderFragment @Inject constructor() :
    BaseFragment<FragmentQuotesliderBinding>(R.layout.fragment_quoteslider) {

    val args by navArgs<QuoteSliderFragmentArgs>()
    private val sharedViewModel: CommonViewModel by activityViewModels()
    val mAdapterSlider = BaseAdapterCustom<UiQuoteModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPagerDetails!!.setPageTransformer(ViewPager2PageTranFormer())
        binding.toptitle.text = args.toptitle
        binding.toptitle.apply {
            ellipsize = TextUtils.TruncateAt.MARQUEE;
            isSingleLine = true;
            isSelected = true;
        }
        addObserver()
        inflateAdatper()
    }

    private fun addObserver() {
        sharedViewModel.sharedUIQuoteModel.observe(viewLifecycleOwner) {
            mAdapterSlider.listOfItems =
                it.map { UiQuoteModel(it.id.toInt(), it.quote, "", null) }.toMutableList()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.viewPagerDetails.setCurrentItem(args.index, true)

            }, 500)
        }
        binding.viewPagerDetails.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.txtQuotecount.text =
                    " Quote (${position + 1}/${sharedViewModel.sharedUIQuoteModel.value?.size ?: 0})"
            }
        })

    }

    private fun inflateAdatper() {

        mAdapterSlider.expressionViewHolderBinding = { eachItem, viewBinding ->
            (viewBinding as ContentPagerItemConstrainBinding).apply {
                val htmldata = HtmlCompat.fromHtml(
                    "" + eachItem.quote,
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                )
                this.txtQuotesDetail.text = htmldata
                imgCopy.setOnClickListener { requireContext().copyText(this.txtQuotesDetail.text.toString()) }
                imgShare.setOnClickListener { requireContext().shareText(this.txtQuotesDetail.text.toString()) }

                sharedViewModel.localDb.userDao().getFromDb(txtQuotesDetail.text.toString())?.let {
                    imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_fill_24)
                }?: kotlin.run {
                    imgFavorite.setImageResource(R.drawable.heart)
                }
                imgFavorite.setOnClickListener {
                    if(imgFavorite.tag=="f"){
                        imgFavorite.setImageResource(R.drawable.heart)
                        imgFavorite.tag="n"
                        val inserted=sharedViewModel.localDb.userDao().deleteItem(eachItem.toDbTypeModel())
                        showToastTemp("Delete $inserted")
                    }else{
                        imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_fill_24)
                        imgFavorite.tag="f"
                        val inserted=sharedViewModel.localDb.userDao().insertAll(eachItem.toDbTypeModel())
                        showToastTemp("Insert $inserted")
                    }

                }
            }

        }
        mAdapterSlider.expressionOnCreateViewHolder = { viewGroup ->
            ContentPagerItemConstrainBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        }
        binding.viewPagerDetails.apply {
            adapter = mAdapterSlider
        }
    }

    override fun baseCheckAllow(): Boolean = false

}