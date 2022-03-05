package com.learning.nasaapp.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.learning.nasaapp.commonutils.SharedPreferenceUtils
import com.learning.nasaapp.models.APODResponse
import com.learning.nasaapp.network.NetworkAPIManager
import com.learning.nasaapp.network.NetworkUtils
import com.learning.nasaapp.repository.helper.RepositoryHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL

class APODRepository(val context: Context) {
    private lateinit var repositoryHelper: RepositoryHelper

    private var _imageOfTheDay = MutableLiveData<Bitmap>()
    private var apodMutableLiveData = MutableLiveData<APODResponse>()
    private var progressMutableLiveData = MutableLiveData<Boolean>()

    val apodResponseLiveData: LiveData<APODResponse?>
        get() = apodMutableLiveData

    val progress: LiveData<Boolean>
        get() = progressMutableLiveData

    val imageOfTheDay: LiveData<Bitmap>
        get() = _imageOfTheDay


    suspend fun getApodData() {
        progressMutableLiveData.postValue(true)
        repositoryHelper = RepositoryHelper()
        if (!NetworkUtils.isInternetAvailable(context)) {
            val date = SharedPreferenceUtils.getSharedPreferences(context).getString("date")
            val imageOfTheDay = date?.let { repositoryHelper.getImage(context, it) }
            val title = SharedPreferenceUtils.getSharedPreferences(context).getString("title")
            val explanation =
                SharedPreferenceUtils.getSharedPreferences(context).getString("explanation")
            val apodResponse = APODResponse(
                date,
                "",
                "",
                "",
                explanation,
                title,
                ""
            )
            fillDataModelWithDb(apodResponse = apodResponse, progress = false, bmp = imageOfTheDay)
            return
        }
        var response: String?
        var apodResponse: APODResponse?
        CoroutineScope(Dispatchers.IO).async {
            response = NetworkAPIManager.getData(APOD_URL)
            apodResponse = response?.let {
                repositoryHelper.parseIntoApodData(it)
            }
            var bmp: Bitmap? = null
            try {
                val connection = URL(apodResponse?.url).openConnection()
                connection.connect()
                val inputStream = connection.getInputStream()
                bmp = BitmapFactory.decodeStream(inputStream)
                inputStream.close()

            } catch (e: Exception) {
                e.printStackTrace()
            }
            apodResponse?.date?.let {
                if (bmp != null) {
                    repositoryHelper.saveImage(context, it, bmp)
                }
            }
            fillDataModelWithNetwork(apodResponse = apodResponse, bmp = bmp, progress = false)
        }
    }

    private fun fillDataModelWithNetwork(
        apodResponse: APODResponse? = null,
        bmp: Bitmap? = null,
        progress: Boolean
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            apodResponse?.let {
                it.title?.let { title ->
                    SharedPreferenceUtils.getSharedPreferences(context).putString(
                        "title",
                        title
                    )
                }
                it.explanation?.let { desc ->
                    SharedPreferenceUtils.getSharedPreferences(context).putString(
                        "explanation",
                        desc
                    )
                }
                it.date?.let { date ->
                    SharedPreferenceUtils.getSharedPreferences(context).putString(
                        "date",
                        date
                    )
                }
            }
            apodMutableLiveData.postValue(apodResponse)
            progressMutableLiveData.postValue(progress)
            _imageOfTheDay.postValue(bmp)
        }
    }

    private fun fillDataModelWithDb(
        apodResponse: APODResponse? = null,
        bmp: Bitmap? = null,
        progress: Boolean
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            apodMutableLiveData.postValue(apodResponse)
            progressMutableLiveData.postValue(progress)
            _imageOfTheDay.postValue(bmp)
        }
    }

    companion object {
        const val APOD_URL =
            "https://api.nasa.gov/planetary/apod?api_key=QRxh0egeUgqczaEDqHs6VBxhIUQUNFbBI081B8ES"
        const val TAG = "APODRepository"
    }
}