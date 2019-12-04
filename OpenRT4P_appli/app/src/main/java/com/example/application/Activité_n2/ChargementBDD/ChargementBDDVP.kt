package com.example.application.Activité_n2.ChargementBDD

import android.os.AsyncTask
import com.example.application.BDD.DataBaseHelperValeurProgramme.Companion.getInstance
import com.example.application.objets.valeurProgramme

class ChargementBDDVP(private val mListenerR: chargmentVP) : AsyncTask<Void?, Void?, List<valeurProgramme?>?>() {
    protected override fun doInBackground(vararg voids: Void?): List<valeurProgramme?>? {
        return getInstance()!!.valeurProgrammeDAO!!.all
    }

    override fun onPostExecute(listeVP: List<valeurProgramme?>?) {
        mListenerR.chargementBDDvaleursP(listeVP)
    }

}