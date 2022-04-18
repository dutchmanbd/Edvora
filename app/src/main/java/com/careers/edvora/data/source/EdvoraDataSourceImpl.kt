package com.careers.edvora.data.source

import com.careers.edvora.data.remote.EdvoraApiService

import com.careers.utilities.middleware.SafeApiRequest


class EdvoraDataSourceImpl(
    private val apiService: EdvoraApiService
) : SafeApiRequest(), EdvoraDataSource {

    override suspend fun getUser() = apiRequest {
        apiService.getUser()
    }

    override suspend fun getRides() = apiRequest {
        apiService.getRides()
    }


}