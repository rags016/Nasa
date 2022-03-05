package com.learning.nasaapp.viewModels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learning.nasaapp.viewModels.APODViewModel

class APODViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return APODViewModel(context) as T
    }
}