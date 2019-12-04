package com.example.application.BDD

import androidx.room.Room
import com.example.application.Activité_n2.DAO.DAOvaleurProgramme
import com.example.application.Activité_n2.MainActivity.Companion.context

class DataBaseHelperValeurProgramme {
    private val db: valeurProgrammeDB
    val valeurProgrammeDAO: DAOvaleurProgramme?
        get() = db.valeurProgrammeDAO()

    companion object {
        private var instance: DataBaseHelperValeurProgramme? = null
        @JvmStatic
        fun getInstance(): DataBaseHelperValeurProgramme? {
            if (instance == null) {
                instance = DataBaseHelperValeurProgramme()
            }
            return instance
        }
    }

    init {
        db = Room.databaseBuilder(context!!, valeurProgrammeDB::class.java, "ma_bdd_valeurProgramme.db").build()
    }
}