package com.careers.edvora.data.remote.dto


import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("station_code")
    val stationCode: Int,
    @SerializedName("url")
    val url: String?
)