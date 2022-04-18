package com.careers.utilities

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading(val nothing: Nothing? = null) : Resource<Nothing>()
    data class Failure(val message: String, val code: Int) : Resource<Nothing>()
}