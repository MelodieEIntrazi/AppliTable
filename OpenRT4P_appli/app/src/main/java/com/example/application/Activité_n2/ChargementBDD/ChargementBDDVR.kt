package com.example.application.Activité_n2.ChargementBDD

import android.os.AsyncTask
import com.example.application.BDD.DataBaseHelperValeurReel.Companion.getInstance
import com.example.application.objets.valeurReel

class ChargementBDDVR(private val mListenerR: chargmentVR) : AsyncTask<Void?, Void?, List<valeurReel?>?>() {
    protected override fun doInBackground(vararg voids: Void?): List<valeurReel?>? {
        return getInstance()!!.valeurReelDAO!!.all
    }

    override fun onPostExecute(listeVR: List<valeurReel?>?) {
        print(listeVR!!.size)
        mListenerR.chargementBDDvaleursR(listeVR)
    }

}