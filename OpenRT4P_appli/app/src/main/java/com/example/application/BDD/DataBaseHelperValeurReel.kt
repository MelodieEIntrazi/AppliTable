package com.example.application.BDD

import androidx.room.Room
import com.example.application.Activité_n2.DAO.DAOvaleurReel
import com.example.application.Activité_n2.MainActivity.Companion.context

class DataBaseHelperValeurReel {
    private val db: valeurReelDB
    val valeurReelDAO: DAOvaleurReel?
        get() = db.valeurReelDAO()

    companion object {
        private var instance: DataBaseHelperValeurReel? = null
        @JvmStatic
        fun getInstance(): DataBaseHelperValeurReel? {
            if (instance == null) {
                instance = DataBaseHelperValeurReel()
            }
            return instance
        }
    }

    init {
        db = Room.databaseBuilder(context!!, valeurReelDB::class.java, "ma_bdd_valeurReel.db").build()
    }
}