package com.development.task.triphava.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Trips (
    val id : Int,
    val status : String,
    val request_date : String,
    val pickup_lat : Double,
    val pickup_lng : Double,
    val pickup_location : String,
    val dropoff_lat : Double,
    val dropoff_lng : Double,
    val dropoff_location : String,
    val pickup_date : String,
    val dropoff_date : String,
    val type : String,
    val driver_id : Int,
    val driver_name : String,
    val driver_rating : Int,
    val driver_pic : String,
    val car_make : String,
    val car_model : String,
    val car_number : String,
    val car_year : Int,
    val car_pic : String,
    val duration : Int,
    val duration_unit : String,
    val distance : Double,
    val distance_unit : String,
    val cost : Int,
    val cost_unit : String
): Parcelable