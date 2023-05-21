package com.example.templatesampleapp.model.network


import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize


@Entity(tableName = "QuotesModel", primaryKeys = ["id","type"])
@Parcelize
data class Data(
    val id: String,
    val lang: String,
    val quote: String,
    val type: String
): Parcelable