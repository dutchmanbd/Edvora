package com.careers.utilities.responses

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

const val UNKNOWN_ERROR = "Something went wrong, Please try again later"
const val NETWORK_ERROR = "Please check your internet connection"
const val JSON_PARSE_ERROR = "Data loading failed, please try again later"
const val UNKNOWN_CODE = 4345

sealed class ApiResponse<T> {

    companion object {
        fun <T> create(error: String?, code: Int = 444): ApiErrorResponse<T> {
            return ApiErrorResponse(error ?: UNKNOWN_ERROR, code)
        }
        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse(UNKNOWN_ERROR, response.code())
                } else {
                    ApiSuccessResponse(
                        body = body
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val code = response.code()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    val sb = StringBuilder()
                    try {
                        val errorObject = JSONObject(msg)
                        when {
                            errorObject.has("detail") -> {
                                sb.append(errorObject.getString("detail"))
                            }
                            errorObject.has("message") -> {
                                sb.append(errorObject.getString("message"))
                            }
                            else -> {
                                errorObject.keys().forEach {
                                    val errorArrays = errorObject.getJSONArray(it)
                                    for (index in 0 until errorArrays.length()) {
                                        sb.append(errorArrays.getString(index))
                                        if (index < errorArrays.length() - 1) {
                                            sb.append(",")
                                        }
                                    }
                                }
                            }
                        }
                    } catch (e: JSONException) {
                        sb.append(UNKNOWN_ERROR)
                    }
                    sb.toString()
                }
                ApiErrorResponse(errorMsg, code)
            }
        }
    }

}


class ApiEmptyResponse<T>(val errorMessage: String, val code: Int) : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String, val code: Int) : ApiResponse<T>()