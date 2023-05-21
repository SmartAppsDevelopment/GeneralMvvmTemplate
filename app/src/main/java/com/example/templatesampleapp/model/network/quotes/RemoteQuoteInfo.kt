package com.example.templatesampleapp.model.network.quotes

import com.example.templatesampleapp.model.network.quotes.RemoteQuoteData

data class RemoteQuoteInfo(
    val data: List<RemoteQuoteData>,
    val msg: String,
    val status: String
)