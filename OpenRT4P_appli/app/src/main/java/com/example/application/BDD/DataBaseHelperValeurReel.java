package com.example.application.BDD;

import android.arch.persistence.room.Room;

import com.example.application.Activité_n2.DAO.DAOvaleurReel;
import com.example.application.Activité_n2.Fragments.Programmé.Programme;
import com.example.application.Activité_n2.Fragments.Temps_réel.TempsReel;
import com.example.application.Activité_n2.MainActivity;


public class DataBaseHelperValeurReel {
    static DataBaseHelperValeurReel instance=null;
    private final valeurReelDB db;

    public static DataBaseHelperValeurReel getInstance(){
        if(instance==null){
            instance=new DataBaseHelperValeurReel();
        }
        return instance;
    }

    public DAOvaleurReel getValeurReelDAO(){
        return db.valeurReelDAO();
    }

    public DataBaseHelperValeurReel(){
        db = Room.databaseBuilder(MainActivity.getContext(),valeurReelDB.class,"ma_bdd_valeurReel.db").build();

    }
}
