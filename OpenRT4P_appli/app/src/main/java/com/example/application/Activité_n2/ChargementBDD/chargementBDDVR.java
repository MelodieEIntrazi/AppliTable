package com.example.application.Activité_n2.ChargementBDD;

import android.os.AsyncTask;

import com.example.application.BDD.DataBaseHelperValeurReel;
import com.example.application.objets.valeurReel;

import java.util.List;

public class chargementBDDVR extends AsyncTask<Void,Void,List<valeurReel>> {
    private chargmentVR mListenerR;
    public chargementBDDVR(chargmentVR mListenerR) {this.mListenerR=mListenerR; }

    @Override
    protected List<valeurReel> doInBackground(Void... voids) {
        List<valeurReel> valeurVR= DataBaseHelperValeurReel.getInstance().getValeurReelDAO().getAll();
        return valeurVR;
    }

    @Override
    protected void onPostExecute(List<valeurReel> listeVR) {
        System.out.print(listeVR.size());
        mListenerR.chargementBDDvaleursR(listeVR);

    }
}
