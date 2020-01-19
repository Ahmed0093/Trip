package com.development.task.triphava.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.development.task.triphava.LiveDataTestUtil
import com.development.task.triphava.getTripsCompletedMock
import com.development.task.triphava.model.MyResult
import com.development.task.triphava.model.Results
import com.development.task.triphava.model.Status
import com.development.task.triphava.repository.TripRepository
import com.development.task.triphava.ui.FilterModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.warbabank.warbaonline.loyalty.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TripResultViewModelTest {


    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var tripRepository: TripRepository


    @Test
    fun loadingTest() = mainCoroutineRule.runBlockingTest {
        tripRepository = mock {
            onBlocking { getTripsData(tripsFilterModel = FilterModel()) } doReturn object :
                LiveData<MyResult<Results>>() {
                init {
                    value = MyResult.loading()
                }
            }
        }

        val tripResultViewModel = TripResultViewModel(tripRepository)
        val result = tripResultViewModel.tripsList
        tripResultViewModel.getTripsList(FilterModel())

        result.observeForever {}

        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
    }


@Test
fun SuccessTest() = mainCoroutineRule.runBlockingTest {
    tripRepository = mock {
        onBlocking { getTripsData(tripsFilterModel = com.development.task.triphava.ui.FilterModel()) } doReturn object :
            LiveData<MyResult<Results>>() {
            init {
                value = MyResult.success(Results(listOf(getTripsCompletedMock())))
            }
        }
    }

    val tripResultViewModel = TripResultViewModel(tripRepository)
    val result = tripResultViewModel.tripsList
    tripResultViewModel.getTripsList(FilterModel())

    result.observeForever {}

    assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)
    assert(Results(listOf(getTripsCompletedMock())) == LiveDataTestUtil.getValue(result).data)

}

}