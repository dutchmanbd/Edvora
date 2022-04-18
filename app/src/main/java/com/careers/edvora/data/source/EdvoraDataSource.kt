package com.careers.edvora.data.source

import com.careers.edvora.data.remote.dto.RideDto
import com.careers.edvora.data.remote.dto.UserDto
import com.careers.utilities.responses.ApiResponse

interface EdvoraDataSource {

    suspend fun getUser(): ApiResponse<UserDto>

    suspend fun getRides(): ApiResponse<List<RideDto>>
}