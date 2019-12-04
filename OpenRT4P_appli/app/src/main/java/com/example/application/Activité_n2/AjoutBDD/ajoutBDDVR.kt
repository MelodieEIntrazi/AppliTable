package com.example.application.Activité_n2.AjoutBDD

import android.os.AsyncTask
import com.example.application.BDD.DataBaseHelperValeurReel.Companion.getInstance
import com.example.application.objets.valeurReel

class ajoutBDDVR(private val mListener: ajoutVR) : AsyncTask<valeurReel?, Void?, Int>() {
    protected override fun doInBackground(vararg strings: valeurReel?): Int {
        val valeurs = getInstance()!!.valeurReelDAO!!.all
        return if (valeurs!!.size < 10) {
            val tab = arrayOfNulls<String>(valeurs.size)
            for (i in valeurs.indices) {
                tab[i] = valeurs[i]!!.id
            }
            valeurs.add(strings[0])
            getInstance()!!.valeurReelDAO!!.insertAll(valeurs)
            for (j in tab.indices) {
                if (tab[j] == strings[0]!!.id) {
                    return 2
                }
            }
            0
        } else {
            getInstance()!!.valeurReelDAO!!.insertAll(valeurs)
            1
        }
    }

    override fun onPostExecute(bool: Int) {
        mListener.ajoutBDDvaleursR(bool)
    }

}