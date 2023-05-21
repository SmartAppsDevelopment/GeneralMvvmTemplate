package com.example.templatesampleapp.repo.service

import com.example.templatesampleapp.model.network.QuotesDataModel
import com.example.templatesampleapp.model.network.quotes.RemoteQuote
import com.example.templatesampleapp.model.network.quotetype.QuotesTypeModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @author Umer Bilal
 * Created 7/23/2022 at 11:48 AM
 *
 */

interface QuotesApiNetworkService {

    @GET("getdata.php?")
    suspend fun getDataFromQuotesDb(
        @Query("tablename") tablename: String,
        @Query("page") page: String
    ): QuotesDataModel

    @GET("getdata.php?")
    suspend fun getTypesFromQuotesDb(@Query("tablename") tablename: String): QuotesTypeModel

    @GET("getdata.php?")
    suspend fun getQuotesByType(/*@Query("tablename") tablename:String,*/@Query("type") type: String,
                                                                         @Query("page") page: String
    ): RemoteQuote

//    @GET("transformers")
//    suspend fun getAllAvailableLists(@Header("Authorization") token:String): ResponseBody
//
//    @DELETE("transformers/{userid}")
//    suspend fun deleteDataByID(@Header("Authorization") token:String,@Path("userid") userid: String): Response<Void>
//
//    @POST("transformers")
//    suspend fun postNewData(@Header("Authorization") token:String,@Body requestBody: TranFormerDataModel): ResponseBody
//
//    @PUT("transformers")
//    suspend fun updateData(@Header("Authorization") token:String,@Body requestBody: TranFormerDataModel): ResponseBody

}