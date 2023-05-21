package com.example.templatesampleapp.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

object Constant {

    //val BASE_URL="http://192.168.10.8/quotes/"
    val BASE_URL="https://smartappsdevelopment.000webhostapp.com/quotes/"
    val BASE_URL_IMAGE="https://smartappsdevelopment.000webhostapp.com/quotes/image"
    @JvmField
    var DB_NAME = "quotes_app.db"
    var TABLE_NAME_BIOS_DATA="biosdata"
    const val TABLE_NAME_TYPE="biosinfo"

    // Table Category in the database.
    const val TBL_CATEGORY = "tbl_category"
    const val TBL_BEST_QUOTE = "tbl_best_quote"
    const val TBL_CATEGORY_COLUMN_ID = "cat_id"
    const val TBL_CATEGORY_COLUMN_NAME = "cat_name"
    const val TBL_CATEGORY_COLUMN_IMAGE = "cat_image"

    // Table Author in the database.
    const val TBL_AUTHOR = "tbl_author"
    const val TBL_AUTHOR_COLUMN_ID = "author_id"
    const val TBL_AUTHOR_COLUMN_NAME = "author_name"
    const val TBL_AUTHOR_COLUMN_IMAGE = "author_image"

    // Table Topics in the database.
    const val TBL_QUOTES = "tbl_quotes"
    const val TBL_QUOTES_FAV = "tbl_quotes_fav"
    const val TBL_LATEST_QUOTES = "tbl_latest_quotes"
    const val TBL_BOOKMARK = "tbl_bookmark"
    const val TBL_QUOTES_COLUMN_ID = "quotes_id"
    const val TBL_QUOTES_CAT_COLUMN_ID = "cat_id"
    const val TBL_QUOTES_COLUMN_IMAGE = "quotes_image"
    const val TBL_QUOTES_COLUMN_NAME = "quotes_name"
    const val TBL_QUOTES_COLUMN_DETAILS = "quotes_details"
    const val TBL_QUOTES_COLUMN_ISFAVORITE = "quotes_isfav"
    const val TBL_QUOTES_COLUMN_DATE = "quotes_date"
    const val TBL_QUOTES_BOOKMARK = "bookmark"
    const val TBL_ID = "id"

}

object DATA_TRANSFER{
    const val QUOTES_TYPE = "QuotesType"
}


inline fun <reified T> T.showLog(msg: String,tag:String=this!!::class.java.name) {
    Log.e(tag, msg)
}


fun hasNetwork(context: Context): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}

