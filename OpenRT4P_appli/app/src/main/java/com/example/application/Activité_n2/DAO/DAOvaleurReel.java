package com.example.application.Activité_n2.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.application.objets.valeurReel;

import java.util.List;

@Dao
public interface DAOvaleurReel {
    @Query("SELECT * FROM valeurReel")
    List<valeurReel> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<valeurReel> valeurProgrammes);

    @Delete
    void delete(valeurReel valeurProgrammes);
}
