package com.example.templatesampleapp.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.codemybrainsout.ratingdialog.RatingDialog
import com.example.templatesampleapp.R
import com.example.templatesampleapp.adapter.BannerPagerAdapter
import com.example.templatesampleapp.adapter.CategoryQuotesAdpter
import com.example.templatesampleapp.adapter.QuoteStaggersAdapter
import com.example.templatesampleapp.base.BaseFragment
import com.example.templatesampleapp.databinding.FragmentHomeBinding
import com.example.templatesampleapp.helper.showToast
import com.example.templatesampleapp.helper.showToastTemp
import com.example.templatesampleapp.model.ui.UiQuoteModel
import com.example.templatesampleapp.ui.activmain.MainActivity
import com.example.templatesampleapp.ui.fragments.category.CategoryFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Umer Bilal
 * Created 4/1/2023 at 3:49 PM
 */

@AndroidEntryPoint
class HomeFragment @Inject constructor() :
    BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    val adapter = CategoryQuotesAdpter() {
        findNavController().navigate(
            CategoryFragmentDirections.actionGlobalGridFragment(
                false,
                it.quote,
                (it.id).toString()
            )
        )
    }
    val famouseQuoteadapter = QuoteStaggersAdapter()

    private val sharedViewModel: CommonViewModel by activityViewModels()
    private var currentCount = 0

    private val bannerPagerAdapter by lazy {
        BannerPagerAdapter(requireContext(), sharedViewModel.existingdb.bestQuotes)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvcategory.adapter = adapter
        binding.rvfamousequote.adapter = famouseQuoteadapter
        fromdb()
        binding.btnMoreCategory.setOnClickListener {
            (requireActivity() as MainActivity).binding.bottomNavigation.selectedItemId =
                R.id.categoryFragment
        }
        setHasOptionsMenu(true)
    }

    private fun fromdb() {
        val size = sharedViewModel.existingdb.allCategories.map {
            UiQuoteModel(it.cat_id, it.cat_name, it.newBiosType, it.cat_image)
        }
        adapter.submitList(size)

        val famouseQuote = sharedViewModel.existingdb.latestQuotes.map {
            UiQuoteModel(1, it.quotes_name!!, "", null)
        }
        famouseQuoteadapter.submitList(famouseQuote)


        binding.viewPager.adapter = bannerPagerAdapter
        binding.indicator!!.setViewPager(binding.viewPager)
        playViewPager(binding.viewPager)
    }

    private fun playViewPager(viewPager: ViewPager?) {
        viewPager!!.postDelayed({
            try {
                if (viewPager.adapter!!.count > 0) {
                    val position = currentCount % bannerPagerAdapter!!.count
                    currentCount++
                    viewPager.currentItem = position
                    playViewPager(viewPager)
                }
            } catch (e: Exception) {
            }
        }, 5000)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.more_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        setClickOtpopMenu()
        return true
    }

    private fun setClickOtpopMenu() {
        val popup = PopupMenu(context!!, binding.refmenuview)
        popup.setForceShowIcon(true)
        popup.menuInflater.inflate(R.menu.menu_toplist, popup.menu)


//        if (popup.menu is MenuBuilder) {
//            val menuBuilder = popup.menu as MenuBuilder
//            menuBuilder.setOptionalIconsVisible(true)
//            for (item in menuBuilder.visibleItems) {
//                val iconMarginPx =
//                    TypedValue.applyDimension(
//                        TypedValue.COMPLEX_UNIT_DIP, 25.toFloat(), resources.displayMetrics)
//                        .toInt()
//                if (item.icon != null) {
//                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//                        item.icon = InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx,0)
//                    } else {
//                        item.icon =
//                            object : InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx, 0) {
//                                override fun getIntrinsicWidth(): Int {
//                                    return intrinsicHeight + iconMarginPx + iconMarginPx
//                                }
//                            }
//                    }
//                }
//            }
//        }


        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            // Respond to menu item click.
            when (menuItem.itemId) {
                R.id.menu_share -> {
                    showToastTemp("Share")
                }
                R.id.menu_about -> {
                    showToastTemp("menu_about")
                }
                R.id.menu_privacy -> {
                    showToastTemp("menu_privacy")
                }
                R.id.menu_rateus -> {
                    showToastTemp("menu_rateus")
                    showRatingDialog()
                }
            }
            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

    private fun showRatingDialog() {
        val ratingDialog: RatingDialog = RatingDialog.Builder(requireContext())
            .title(R.string.tiltrateus)
            .threshold(3)
            .session(1)
            .playstoreUrl("https://play.google.com/store/apps/details?id=com.mofept.govpk.teleschool")
            .onRatingBarFormSubmit { feedback ->
                showToast("Feedback Submitted ")
                Log.i("TAGgered", "onRatingBarFormSubmit: $feedback")
            }
            .build()

        ratingDialog.show()
    }

    override fun baseCheckAllow(): Boolean = false


//    fun getSampleItems(): ArrayList<UiQuoteModel> {
//        val list = arrayListOf<UiQuoteModel>()
//        for (i in 0..100)
//            list.add(UiQuoteModel(i, "hi from quote $i", "dsf"))
//        return list
//    }


}