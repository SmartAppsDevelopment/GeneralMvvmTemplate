package com.example.templatesampleapp.model.network.quotes

import com.example.templatesampleapp.model.QuotesModel
import com.example.templatesampleapp.model.ui.UiQuoteModel


data class RemoteQuoteData(
    val id: String,
    val lang: String,
    val quote: String,
    val type: String
)

fun RemoteQuoteData.toQuotesModel()=
    UiQuoteModel(id.toInt(),quote,type,null)
