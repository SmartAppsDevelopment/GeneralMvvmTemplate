package com.example.templatesampleapp.ui.fragments.author

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.templatesampleapp.R
import com.example.templatesampleapp.adapter.AuthorStaggersAdapter
import com.example.templatesampleapp.base.BaseFragment
import com.example.templatesampleapp.databinding.FragmentAuthorBinding
import com.example.templatesampleapp.model.ui.UiQuoteModel
import com.example.templatesampleapp.ui.fragments.category.CategoryFragmentDirections
import com.example.templatesampleapp.ui.fragments.home.CommonViewModel
import com.example.templatesampleapp.ui.fragments.quotesgrid.GridFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Umer Bilal
 * Created 4/1/2023 at 3:49 PM
 */

@AndroidEntryPoint
class AuthorFragment @Inject constructor() :
    BaseFragment<FragmentAuthorBinding>(R.layout.fragment_author) {

    val adapter = AuthorStaggersAdapter(){
        GridFragment.IS_CATEGORY=false
        findNavController().navigate(
            CategoryFragmentDirections.actionGlobalGridFragment(
            false,
            it.quote,
            (it.id).toString()
        ))
    }

    private val sharedViewModel: CommonViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fromdb()

    }

    private fun fromdb() {


        binding.rvfamousequote.adapter = adapter
        val size = sharedViewModel.existingdb.allAuthor.map {
            val categoryCount = sharedViewModel.existingdb.getQuotesCountAuthor(it.author_id)
            UiQuoteModel(it.author_id, it.author_name, "$categoryCount Quotes", it.author_image)
        }
        adapter.submitList(size)


    }


//    fun getSampleItems(): ArrayList<UiQuoteModel> {
//        val list = arrayListOf<UiQuoteModel>()
//        for (i in 0..100)
//            list.add(UiQuoteModel(i, "hi from quote $i", "dsf"))
//        return list
//    }


}