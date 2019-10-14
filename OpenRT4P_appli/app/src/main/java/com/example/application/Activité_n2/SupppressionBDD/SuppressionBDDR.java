package com.example.application.Activité_n2.SupppressionBDD;

import android.os.AsyncTask;

import com.example.application.BDD.DataBaseHelperValeurProgramme;
import com.example.application.BDD.DataBaseHelperValeurReel;
import com.example.application.objets.valeurProgramme;
import com.example.application.objets.valeurReel;

public class SuppressionBDDR extends AsyncTask<valeurReel,Void, Void> {
    @Override
    protected Void doInBackground(valeurReel... strings) {

        DataBaseHelperValeurReel.getInstance().getValeurReelDAO().delete(strings[0]);
        return null;

    }
}
