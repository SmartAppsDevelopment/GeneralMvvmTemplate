package com.example.templatesampleapp.repo

import android.accounts.NetworkErrorException
import android.content.Context
import com.example.templatesampleapp.helper.*
import com.example.templatesampleapp.model.network.Data
import com.example.templatesampleapp.model.network.quotes.RemoteQuote
import com.example.templatesampleapp.model.network.quotes.RemoteQuoteData
import com.example.templatesampleapp.model.network.quotetype.QuotesType
import com.example.templatesampleapp.model.network.quotetype.QuotesTypeModel
import com.example.templatesampleapp.repo.service.Either
import com.example.templatesampleapp.repo.service.QuotesApiNetworkService
import com.google.gson.Gson
import java.io.IOException
import javax.inject.Inject

/**
 * @author Umer Bilal
 * Created 11/26/2022 at 8:29 PM
 */
class QuotesRepository @Inject constructor(
    val context: Context,
    val quotesApiNetworkService: QuotesApiNetworkService
) {

    suspend fun getListOfQuotes(page: String): Either<List<Data>> {
        return runCatching {
            val quotesdata =
                quotesApiNetworkService.getDataFromQuotesDb(Constant.TABLE_NAME_TYPE, page)

//            Log.d(TAG, "getAllOperators: $authResponse")

            if (quotesdata.info.status.equals("true")) {

                saveToCacheFile(
                    context,
                    BASE_FILE_NAME + "${Constant.TABLE_NAME_TYPE}_$page",
                    Gson().toJson(quotesdata.info.data)
                )
                Either.success(quotesdata.info.data)
            } else {
                Either.error(quotesdata.info.msg)
            }
        }.getOrElse {
            when (it) {
                is NetworkErrorException -> {
                    Either.error("502")
                }
                is IOException -> {
                    Either.error("500")
                }
                else -> {
                    Either.error("Something went wrong")
                }
            }
        }
    }

    suspend fun getListOfQuotesType(): Either<List<QuotesType>> {
        return runCatching {
            val FileNAME = getFileNameTypePage(Constant.TABLE_NAME_TYPE)
            val textFromFile = getFromCacheFile(context, FileNAME)
            if (textFromFile != null) {
                val quoteDataFromFile = Gson().fromJson(textFromFile, QuotesTypeModel::class.java)
                if (quoteDataFromFile.info.status.equals("true")) {
                    Either.success(quoteDataFromFile.info.data)
                } else {
                    val quotesdata =
                        quotesApiNetworkService.getTypesFromQuotesDb(Constant.TABLE_NAME_TYPE)
                    if (quotesdata.info.status.equals("true")) {

                        saveToCacheFile(
                            context,
                            FileNAME,
                            Gson().toJson(quotesdata)
                        )

                        Either.success(quotesdata.info.data)
                    } else {
                        Either.error(quotesdata.info.msg)
                    }
                }
            } else {
                val quotesdata =
                    quotesApiNetworkService.getTypesFromQuotesDb(Constant.TABLE_NAME_TYPE)

                if (quotesdata.info.status.equals("true")) {
                    saveToCacheFile(
                        context,
                        FileNAME,
                        Gson().toJson(quotesdata)
                    )
                    Either.success(quotesdata.info.data)
                } else {
                    Either.error(quotesdata.info.msg)
                }
            }
        }.getOrElse {
            when (it) {
                is NetworkErrorException -> {
                    Either.error("Network Error Code (502)")
                }
                is IOException -> {
                    Either.error("Network Error Code (500)")
                }
                else -> {
                    Either.error("No More Data Found")
                }
            }
        }
    }

    suspend fun getQuotesByType(type: String, page: String): Either<List<RemoteQuoteData>> {
        return runCatching {

            val textFromFile = getFromCacheFile(context, getFileNameTypePage(type, page))
            if (textFromFile != null) {
                val quoteDataFromFile = Gson().fromJson(textFromFile, RemoteQuote::class.java)
                if (quoteDataFromFile.info.status.equals("true")) {
                    Either.success(quoteDataFromFile.info.data)
                } else {
                    val quotesdata = quotesApiNetworkService.getQuotesByType(type, page)
                    if (quotesdata.info.status.equals("true")) {

                        saveToCacheFile(
                            context,
                            getFileNameTypePage(type, page),
                            Gson().toJson(quotesdata)
                        )

                        Either.success(quotesdata.info.data)
                    } else {
                        Either.error(quotesdata.info.msg)
                    }
                }
            } else {
                val quotesdata = quotesApiNetworkService.getQuotesByType(type, page)
                if (quotesdata.info.status.equals("true")) {

                    saveToCacheFile(
                        context,
                        getFileNameTypePage(type, page),
                        Gson().toJson(quotesdata)
                    )

                    Either.success(quotesdata.info.data)
                } else {
                    Either.error(quotesdata.info.msg)
                }
            }

        }.getOrElse {
            when (it) {
                is NetworkErrorException -> {
                    Either.error("Network Error Code (502)")
                }
                is IOException -> {
                    Either.error("Network Error Code (500)")
                }
                else -> {
                    Either.error("No More Data Found")
                }
            }
        }
    }

    fun getFileNameTypePage(type: String, page: String) =
        BASE_FILE_NAME + "${type}_$page"

    fun getFileNameTypePage(type: String) =
        BASE_FILE_NAME + "${type}"

}