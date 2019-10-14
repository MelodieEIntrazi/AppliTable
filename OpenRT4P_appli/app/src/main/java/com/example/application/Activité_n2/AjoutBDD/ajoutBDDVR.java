package com.example.application.Activité_n2.AjoutBDD;

import android.os.AsyncTask;

import com.example.application.BDD.DataBaseHelperValeurReel;
import com.example.application.objets.valeurReel;


import java.util.List;

public class ajoutBDDVR extends AsyncTask<valeurReel,Void,Integer> {
    private ajoutVR mListener;

    public ajoutBDDVR(ajoutVR mListener){
        this.mListener=mListener;
    }
    @Override
    protected Integer doInBackground(valeurReel... strings) {

        List<valeurReel> valeurs = DataBaseHelperValeurReel.getInstance().getValeurReelDAO().getAll();
        if(valeurs.size()<10){
            String[] tab = new String[valeurs.size()];
            for(int i=0;i<valeurs.size();i++){
                tab[i]=valeurs.get(i).id;
            }
        valeurs.add(strings[0]);
        DataBaseHelperValeurReel.getInstance().getValeurReelDAO().insertAll(valeurs);
            for(int j=0;j<tab.length;j++){
                if(tab[j].equals(strings[0].id))
                {return 2;}}
        return 0;}
        else{
            DataBaseHelperValeurReel.getInstance().getValeurReelDAO().insertAll(valeurs);
            return 1;
        }

    }

    @Override
    protected void onPostExecute(Integer bool) {
        mListener.ajoutBDDvaleursR(bool);

    }
}
