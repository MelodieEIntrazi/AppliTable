package com.example.application.Activité_n2.DAO

import androidx.room.*
import com.example.application.objets.valeurProgramme

@Dao
interface DAOvaleurProgramme {
    @get:Query("SELECT * FROM valeurProgramme")
    val all: MutableList<valeurProgramme?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(valeurProgrammes: List<valeurProgramme?>?)

    @Delete
    fun delete(valeurProgrammes: valeurProgramme?)
}