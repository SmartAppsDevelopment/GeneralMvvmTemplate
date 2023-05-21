package com.example.templatesampleapp.repo

import androidx.room.*
import com.example.templatesampleapp.model.ui.UiQuoteModel
import com.example.templatesampleapp.model.ui.UiQuoteModelDB

/**
 * @author Umer Bilal
 * Created 5/1/2023 at 6:50 PM
 */

@Dao
interface QuotesDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: UiQuoteModelDB): Long

    @Delete
    fun deleteItem(users: UiQuoteModelDB): Int

    @Query("SELECT * FROM Quotes_Favourite where Quote like :str")
    fun getFromDb(str:String):UiQuoteModelDB?

    @Query("SELECT * FROM Quotes_Favourite")
    fun getAllFromDb():List<UiQuoteModelDB>

}