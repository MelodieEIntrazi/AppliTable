package com.example.application.objets

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class valeurReel {
    @JvmField
    @PrimaryKey
    var id: String? = null
    @JvmField
    @ColumnInfo
    var tableSteps: String? = null
    @JvmField
    @ColumnInfo
    var acceleration: String? = null
    @JvmField
    @ColumnInfo
    var speed: String? = null
    @JvmField
    @ColumnInfo
    var direction: Boolean? = null
    @JvmField
    @ColumnInfo
    var rotationMode: Boolean? = null
    @JvmField
    @ColumnInfo
    var rotationNumber: String? = null
}