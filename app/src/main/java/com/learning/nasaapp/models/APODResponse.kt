package com.learning.nasaapp.models

import com.google.gson.annotations.SerializedName

data class APODResponse(
    @SerializedName("date")
    val date: String?,
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("hdurl")
    val hdurl: String?,
    @SerializedName("service_version")
    val serviceVersion: String?,
    @SerializedName("explanation")
    val explanation: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?
)