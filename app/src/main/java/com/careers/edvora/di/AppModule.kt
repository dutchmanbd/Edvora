package com.careers.edvora.di

import android.content.Context
import com.careers.edvora.BuildConfig
import com.careers.edvora.data.remote.EdvoraApiService
import com.careers.edvora.data.repository.EdvoraRepositoryImpl
import com.careers.edvora.data.source.EdvoraDataSource
import com.careers.edvora.data.source.EdvoraDataSourceImpl
import com.careers.edvora.domain.repository.EdvoraRepository
import com.careers.utilities.middleware.ConnectivityInterceptor
import com.careers.utilities.middleware.ConnectivityInterceptorImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(
        @ApplicationContext context: Context
    ): ConnectivityInterceptor = ConnectivityInterceptorImpl(context)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        connectivityInterceptor: ConnectivityInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(connectivityInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideEdvoraApiService(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): EdvoraApiService = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(EdvoraApiService::class.java)

    @Provides
    @Singleton
    fun provideEdvoraDataSource(
        apiService: EdvoraApiService
    ): EdvoraDataSource = EdvoraDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideEdvoraRepository(
        dataSource: EdvoraDataSource
    ): EdvoraRepository = EdvoraRepositoryImpl(dataSource)



}