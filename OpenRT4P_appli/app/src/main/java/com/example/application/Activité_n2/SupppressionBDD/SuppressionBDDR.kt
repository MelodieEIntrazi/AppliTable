package com.example.application.Activité_n2.SupppressionBDD

import android.os.AsyncTask
import com.example.application.BDD.DataBaseHelperValeurReel.Companion.getInstance
import com.example.application.objets.valeurReel

class SuppressionBDDR : AsyncTask<valeurReel?, Void?, Void?>() {
    override fun doInBackground(vararg strings: valeurReel?): Void? {
        getInstance()!!.valeurReelDAO!!.delete(strings[0])
        return null
    }
}