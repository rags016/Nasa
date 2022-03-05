package com.learning.nasaapp.repository.helper

import android.content.Context
import android.graphics.Bitmap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.learning.nasaapp.models.APODResponse

class RepositoryHelper {


    fun parseIntoApodData(response: String): APODResponse? {
        var apodResponseData: APODResponse? = null
        val gson = Gson()
        val apod = object : TypeToken<APODResponse>() {}.type
        apodResponseData = gson.fromJson(response, apod)
        return apodResponseData
    }

    fun saveImage(context: Context, fileName: String, bmp: Bitmap) {
        ImageSaver(context)
            .setFileName(fileName)
            .setDirectoryName(DIR_NAME)
            .save(bmp)
    }

    fun getImage(context: Context, fileName: String): Bitmap? {
        return ImageSaver(context)
            .setFileName(fileName)
            .setDirectoryName(DIR_NAME)
            .load()
    }

    companion object {
        const val DIR_NAME = "image_media"
    }

}