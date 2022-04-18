package com.careers.edvora.data.mapper

import com.careers.edvora.data.remote.dto.RideDto
import com.careers.edvora.domain.model.Ride
import kotlin.math.abs

fun RideDto.toRide(userStationCode: Int): Ride {
    val distance = stationPath.minOf {
        abs(it - userStationCode)
    }
    return Ride(
        city = city,
        date = date,
        destinationStationCode = destinationStationCode,
        id = id,
        mapUrl = mapUrl ?: "",
        originStationCode = originStationCode,
        state = state,
        stationPath = stationPath,
        distance = distance
    )
}

fun Ride.toRideDto() = RideDto(
    city = city,
    date = date,
    destinationStationCode = destinationStationCode,
    id = id,
    mapUrl = mapUrl,
    originStationCode = originStationCode,
    state = state,
    stationPath = stationPath
)