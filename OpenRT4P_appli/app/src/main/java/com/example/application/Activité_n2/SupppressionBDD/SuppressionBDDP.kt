package com.example.application.Activité_n2.SupppressionBDD

import android.os.AsyncTask
import com.example.application.BDD.DataBaseHelperValeurProgramme.Companion.getInstance
import com.example.application.objets.valeurProgramme

open class SuppressionBDDP : AsyncTask<valeurProgramme?, Void?, Void?>() {
    override fun doInBackground(vararg strings: valeurProgramme?): Void? {
        getInstance()!!.valeurProgrammeDAO!!.delete(strings[0])
        return null
    }

}