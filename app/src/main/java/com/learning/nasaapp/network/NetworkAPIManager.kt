package com.learning.nasaapp.network

import android.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object NetworkAPIManager {

    suspend fun getData(genericUrl: String): String? {
        return suspendCancellableCoroutine { cancellableContinuation ->

            try {
                val reader: BufferedReader
                val url = URL(genericUrl)

                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "GET"
                    reader = BufferedReader(InputStreamReader(inputStream) as Reader)

                    var response = StringBuffer()
                    var inputLine = reader.readLine()

                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = reader.readLine()
                    }
                    reader.close()

                    if (cancellableContinuation.isActive) {
                        cancellableContinuation.resume(response.toString())
                    }
                }
            } catch (e: Exception) {
                Log.d("TAG", "getData: ${e.printStackTrace()}")
                e.printStackTrace()
                if (cancellableContinuation.isActive) {
                    cancellableContinuation.resumeWithException(e)
                }


            }
        }
    }
}