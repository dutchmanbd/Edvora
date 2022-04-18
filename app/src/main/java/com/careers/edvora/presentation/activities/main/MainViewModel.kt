package com.careers.edvora.presentation.activities.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.careers.edvora.domain.model.User
import com.careers.edvora.domain.repository.EdvoraRepository
import com.careers.edvora.presentation.fragments.base.BaseViewModel
import com.careers.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: EdvoraRepository
) : BaseViewModel() {

    init {
        fetchUser()
    }
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private fun fetchUser() {
        viewModelScope.launch {
            when (val resource = repository.getUser()) {
                is Resource.Failure -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    resource.data.let {
                        _user.postValue(it)
                    }
                }
            }
        }
    }

    fun getRides(userStationCode: Int) = repository.getRides(userStationCode)
}