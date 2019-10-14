package com.example.application.Activité_n2.SupppressionBDD;

import android.os.AsyncTask;

import com.example.application.BDD.DataBaseHelperValeurProgramme;
import com.example.application.BDD.DataBaseHelperValeurReel;
import com.example.application.objets.valeurProgramme;
import com.example.application.objets.valeurReel;

import java.util.List;

public class SuppressionBDDP extends AsyncTask<valeurProgramme,Void, Void> {
    @Override
    protected Void doInBackground(valeurProgramme... strings) {

        DataBaseHelperValeurProgramme.getInstance().getValeurProgrammeDAO().delete(strings[0]);
        return null;

    }
}
