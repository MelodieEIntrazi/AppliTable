package com.example.application.Activité_n2.DAO

import androidx.room.*
import com.example.application.objets.valeurReel

@Dao
interface DAOvaleurReel {
    @get:Query("SELECT * FROM valeurReel")
    val all: MutableList<valeurReel?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(valeurProgrammes: List<valeurReel?>?)

    @Delete
    fun delete(valeurProgrammes: valeurReel?)
}