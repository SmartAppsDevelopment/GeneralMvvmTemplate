package com.example.templatesampleapp.model

import java.io.Serializable

class QuotesModel : Serializable {
    var cat_id: Int? = null
    var author_id: Int? = null
    var id: Int? = null
    var quotes_id: String? = null
    var quotes_name: String? = null
    var quotes_date: String? = null
    var quotes_image: ByteArray?=null
    var bookmark: String? = null
    var isBookmared = false
    var is_Quotes_fav: Boolean? = null
        get() = field
        set(is_quotes_fav) {
            field = is_quotes_fav
        }

    constructor(
        quotes_id: String?, cat_id: Int?, quotes_name: String?,
        quotes_date: String?, is_quotes_fav: Boolean?, author_id: Int?
    ) {
        this.quotes_id = quotes_id
        this.cat_id = cat_id
        this.author_id = author_id
        this.quotes_name = quotes_name
        this.quotes_date = quotes_date
        is_Quotes_fav = is_quotes_fav
    }

    constructor(
        quotes_id: String?,
        quotes_name: String?,
        quotes_date: String?,
        is_quotes_fav: Boolean?
    ) {
        this.quotes_id = quotes_id
        this.quotes_name = quotes_name
        this.quotes_date = quotes_date
        is_Quotes_fav = is_quotes_fav
    }

    constructor(quotes_id: String?, quotes_name: String?, quotes_image: ByteArray) {
        this.quotes_id = quotes_id
        this.quotes_name = quotes_name
        this.quotes_image = quotes_image
    }
}