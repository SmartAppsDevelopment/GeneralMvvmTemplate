package com.example.templatesampleapp.ui.fragments.quotesgrid

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.templatesampleapp.R
import com.example.templatesampleapp.adapter.QuoteStaggersAdapter
import com.example.templatesampleapp.base.BaseFragment
import com.example.templatesampleapp.databinding.FragmentGridBinding
import com.example.templatesampleapp.helper.showLog
import com.example.templatesampleapp.helper.showToast
import com.example.templatesampleapp.model.network.quotes.toQuotesModel
import com.example.templatesampleapp.model.ui.UiQuoteModel
import com.example.templatesampleapp.ui.fragments.home.CommonViewModel
import com.example.templatesampleapp.utils.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Umer Bilal
 * Created 4/1/2023 at 3:49 PM
 */

@AndroidEntryPoint
class GridFragment @Inject constructor() :
    BaseFragment<FragmentGridBinding>(R.layout.fragment_grid) {
    companion object {
        var IS_CATEGORY = true
    }

    val args by navArgs<GridFragmentArgs>()
    val famouseQuoteadapter = QuoteStaggersAdapter { pos, item ->
        sharedViewModel.sharedUIQuoteModel.value =
            (binding.rvfamousequote.adapter as QuoteStaggersAdapter).currentList
        findNavController().navigate(
            GridFragmentDirections.actionGlobalQuoteSliderFragment(
                args.toptitle,
                pos
            )
        )
    }
    private var isLoading = false
    private var isLastPage = false
    private var pageCounter = 1
    private val sharedViewModel: CommonViewModel by activityViewModels()
    val getQuoteType by lazy { args.currentTypeId }
    lateinit var layoutManager: StaggeredGridLayoutManager

    init {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.mainUiEvents.collect {
                    when (it) {
                        is CommonViewModel.MainUiEventsGeneral.Error -> {
                            showToast(it.msg)
                            binding.spinKit.isVisible = false
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvfamousequote!!.layoutManager = layoutManager
        binding.rvfamousequote.adapter = famouseQuoteadapter
        if (args.isFromRemote) {
            fromRemotedb()
            addObserver()
        } else {
            fromLocalDb()
        }
        setTopTitle()
    }

    private fun setTopTitle() {
        binding.toptitle.setText(args.toptitle)
        binding.toptitle.apply {
            ellipsize = TextUtils.TruncateAt.MARQUEE;
            isSingleLine = true;
            isSelected = true;
        }
    }

    private fun addObserver() {


        sharedViewModel.getRemoteQuotesData.observe(viewLifecycleOwner) {

            val newList = famouseQuoteadapter.currentList.toMutableList()
                .apply { addAll(it.map { it.toQuotesModel() }) }
            famouseQuoteadapter.submitList(newList)
            isLoading = false
            isLastPage = false
        }


        binding.rvfamousequote.addOnScrollListener(object :
            PaginationScrollListener(layoutManager = layoutManager) {
            override fun loadMoreItems() {
                val finalDec = !isLastPage && !isLoading /*&& START_INDEX > 0*/
                showLog("Final Dec is = $finalDec", "QuotesModelListFound")
                if (finalDec) {
                    isLoading = true
                    binding.spinKit.isVisible = true

                    Handler().postDelayed({

                        sharedViewModel.getQuotesList(
                            getQuoteType,
                            (++pageCounter).toString()
                        )

                    }, 1000)
                }
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
    }

    private fun fromRemotedb() {
        lifecycleScope.launch {
            if (args.isFromRemote)
                sharedViewModel.getQuotesList(getQuoteType, pageCounter.toString())
        }
    }

    private fun fromLocalDb() {
        sharedViewModel.existingdb.getAllquotessByCatAuth(args.currentTypeId.toInt(), IS_CATEGORY)
            .apply {
                binding.spinKit.isVisible = false
                val newMappedList =
                    this.map { UiQuoteModel(it.id?.toInt() ?: 1, it.quotes_name!!, "", null) }
                famouseQuoteadapter.submitList(newMappedList)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        //Nutralized effect of author select
        IS_CATEGORY = true
    }

    override fun baseCheckAllow(): Boolean = false


//    fun getSampleItems(): ArrayList<UiQuoteModel> {
//        val list = arrayListOf<UiQuoteModel>()
//        for (i in 0..100)
//            list.add(UiQuoteModel(i, "hi from quote $i", "dsf"))
//        return list
//    }


}