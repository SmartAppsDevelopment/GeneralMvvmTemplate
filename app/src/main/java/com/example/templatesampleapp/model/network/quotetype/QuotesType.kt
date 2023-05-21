package com.example.templatesampleapp.model.network.quotetype


import android.os.Parcelable
import androidx.room.Entity
import com.accuspot.quotes.model.CategoryModel
import kotlinx.parcelize.Parcelize


@Entity(tableName = "QuotesType", primaryKeys = ["id"])
@Parcelize
data class QuotesType(
    val id: String,
    val type: String
): Parcelable{

    fun toCategoryModel():CategoryModel{
        return CategoryModel(id.toInt(),type,null).apply { newBiosType=id }
    }
}

