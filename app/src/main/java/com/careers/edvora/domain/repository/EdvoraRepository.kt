package com.careers.edvora.domain.repository

import androidx.lifecycle.LiveData
import com.careers.edvora.domain.model.Ride
import com.careers.edvora.domain.model.User
import com.careers.utilities.Resource

interface EdvoraRepository {

    suspend fun getUser(): Resource<User>
    fun getRides(userStationCode: Int): LiveData<Resource<List<Ride>>>
}