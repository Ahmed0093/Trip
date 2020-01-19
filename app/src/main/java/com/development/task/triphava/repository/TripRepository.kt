package com.development.task.triphava.repository

import androidx.lifecycle.LiveData
import com.development.task.triphava.model.MyResult
import com.development.task.triphava.model.Results
import com.development.task.triphava.ui.FilterModel


interface TripRepository {
    suspend fun getTripsData(tripsFilterModel: FilterModel?): LiveData<MyResult<Results>>
}
