package com.example.templatesampleapp.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.templatesampleapp.helper.showLog

/**
 * @author Umer Bilal
 * Created 12/17/2022 at 12:05 PM
 */


abstract class PaginationScrollListener(private var layoutManager: StaggeredGridLayoutManager) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        showLog("$dx -- dy $dy  ","PaginationScrollListener")
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
       // val firstVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPositions()
       val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPositions(null)[0]
        if (!isLoading() && !isLastPage()) {
            showLog("$dx -- dy $dy  Inside ","PaginationScrollListener")
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
            ) {
                showLog("$dx -- dy $dy  Inside 2","PaginationScrollListener")
                loadMoreItems()
            }
        }
    }


    protected abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean
}