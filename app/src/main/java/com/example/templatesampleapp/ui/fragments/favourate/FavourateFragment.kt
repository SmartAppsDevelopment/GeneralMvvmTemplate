package com.example.templatesampleapp.ui.fragments.favourate

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.templatesampleapp.R
import com.example.templatesampleapp.adapter.QuoteStaggersAdapter
import com.example.templatesampleapp.base.BaseFragment
import com.example.templatesampleapp.databinding.FragmentFavourateBinding
import com.example.templatesampleapp.ui.activmain.MainActivity
import com.example.templatesampleapp.ui.fragments.home.CommonViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Umer Bilal
 * Created 4/1/2023 at 3:49 PM
 */

@AndroidEntryPoint
class FavourateFragment @Inject constructor() :
    BaseFragment<FragmentFavourateBinding>(R.layout.fragment_favourate) {

    val famouseQuoteadapter = QuoteStaggersAdapter { pos, item ->
        sharedViewModel.sharedUIQuoteModel.value =
            (binding.rvfamousequote.adapter as QuoteStaggersAdapter).currentList
    }

    private val sharedViewModel: CommonViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvfamousequote.adapter = famouseQuoteadapter
        fromLocalDb()
    }

    private fun fromLocalDb() {
        sharedViewModel.localDb.userDao().getAllFromDb()
            .apply {
                val newMappedList = this.map { it.toUiTypeModel() }
                    binding.groupemtpylayout.isVisible=newMappedList.isNullOrEmpty().apply {
                        if(this){
                            binding.imgFavorite.bringToFront()
                            binding.emptytext.bringToFront()
                            binding.imgFavorite.setOnClickListener {
                                binding.emptytext.callOnClick()
                            }
                            binding.emptytext.setOnClickListener {
                                (requireActivity() as MainActivity).binding.bottomNavigation.selectedItemId=R.id.homeFragment
                            }
                        }
                    }
                famouseQuoteadapter.submitList(newMappedList)
            }

    }

    override fun baseCheckAllow(): Boolean = true

}