package com.example.templatesampleapp.helper

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MainApplication : Application() {


    override fun onCreate() {
        super.onCreate()


//        val ini = applicationContext.assets.open("quotes_app.db")
//
//        val output = File(applicationContext.filesDir,"dbfile.db")
//        output.createNewFile()
//        FileUtils.copyToFile(ini, output)


//        val dbhh: SQLiteDatabase =
//            SQLiteDatabase.openOrCreateDatabase(output, null)
//        dbhh.isOpen


    }
}