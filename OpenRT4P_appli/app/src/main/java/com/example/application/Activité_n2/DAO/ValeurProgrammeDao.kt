package com.example.application.Activité_n2.DAO

import androidx.room.*
import com.example.application.Objets.ValeurProgramme

//Les fonctions utiliser avec la bdd Programme
@Dao
interface ValeurProgrammeDao {
    @Query("SELECT * FROM valeurprogramme")
    fun getAll(): List<ValeurProgramme>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(valeurProgrammes: List<ValeurProgramme>)

    @Delete
    fun delete(valeurProgrammes: ValeurProgramme)

    @Query("DELETE FROM valeurprogramme")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(valeurProgrammes: ValeurProgramme)


}