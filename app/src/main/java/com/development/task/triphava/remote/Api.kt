package com.development.task.triphava.remote

import com.development.task.triphava.model.Results
import com.development.task.triphava.dataSource.TripRemoteDataSourceImpl
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET


interface Api {
    @GET("trips/recent.json")
    fun getTrips(): Deferred<Response<Results>>
}