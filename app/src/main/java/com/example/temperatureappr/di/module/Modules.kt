package com.example.temperatureappr.di.module

import com.example.temperatureappr.data.networking.TemperatureApi
import com.example.temperatureappr.data.networking.WeatherApi
import com.example.temperatureappr.data.repository.InsideTemperatureRepository
import com.example.temperatureappr.data.repository.InsideTemperatureRepositoryImpl
import com.example.temperatureappr.data.repository.OutsideTemperatureRepository
import com.example.temperatureappr.data.repository.OutsideTemperatureRepositoryImpl
import com.example.temperatureappr.inside.InsideViewModel
import com.example.temperatureappr.outside.OutsideViewModel
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val INSIDE_TEMPERATURE_API_BASE_URL = "https://temperatureserver.azurewebsites.net"
const val OUTSIDE_TEMPERATURE_API_BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val TIMEOUT = 90L

val appModules = module {
    single {
        provideRetrofitService<TemperatureApi>(
            okHttpClient = provideHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = INSIDE_TEMPERATURE_API_BASE_URL
        )
    }
    single {
        provideRetrofitService<WeatherApi>(
            okHttpClient = provideHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = OUTSIDE_TEMPERATURE_API_BASE_URL
        )
    }

    factory<InsideTemperatureRepository> { InsideTemperatureRepositoryImpl(temperatureApi = get()) }
    factory<OutsideTemperatureRepository> { OutsideTemperatureRepositoryImpl(weatherApi = get()) }

    viewModel { InsideViewModel(insideTemperatureRepository = get()) }
    viewModel { OutsideViewModel(outsideTemperatureRepository = get()) }
}

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }
}


fun provideHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(provideHttpLoggingInterceptor())
        .build()
}

inline fun <reified T> provideRetrofitService(
    okHttpClient: OkHttpClient,
    factory: CallAdapter.Factory, baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(factory)
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}