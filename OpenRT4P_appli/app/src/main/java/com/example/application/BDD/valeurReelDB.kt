package com.example.application.BDD

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.application.Activité_n2.DAO.DAOvaleurReel
import com.example.application.objets.valeurReel

@Database(entities = [valeurReel::class], version = 5)
abstract class valeurReelDB : RoomDatabase() {
    abstract fun valeurReelDAO(): DAOvaleurReel?
}