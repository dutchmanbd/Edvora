package com.careers.utilities.middleware

import android.content.Context
import com.careers.extensions.isNetworkAvailable
import com.careers.utilities.responses.NETWORK_ERROR
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!appContext.isNetworkAvailable()) {
            throw IOException(NETWORK_ERROR)
        }
        return chain.proceed(chain.request())
    }
}