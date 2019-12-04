package com.example.application.BDD

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.application.Activité_n2.DAO.DAOvaleurProgramme
import com.example.application.objets.valeurProgramme

@Database(entities = [valeurProgramme::class], version = 5)
abstract class valeurProgrammeDB : RoomDatabase() {
    abstract fun valeurProgrammeDAO(): DAOvaleurProgramme?
}