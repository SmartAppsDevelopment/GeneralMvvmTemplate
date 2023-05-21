package com.example.templatesampleapp.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.templatesampleapp.model.ui.UiQuoteModel
import com.example.templatesampleapp.model.ui.UiQuoteModelDB

/**
 * @author Umer Bilal
 * Created 5/1/2023 at 6:53 PM
 */

@Database(entities = [UiQuoteModelDB::class], version = 1)
abstract class QuotesAppDb : RoomDatabase() {
    abstract fun userDao(): QuotesDao
}