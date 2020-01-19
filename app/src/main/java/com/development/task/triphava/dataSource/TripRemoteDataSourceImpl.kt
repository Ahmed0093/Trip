package com.development.task.triphava.dataSource

import CoroutinesIO
import com.development.task.triphava.network.NetworkModule
import com.development.task.triphava.network.networkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

val tripRemoteDataSourceImpl by lazy { TripRemoteDataSourceImpl() }

class TripRemoteDataSourceImpl constructor(
    private val service: NetworkModule = networkModule,
    private val context: CoroutineContext = Dispatchers.IO
) : TripRemoteDataSource {
    override suspend fun getTripsData() = withContext(context) {
        service.provideTripApi().await().body()
    }
}


