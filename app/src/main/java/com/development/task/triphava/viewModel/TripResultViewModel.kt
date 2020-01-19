package com.development.task.triphava.viewModel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.development.task.triphava.model.MyResult
import com.development.task.triphava.model.Results
import com.development.task.triphava.repository.TripRepository
import com.development.task.triphava.repository.tripRepositoryImpl
import com.development.task.triphava.ui.FilterModel
import kotlinx.coroutines.launch

class  TripResultViewModel constructor(
    private val tripRepository: TripRepository = tripRepositoryImpl
) : ViewModel() {



    val tripsList = MediatorLiveData<MyResult<Results>>()


    fun getTripsList(tripsFilterModel: FilterModel?) {

        viewModelScope.launch {
            tripsList.addSource(
                tripRepository.getTripsData(tripsFilterModel)
            )
            {
                tripsList.value = it

            }

        }
    }




}
