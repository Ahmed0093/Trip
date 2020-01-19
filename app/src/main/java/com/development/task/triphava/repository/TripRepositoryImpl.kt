package com.development.task.triphava.repository

import androidx.lifecycle.liveData
import com.development.task.triphava.dataSource.TripRemoteDataSource
import com.development.task.triphava.dataSource.tripRemoteDataSourceImpl
import com.development.task.triphava.model.MyResult
import com.development.task.triphava.model.Results
import com.development.task.triphava.model.Trips
import com.development.task.triphava.ui.FilterModel

val COMPLETED_STATUS = "COMPLETED"
val CANCELED_STATUS = "CANCELED"
val tripRepositoryImpl by lazy { TripRepositoryImpl() }

class TripRepositoryImpl constructor(
    private val remoteDataSource: TripRemoteDataSource = tripRemoteDataSourceImpl
) : TripRepository {
    lateinit var resultAfterFilteration2: List<Trips>

    override suspend fun getTripsData(tripsFilterModel: FilterModel?) =
        liveData<MyResult<Results>> {
            emit(MyResult.loading())
            try {
                val result = remoteDataSource.getTripsData()
                if (!tripsFilterModel?.isIncludeCanceledTrips!!) {
                    resultAfterFilteration2 = result?.trips?.filter {
                        (it.distance in tripsFilterModel?.distanceCheckedFrom!!..tripsFilterModel?.distanceCheckedTo!!)
                                && (it.status.equals(COMPLETED_STATUS)
                                && (it.duration in tripsFilterModel?.timeCheckedFrom?.toInt()!!..tripsFilterModel.timeCheckedTo?.toInt()!!))
                    }!!
                } else {
                    resultAfterFilteration2 = result?.trips?.filter {
                        (it.distance in tripsFilterModel?.distanceCheckedFrom!!..tripsFilterModel.distanceCheckedTo!!)
                                && (it.duration in tripsFilterModel?.timeCheckedFrom?.toInt()!!..tripsFilterModel.timeCheckedTo?.toInt()!!)
                    }!!
                }

                emit(
                    MyResult.success(resultAfterFilteration2?.let { Results(it) })
                )
            } catch (exception: Exception) {
                emit(MyResult.error(exception.message ?: ""))
            }
        }

}
