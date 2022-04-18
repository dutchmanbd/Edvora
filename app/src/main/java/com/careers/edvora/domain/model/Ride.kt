package com.careers.edvora.domain.model

data class Ride(
    val city: String,
    val date: String,
    val destinationStationCode: Int,
    val id: Int,
    val mapUrl: String,
    val originStationCode: Int,
    val state: String,
    val stationPath: List<Int>,
    val distance: Int = -1
)