package com.example.application.Objets

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Class définissant les attribus du mode valeur Programme
@Entity(tableName = "ValeurProgramme")
class ValeurProgramme {
    @PrimaryKey
    @NonNull
    var id: String = " "
    @ColumnInfo
    var tableSteps: String? = null
    @ColumnInfo
    var acceleration: String? = null
    @ColumnInfo
    var speed: String? = null
    @ColumnInfo
    var direction: Boolean? = null
    @ColumnInfo
    var timeBetweenPhotosNumber: String? = null
    @ColumnInfo
    var camera_number: String? = null
    @ColumnInfo
    var frame: String? = null
    @ColumnInfo
    var focusStacking: Boolean? = null

    constructor(id: String, tableSteps: String?, acceleration: String?, speed: String?, direction: Boolean?, timeBetweenPhotosNumber: String?, camera_number: String?, frame: String?, focusStacking: Boolean?) {
        this.id = id
        this.tableSteps = tableSteps
        this.acceleration = acceleration
        this.speed = speed
        this.direction = direction
        this.timeBetweenPhotosNumber = timeBetweenPhotosNumber
        this.camera_number = camera_number
        this.frame = frame
        this.focusStacking = focusStacking
    }
}