package com.development.task.triphava.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Results (
    val trips : List<Trips>
): Parcelable