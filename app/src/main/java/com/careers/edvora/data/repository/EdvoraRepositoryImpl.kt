package com.careers.edvora.data.repository

import androidx.lifecycle.liveData
import com.careers.edvora.data.mapper.toRide
import com.careers.edvora.data.mapper.toUser
import com.careers.edvora.data.source.EdvoraDataSource
import com.careers.edvora.domain.repository.EdvoraRepository
import com.careers.utilities.Resource
import com.careers.utilities.responses.ApiEmptyResponse
import com.careers.utilities.responses.ApiErrorResponse
import com.careers.utilities.responses.ApiSuccessResponse

class EdvoraRepositoryImpl(
    private val dataSource: EdvoraDataSource
) : EdvoraRepository {

    override suspend fun getUser() = when (val response = dataSource.getUser()) {
        is ApiEmptyResponse -> Resource.Failure(response.errorMessage, response.code)
        is ApiErrorResponse -> Resource.Failure(response.errorMessage, response.code)
        is ApiSuccessResponse -> Resource.Success(response.body.toUser())
    }


    override fun getRides(userStationCode: Int) = liveData {
        emit(Resource.Loading())
        emit(
            when (val response = dataSource.getRides()) {
                is ApiEmptyResponse -> Resource.Failure(response.errorMessage, response.code)
                is ApiErrorResponse -> Resource.Failure(response.errorMessage, response.code)
                is ApiSuccessResponse -> {
                    val rides = response.body.map {
                        it.toRide(userStationCode)
                    }
                    Resource.Success(rides)
                }
            }
        )
    }
}