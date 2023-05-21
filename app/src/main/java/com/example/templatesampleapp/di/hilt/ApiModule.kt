package com.example.templatesampleapp.di.hilt


import android.content.Context
import android.provider.SyncStateContract
import androidx.room.Room
import com.example.templatesampleapp.helper.Constant.BASE_URL
import com.example.templatesampleapp.helper.hasNetwork
import com.example.templatesampleapp.repo.DatabaseHelper
import com.example.templatesampleapp.repo.QuotesAppDb
import com.example.templatesampleapp.repo.QuotesRepository
import com.example.templatesampleapp.repo.service.QuotesApiNetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @HttpLoggerInterceptorBasic
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }


    @HttpLoggerInterceptorBody
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor1(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }


    @HttpLoggerInterceptorHeader
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor2(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS }

    @Singleton
    @Provides
    fun getExistDb(@ApplicationContext context: Context): DatabaseHelper {
        val db = DatabaseHelper(context)
        db.copyDataBase()
        return db
    }


    @Singleton
    @Provides
    fun providesOkHttpClient(
        @ApplicationContext context: Context,
        @HttpLoggerInterceptorBody httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val httpCacheDirectory = File(context.cacheDir, "QuotesAppCache")
        //10 MB
        val cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->

                // Get the request from the chain.
                var request = chain.request()

                request = if (hasNetwork(context)!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                chain.proceed(request)
            }
            .build()
    }


    @Singleton
    @Provides
    fun provideQuotesApiData(
        @ApplicationContext context: Context,
        quotesApiNetworkService: QuotesApiNetworkService
    ) = QuotesRepository(context, quotesApiNetworkService)

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): QuotesApiNetworkService =
        retrofit.create(QuotesApiNetworkService::class.java)

    @Singleton
    @Provides
    fun getRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getTemperatureDataBase(@ApplicationContext context:Context): QuotesAppDb {

        return Room.databaseBuilder(
            context,
            QuotesAppDb::class.java, "QuotesAppDb"
        ).allowMainThreadQueries().build()
    }
}