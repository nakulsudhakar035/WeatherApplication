package com.nakuls.weatherapplication.data.remote

import com.nakuls.weatherapplication.data.repository.WeatherRepositoryImpl
import com.nakuls.weatherapplication.domain.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {

    private const val baseurl = "https://api.weatherapi.com"

    @Provides
    @Singleton
    private fun getInstance(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)

    }

    @Provides // means this method provides a dependency
    @Singleton
    fun provideWeatherRepository(weatherAPI: WeatherAPI): WeatherRepository {
        return WeatherRepositoryImpl(weatherAPI)
    }
}