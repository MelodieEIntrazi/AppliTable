package com.example.application.BDD

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.application.Activité_n2.DAO.ValeurProgrammeDao
import com.example.application.Activité_n2.DAO.ValeurReelDao
import com.example.application.Objets.ValeurProgramme
import com.example.application.Objets.ValeurReel

@Database(entities = [ValeurReel::class, ValeurProgramme::class], version = 8, exportSchema = false)
abstract class ValeurReelAndProgDataBase : RoomDatabase() {

    abstract fun vRDao(): ValeurReelDao
    abstract fun vPDao(): ValeurProgrammeDao

    companion object {
        private var INSTANCE: ValeurReelAndProgDataBase? = null

        fun getDatabase(context: Context): ValeurReelAndProgDataBase? {

            if (INSTANCE == null) {
                synchronized(ValeurReelAndProgDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context,
                            ValeurReelAndProgDataBase::class.java, "VR.db")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}