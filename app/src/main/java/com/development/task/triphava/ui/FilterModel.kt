package com.development.task.triphava.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterModel(
    var isAnyDistanceChecked: Boolean = false,
    var isAnyTimeChecked: Boolean = false,
    var isDistanceUnder3kmChecked: Boolean = false,
    var isDistancefrom3kmto8kmChecked: Boolean = false,
    var isDistancefrom8kmto15kmChecked: Boolean = false,
    var isDistanceMoreThan15kmChecked: Boolean = false,
    var isTimeMoreThan20MinChecked: Boolean = false,
    var isTimeFrom10to20MinChecked: Boolean = false,
    var isTimeFrom5to10MinChecked: Boolean = false,
    var isTimeUnder5MinChecked: Boolean = false,
    var isIncludeCanceledTrips: Boolean? = false,


    var distanceChecked: Double? = 0.0,
    var distanceCheckedFrom: Double? = 0.0,
    var distanceCheckedTo: Double? = 0.0,

    var timeCheckedFrom: Double? = 0.0,
    var timeCheckedTo: Double? = 0.0
//    var TimeChecked: Double? = 0.0

) : Parcelable
