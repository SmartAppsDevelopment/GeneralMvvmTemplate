package com.example.templatesampleapp.model.ui

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * @author Umer Bilal
 * Created 5/17/2023 at 7:52 PM
 */


data class UiQuoteModel(
     val id: Int,
   val quote: String,
   val category: String,
    var cat_image: ByteArray?
){

    fun toDbTypeModel()=UiQuoteModelDB(id,quote,category)
}

@Entity(tableName = "Quotes_Favourite")
data class UiQuoteModelDB(
     @ColumnInfo(name = "id") val id: Int,
     @PrimaryKey @ColumnInfo(name = "Quote") val quote: String,
    @ColumnInfo(name = "Category") val category: String
){
    fun toUiTypeModel()=UiQuoteModel(id,quote,category,null)
}