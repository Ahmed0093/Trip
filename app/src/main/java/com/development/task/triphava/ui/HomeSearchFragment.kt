package com.development.task.triphava.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_home_search.*

val TRIPS_FILTERATION = "tripsFilteration"

class HomeSearchFragment : Fragment() {

    val tripsFilterationModel: FilterModel = FilterModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            com.development.task.triphava.R.layout.fragment_home_search,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            var bundle = Bundle()
            switch1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener() { compoundButton: CompoundButton, isChecked: Boolean ->
                tripsFilterationModel.isIncludeCanceledTrips = isChecked

            })
            search_btn.setOnClickListener {
                setFilterationModelFromUI()
                var selectedDistanceCheckButton = radio_distance.getCheckedRadioButtonId()
                setDistanceFilterData(selectedDistanceCheckButton)
                setDurationFilterationData()
                bundle.putParcelable(TRIPS_FILTERATION, tripsFilterationModel)
                view?.findNavController()
                    ?.navigate(com.development.task.triphava.R.id.fragment_search_result, bundle)

            }


        }

    }

    private fun setDurationFilterationData() {
        var selectedDurationCheckButton = radio_duration.getCheckedRadioButtonId()
        when (selectedDurationCheckButton) {
            com.development.task.triphava.R.id.checkbox_any_time -> {
                tripsFilterationModel.isAnyTimeChecked = true
                tripsFilterationModel.timeCheckedFrom = 0.0
                tripsFilterationModel.timeCheckedTo = 1000000.0
            }
            com.development.task.triphava.R.id.checkbox_under_5min -> {
                tripsFilterationModel.timeCheckedFrom = 0.0
                tripsFilterationModel.timeCheckedTo = 4.9
            }
            com.development.task.triphava.R.id.checkbox_from_5_to_10min -> {
                tripsFilterationModel.timeCheckedFrom = 5.0
                tripsFilterationModel.timeCheckedTo = 10.0
            }
            com.development.task.triphava.R.id.checkbox_from_10_to_20min -> {
                tripsFilterationModel.timeCheckedFrom = 10.0
                tripsFilterationModel.timeCheckedTo = 20.0
            }
            com.development.task.triphava.R.id.checkbox_more_than_20min -> {
                tripsFilterationModel.timeCheckedFrom = 20.1
                tripsFilterationModel.timeCheckedTo = 1000000.0
            }
            else -> {
                tripsFilterationModel.timeCheckedFrom = 0.0
                tripsFilterationModel.timeCheckedTo = 1000000.0
            }
        }
    }

    private fun setDistanceFilterData(selectedDistanceCheckButton: Int) {
        when (selectedDistanceCheckButton) {
            com.development.task.triphava.R.id.checkbox_any_distance -> {
                tripsFilterationModel.isAnyDistanceChecked = true
                tripsFilterationModel.distanceCheckedFrom = 0.0
                tripsFilterationModel.distanceCheckedTo = 1000000.0
            }
            com.development.task.triphava.R.id.checkbox_under_3km -> {
                tripsFilterationModel.distanceCheckedFrom = 0.0
                tripsFilterationModel.distanceCheckedTo = 2.9
            }
            com.development.task.triphava.R.id.checkbox_from_3_to_8Km -> {
                tripsFilterationModel.distanceCheckedFrom = 3.0
                tripsFilterationModel.distanceCheckedTo = 8.0
            }
            com.development.task.triphava.R.id.checkbox_from_8_to_15km -> {
                tripsFilterationModel.distanceCheckedFrom = 8.0
                tripsFilterationModel.distanceCheckedTo = 15.0
            }
            com.development.task.triphava.R.id.checkbox_more_than_15km -> {
                tripsFilterationModel.distanceCheckedFrom = 15.1
                tripsFilterationModel.distanceCheckedTo = 1000000.0
            }
            else -> {
                tripsFilterationModel.distanceCheckedFrom = 0.0
                tripsFilterationModel.distanceCheckedTo = 1000000.0
            }
        }
    }

    private fun setFilterationModelFromUI() {
        tripsFilterationModel.isAnyDistanceChecked = checkbox_any_distance.isChecked
        tripsFilterationModel.isAnyTimeChecked = checkbox_any_time.isChecked
        tripsFilterationModel.isDistanceUnder3kmChecked = checkbox_under_3km.isChecked
        tripsFilterationModel.isDistancefrom3kmto8kmChecked = checkbox_from_3_to_8Km.isChecked
        tripsFilterationModel.isDistancefrom8kmto15kmChecked = checkbox_from_8_to_15km.isChecked
        tripsFilterationModel.isDistanceMoreThan15kmChecked = checkbox_more_than_15km.isChecked
        tripsFilterationModel.isTimeUnder5MinChecked = checkbox_under_5min.isChecked
        tripsFilterationModel.isTimeFrom5to10MinChecked = checkbox_from_5_to_10min.isChecked
        tripsFilterationModel.isTimeFrom10to20MinChecked = checkbox_from_10_to_20min.isChecked
        tripsFilterationModel.isTimeMoreThan20MinChecked = checkbox_more_than_20min.isChecked
    }


}
