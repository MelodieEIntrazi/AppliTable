package com.example.application.Activité_n2.AjoutBDD;

import android.os.AsyncTask;

import com.example.application.BDD.DataBaseHelperValeurProgramme;
import com.example.application.objets.valeurProgramme;


import java.util.List;

public class ajoutBDDVP extends AsyncTask<valeurProgramme,Void, Integer> {
    private ajoutVP mListener;

    public ajoutBDDVP(ajoutVP mListener){
        this.mListener=mListener;
    }

    @Override
    protected Integer doInBackground(valeurProgramme... strings) {

        List<valeurProgramme> valeurs = DataBaseHelperValeurProgramme.getInstance().getValeurProgrammeDAO().getAll();
        if(valeurs.size()<10){
        String[] tab = new String[valeurs.size()];
        for(int i=0;i<valeurs.size();i++){
            tab[i]=valeurs.get(i).id;
        }
        valeurs.add(strings[0]);
        DataBaseHelperValeurProgramme.getInstance().getValeurProgrammeDAO().insertAll(valeurs);
        for(int j=0;j<tab.length;j++){
            if(tab[j].equals(strings[0].id))
            {return 2;}}

        return 0;}
        else{
            DataBaseHelperValeurProgramme.getInstance().getValeurProgrammeDAO().insertAll(valeurs);
            return 1;
        }
    }

    @Override
    protected void onPostExecute(Integer bool) {
        mListener.ajoutBDDvaleursP(bool);

    }
}
