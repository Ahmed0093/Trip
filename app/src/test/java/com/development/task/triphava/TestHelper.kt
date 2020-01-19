package com.development.task.triphava

import com.development.task.triphava.model.Trips
import com.development.task.triphava.repository.CANCELED_STATUS
import com.development.task.triphava.repository.COMPLETED_STATUS

fun getTripsCompletedMock ():Trips {
    return Trips(
        1,
        COMPLETED_STATUS,
        "2100t",
        3.0,
        3.0,
        "moroco",
        12.0,
        1.0,
        "egypt"
        ,
        "16.10.2011",
        "egypt",
        "type",
        1,
        "ahmed",
        14,
        "https://hr.hava.bz/trips/c13.jpg",
        "make",
        "https://hr.hava.bz/trips/c13.jpg",
        "cardumber",
        1,
        "https://hr.hava.bz/trips/c13.jpg",
        12,
        "min",
        12.0,
        "ss",
        12,
        "sa"
    )
}



    fun getTripsCanceledMock():Trips {
        return Trips(
            1,
            CANCELED_STATUS,
            "2100t",
            3.0,
            3.0,
            "moroco",
            12.0,
            1.0,
            "egypt"
            ,
            "16.10.2011",
            "egypt",
            "type",
            1,
            "ahmed",
            14,
            "https://hr.hava.bz/trips/c13.jpg",
            "make",
            "https://hr.hava.bz/trips/c13.jpg",
            "cardumber",
            1,
            "https://hr.hava.bz/trips/c13.jpg",
            12,
            "min",
            12.0,
            "ss",
            12,
            "sa"
        )
    }
