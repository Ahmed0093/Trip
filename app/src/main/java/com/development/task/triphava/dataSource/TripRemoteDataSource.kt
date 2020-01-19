package com.development.task.triphava.dataSource

import androidx.lifecycle.LiveData
import com.development.task.triphava.model.Results

interface TripRemoteDataSource {
    suspend fun getTripsData(): Results?

}
