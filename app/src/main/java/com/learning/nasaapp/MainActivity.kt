package com.learning.nasaapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.learning.nasaapp.databinding.ActivityMainBinding
import com.learning.nasaapp.network.NetworkUtils
import com.learning.nasaapp.viewModels.APODViewModel
import com.learning.nasaapp.viewModels.factory.APODViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: APODViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            APODViewModelFactory(this.applicationContext)
        ).get(APODViewModel::class.java)

        binding.lifecycleOwner = this

        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            binding.noConnectionError.visibility = View.GONE
        } else {
            binding.noConnectionError.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE

            CoroutineScope(Dispatchers.IO).launch {
                loadLastSavedAPOD()
            }
            return
        }
        loadImageOfTheDay()
        updateUI()
    }

    private fun updateUI() {
        viewModel.getApodData().observe(this, Observer { apod ->
            binding.tvTitle.text = apod?.title
            binding.tvDesc.text = apod?.explanation
            binding.tvDate.text = apod?.date
        })

        viewModel.getImageOftheDay().observe(this, Observer {
            binding.ivApod.setImageBitmap(it)
        })

        viewModel.getProgress().observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

        })
    }

    private suspend fun loadLastSavedAPOD() {
        delay(3000)
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getApodFromRepo()
            binding.noConnectionError.visibility = View.GONE
            updateUI()
        }
    }

    private fun loadImageOfTheDay() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getApodFromRepo()
        }
    }
}