package com.example.application.BDD;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.application.Activité_n2.DAO.DAOvaleurProgramme;
import com.example.application.objets.valeurProgramme;

@Database(entities = {valeurProgramme.class}, version=5)
public abstract class valeurProgrammeDB extends RoomDatabase {
    public abstract DAOvaleurProgramme valeurProgrammeDAO();
}
