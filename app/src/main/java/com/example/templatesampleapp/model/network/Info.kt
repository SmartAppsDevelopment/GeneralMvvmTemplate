package com.example.templatesampleapp.model.network

import com.example.templatesampleapp.model.network.Data

data class Info(
    val data: List<Data>,
    val msg: String,
    val status: String
)