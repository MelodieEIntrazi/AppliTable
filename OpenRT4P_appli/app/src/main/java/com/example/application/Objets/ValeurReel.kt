package com.example.application.Objets

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Class définissant les attribus du mode valeur Reel
@Entity(tableName = "valeurReel")
class ValeurReel {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: String = ""
    @ColumnInfo(name = "tableSteps")
    var tableSteps: String? = null
    @ColumnInfo(name = "acceleration")
    var acceleration: String? = null
    @ColumnInfo(name = "speed")
    var speed: String? = null
    @ColumnInfo(name = "direction")
    var direction: Boolean? = null
    @ColumnInfo(name = "rotationMode")
    var rotationMode: Boolean? = null
    @ColumnInfo(name = "rotationNumber")
    var rotationNumber: String? = null

    constructor(id: String, tableSteps: String?, acceleration: String?, speed: String?, direction: Boolean?, rotationMode: Boolean?, rotationNumber: String?) {
        this.id = id
        this.tableSteps = tableSteps
        this.acceleration = acceleration
        this.speed = speed
        this.direction = direction
        this.rotationMode = rotationMode
        this.rotationNumber = rotationNumber
    }
}