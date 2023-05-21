package com.example.templatesampleapp.ui.fragments.category

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.templatesampleapp.R
import com.example.templatesampleapp.adapter.generaladapter.BaseAdapterCustom
import com.example.templatesampleapp.base.BaseFragment
import com.example.templatesampleapp.databinding.FragmentCategoryBinding
import com.example.templatesampleapp.databinding.ItemLayoutQuoteModelBinding
import com.example.templatesampleapp.databinding.ItemLayoutQuoteModelRemoteBinding
import com.example.templatesampleapp.helper.Constant.BASE_URL_IMAGE
import com.example.templatesampleapp.helper.showToast
import com.example.templatesampleapp.model.ui.UiQuoteModel
import com.example.templatesampleapp.ui.fragments.home.CommonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Umer Bilal
 * Created 4/1/2023 at 3:49 PM
 */

@AndroidEntryPoint
class CategoryFragment @Inject constructor() :
    BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

    val listOfImages = arrayListOf<Int>(
        R.drawable.cowboy_hat_face,
        R.drawable.joker,
        R.drawable.clonetheme_high_contrast,
        R.drawable.smiling_face_with_sunglasses
    )
    private val sharedViewModel: CommonViewModel by viewModels()
    val mAdapter = BaseAdapterCustom<UiQuoteModel>()
    val mAdapterRemote = BaseAdapterCustom<UiQuoteModel>()


    init {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.mainUiEvents.collect {
                    when (it) {
                        is CommonViewModel.MainUiEventsGeneral.Error -> {
                            showToast(it.msg)
                        }
                        CommonViewModel.MainUiEventsGeneral.HideProgressDialog -> {
                            binding.spinKit.isVisible = false
                        }
                        CommonViewModel.MainUiEventsGeneral.ShowProgressDialog -> {
                            binding.spinKit.isVisible = true
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateAdatper()
        inflateAdatperRemoteCategory()
        fromdb()
        addObserver()
    }

    private fun addObserver() {
        sharedViewModel.getQuotesType.observe(viewLifecycleOwner) {
            mAdapterRemote.listOfItems =
                it.map { UiQuoteModel(it.id.toInt(), it.type, "", null) }.toMutableList()
        }
    }

    private fun inflateAdatper() {

        mAdapter.expressionViewHolderBinding = { eachItem, viewBinding ->
            (viewBinding as ItemLayoutQuoteModelBinding).apply {
                model = eachItem
                val res = eachItem.cat_image
                val bitmap = BitmapFactory.decodeByteArray(res, 0, res!!.size)
                ivCategoryimg.setImageBitmap(bitmap)
                cvcontainer.setOnClickListener {
                    findNavController().navigate(CategoryFragmentDirections.actionGlobalGridFragment(
                        false,
                        eachItem.quote,
                        (eachItem.id).toString()
                    ))
                }
            }

        }
        mAdapter.expressionOnCreateViewHolder = { viewGroup ->
            ItemLayoutQuoteModelBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        }
        binding.rvcategory.apply {
            adapter = mAdapter
        }
    }


    private fun inflateAdatperRemoteCategory() {

        mAdapterRemote.expressionViewHolderBinding = { eachItem, viewBinding ->
            (viewBinding as ItemLayoutQuoteModelRemoteBinding).apply {
                model = eachItem
//                val res = eachItem.cat_image
//                val bitmap = BitmapFactory.decodeByteArray(res, 0, res!!.size)
                Glide.with(requireActivity())
                    .load(BASE_URL_IMAGE + "/${eachItem.id}.png")
                    .error(R.drawable.joker)
                    .placeholder(R.drawable.joker)
                    .timeout(2000)
                    .into(ivCategoryimg);

                cvcontainer.setOnClickListener {
                    findNavController().navigate(CategoryFragmentDirections.actionGlobalGridFragment(
                        true,
                        eachItem.quote,
                        (eachItem.id).toString()
                    ))
                }


            }

        }
        mAdapterRemote.expressionOnCreateViewHolder = { viewGroup ->
            ItemLayoutQuoteModelRemoteBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        }
        binding.rvcategoryremote.apply {
            adapter = mAdapterRemote
        }
    }

    private fun fromdb() {
        val size = sharedViewModel.existingdb.allCategories.map {
            UiQuoteModel(it.cat_id, it.cat_name, it.newBiosType, it.cat_image)
        }
        mAdapter.listOfItems = (size).toMutableList()
        sharedViewModel.getQuotesType()
    }

}