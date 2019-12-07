package com.example.application.Activité_n2.DAO

import androidx.room.*
import com.example.application.Objets.ValeurReel

@Dao
interface ValeurReelDao {
    @Query("SELECT * FROM valeurReel")
    fun getAll(): List<ValeurReel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(valeurProgrammes: List<ValeurReel>)

    @Delete
    fun delete(valeurReel: ValeurReel)

    @Query("DELETE FROM valeurReel")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(valeurReel: ValeurReel)


}