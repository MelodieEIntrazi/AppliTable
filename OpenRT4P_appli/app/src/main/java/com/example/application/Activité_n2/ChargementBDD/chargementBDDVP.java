package com.example.application.Activité_n2.ChargementBDD;

import android.os.AsyncTask;

import com.example.application.BDD.DataBaseHelperValeurProgramme;
import com.example.application.objets.valeurProgramme;

import java.util.List;

public class chargementBDDVP extends AsyncTask<Void,Void,List<valeurProgramme>> {
    private chargmentVP mListenerR;
    public chargementBDDVP(chargmentVP mListenerR) {this.mListenerR=mListenerR; }

    @Override
    protected List<valeurProgramme> doInBackground(Void... voids) {
        List<valeurProgramme> valeurVP= DataBaseHelperValeurProgramme.getInstance().getValeurProgrammeDAO().getAll();

        return valeurVP;
    }

    @Override
    protected void onPostExecute(List<valeurProgramme> listeVP) {

        mListenerR.chargementBDDvaleursP(listeVP);

    }
}