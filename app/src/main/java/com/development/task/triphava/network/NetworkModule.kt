package com.development.task.triphava.network

import com.development.task.triphava.model.Results
import com.development.task.triphava.model.Trips
import com.development.task.triphava.remote.Api
import com.facebook.stetho.okhttp3.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule by lazy { NetworkModule() }

class NetworkModule {


    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Const.API_URL)
            .addConverterFactory(providesGsonConverterFactory())
            .addCallAdapterFactory(providesRxJavaCallAdapterFactory())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .client(providesOkHttpClient())
            .build()
    }
    fun provideApi(): Api {
        return providesRetrofit().create(Api::class.java);
    }
    fun provideTripApi(): Deferred<Response<Results>> {
        return providesRetrofit().create(Api::class.java).getTrips();
    }


    private fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging)

            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            client.addNetworkInterceptor(StethoInterceptor())
        }

        return client.build()
    }

    private fun providesGson(): Gson {
        return Gson()
    }


    private fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    private fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }
}