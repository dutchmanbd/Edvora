package com.careers.edvora.data.remote

import com.careers.edvora.data.remote.dto.RideDto
import com.careers.edvora.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET

interface EdvoraApiService {

    @GET("user")
    suspend fun getUser(): Response<UserDto>

    @GET("rides")
    suspend fun getRides(): Response<List<RideDto>>

}