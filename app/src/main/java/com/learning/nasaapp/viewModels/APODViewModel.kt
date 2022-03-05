package com.learning.nasaapp.viewModels

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.learning.nasaapp.models.APODResponse
import com.learning.nasaapp.repository.APODRepository

class APODViewModel(val context: Context) : ViewModel() {
    private val repository = APODRepository(context)
    fun getApodData(): LiveData<APODResponse?> {
        return repository.apodResponseLiveData
    }

    fun getProgress(): LiveData<Boolean> {
        return repository.progress
    }

    fun getImageOftheDay(): LiveData<Bitmap> {
        return repository.imageOfTheDay
    }

    suspend fun getApodFromRepo() {
        repository.getApodData()
    }
}