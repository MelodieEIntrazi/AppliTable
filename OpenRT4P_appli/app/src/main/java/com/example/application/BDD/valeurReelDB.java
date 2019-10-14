package com.example.application.BDD;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


import com.example.application.Activité_n2.DAO.DAOvaleurReel;
import com.example.application.objets.valeurReel;

@Database(entities = {valeurReel.class}, version=5)
public abstract class valeurReelDB extends RoomDatabase {
    public abstract DAOvaleurReel valeurReelDAO();
}
