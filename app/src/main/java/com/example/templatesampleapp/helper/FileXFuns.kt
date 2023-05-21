package com.example.templatesampleapp.helper

import android.content.Context
import org.apache.commons.io.Charsets
import org.apache.commons.io.FileUtils
import java.io.File


/**
 * @author Umer Bilal
 * Created 5/7/2023 at 11:25 PM
 */

val BASE_FILE_NAME = "DbQuotes_"

fun saveToCacheFile(context: Context, fileName: String, dataJson: String) {
//    val destFile = File.createTempFile("hello", "txt", context.cacheDir)
    val destFile = File(context.cacheDir.path,fileName+".txt")
    if(destFile.exists().not())
        destFile.createNewFile()

    FileUtils.write(destFile, dataJson, Charsets.UTF_16)
}
fun getFromCacheFile(context: Context, fileName: String) :String?{
//    val destFile = File.createTempFile("hello", "txt", context.cacheDir)
    val destFile = File(context.cacheDir.path,fileName+".txt")
    return if(destFile.exists()){
        FileUtils.readFileToString(destFile,Charsets.UTF_16)
    }else null

}