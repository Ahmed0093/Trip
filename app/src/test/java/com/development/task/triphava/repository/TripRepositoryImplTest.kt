package com.development.task.triphava.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.development.task.triphava.LiveDataTestUtil
import com.development.task.triphava.dataSource.TripRemoteDataSource
import com.development.task.triphava.getTripsCompletedMock
import com.development.task.triphava.getTripsCanceledMock
import com.development.task.triphava.model.Results
import com.development.task.triphava.model.Status
import com.development.task.triphava.ui.FilterModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.warbabank.warbaonline.loyalty.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TripRepositoryImplTest{
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var remoteDataSource: TripRemoteDataSource
    var filterModel = FilterModel(
        isIncludeCanceledTrips = true,
        distanceCheckedFrom = 0.0,
        distanceCheckedTo = 10000.0,
        timeCheckedFrom = 0.0,
        timeCheckedTo = 10000.0
    )
    var filterModelNotIncludeCancel = FilterModel(
        isIncludeCanceledTrips = false,
        distanceCheckedFrom = 0.0,
        distanceCheckedTo = 10000.0,
        timeCheckedFrom = 0.0,
        timeCheckedTo = 10000.0
    )


    @Test
   fun getTripsDataTest() =  mainCoroutineRule.runBlockingTest {
    remoteDataSource = mock {
            onBlocking { getTripsData() } doReturn Results(listOf(getTripsCompletedMock()))
        }
        val tripRepository = TripRepositoryImpl(remoteDataSource)
        val result = tripRepository.getTripsData(filterModel)
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)
        assert(LiveDataTestUtil.getValue(result).data?.trips?.get(0)?.status == COMPLETED_STATUS )

    }


    @Test
    fun getTripsDataTestWithAllCanceled() =  mainCoroutineRule.runBlockingTest {
        remoteDataSource = mock {
            onBlocking { getTripsData() } doReturn Results(listOf(getTripsCanceledMock()))
        }
        val tripRepository = TripRepositoryImpl(remoteDataSource)
        val result = tripRepository.getTripsData(filterModel)
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)
        assert(LiveDataTestUtil.getValue(result).data?.trips?.get(0)?.status == CANCELED_STATUS )

    }

    @Test
    fun getTripsData_Contains_only_Canceled_but_Filter_is_not_included_cancel() =  mainCoroutineRule.runBlockingTest {
        remoteDataSource = mock {
            onBlocking { getTripsData() } doReturn Results(listOf(getTripsCanceledMock()))
        }
        val tripRepository = TripRepositoryImpl(remoteDataSource)
        val result = tripRepository.getTripsData(filterModelNotIncludeCancel)
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)
        LiveDataTestUtil.getValue(result).data?.trips?.size?.equals(0)?.let { assert(it) }

    }
}