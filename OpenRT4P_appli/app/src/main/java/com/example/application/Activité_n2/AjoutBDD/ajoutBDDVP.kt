package com.example.application.Activité_n2.AjoutBDD

import android.os.AsyncTask
import com.example.application.BDD.DataBaseHelperValeurProgramme.Companion.getInstance
import com.example.application.objets.valeurProgramme

class ajoutBDDVP(private val mListener: ajoutVP) : AsyncTask<valeurProgramme?, Void?, Int>() {
    protected override fun doInBackground(vararg strings: valeurProgramme?): Int {
        val valeurs = getInstance()!!.valeurProgrammeDAO!!.all
        return if (valeurs.size < 10) {
            val tab = arrayOfNulls<String>(valeurs.size)
            for (i in valeurs.indices) {
                tab[i] = valeurs[i]!!.id
            }
            valeurs.add(strings[0])
            getInstance()!!.valeurProgrammeDAO!!.insertAll(valeurs)
            for (j in tab.indices) {
                if (tab[j] == strings[0]!!.id) {
                    return 2
                }
            }
            0
        } else {
            getInstance()!!.valeurProgrammeDAO!!.insertAll(valeurs)
            1
        }
    }

    override fun onPostExecute(bool: Int) {
        mListener.ajoutBDDvaleursP(bool)
    }

}