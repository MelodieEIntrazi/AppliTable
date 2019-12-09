package com.example.application.Activité_n2.Fragments.SauvegardeBDD

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.application.Activité_n2.Fragments.Temps_réel.TempsReel
import com.example.application.Activité_n2.Interface.ChangeFragments
import com.example.application.Activité_n2.MainActivity
import com.example.application.BDD.DbThread
import com.example.application.BDD.ValeurReelAndProgDataBase
import com.example.application.Objets.ValeurReel
import com.example.application.R

/**
 * Permet de sauvegarder les information du Mode Temps réel en ajoutant un texte et en valider grace au bouton ok
 */
class SauvegardeReel : androidx.fragment.app.Fragment() {
    var accelerationEditText: String? = null
    var vitesseEditText: String? = null
    var directionSwitch: Boolean? = null
    var stepsEditText: String? = null
    var rotation_numberEditText: String? = null
    var choix_rotationSwitch: Boolean? = null
    var oKButton: Button? = null
    var idRentre: EditText? = null
    private var valeurReelAndProgDataBase: ValeurReelAndProgDataBase? = null
    private lateinit var mDbWorkerThread: DbThread
    private val changeFragments: ChangeFragments = MainActivity.listener!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sauvegarde_reel, container, false)
        accelerationEditText = arguments!!.getString("AccelerationSaveTempsReel")
        vitesseEditText = arguments!!.getString("VitesseSaveTempsReel")
        directionSwitch = arguments!!.getBoolean("DirectionSaveTempsReel")
        stepsEditText = arguments!!.getString("TableStepsSaveTempsReel")
        rotation_numberEditText = arguments!!.getString("RotationNumberSaveTempsReel")
        choix_rotationSwitch = arguments!!.getBoolean("RotationModeSaveTempsReel")
        oKButton = view.findViewById(R.id.sauverReel)
        idRentre = view.findViewById(R.id.IDrentreReel)
        valeurReelAndProgDataBase = ValeurReelAndProgDataBase.getDatabase(context = MainActivity.context!!)
        mDbWorkerThread = DbThread("dbWorkerThread")
        mDbWorkerThread.start()
        return view
    }

    /*
    lors de l'appuie sur le bouton ok, on enregistre les paramètres du mode Temps réel
     */
    override fun onStart() {
        super.onStart()
        oKButton!!.setOnClickListener {
            val nouvelEnregistrement = ValeurReel(idRentre!!.text.toString(), stepsEditText, accelerationEditText, vitesseEditText, directionSwitch, choix_rotationSwitch, rotation_numberEditText)
            insertWeatherDataInDb(nouvelEnregistrement)
            //fragmentManager!!.beginTransaction().replace(R.id.fragment, TempsReel.temps_reel).addToBackStack(null).commit()
            changeFragments.onChangeFragment(TempsReel.temps_reel)

        }
    }

    /*
    Check si la bdd est pleine ou si l'élément existait déja
     */
    private fun insertWeatherDataInDb(valeurReel: ValeurReel) {
        val task = Runnable { valeurReelAndProgDataBase?.vRDao()?.insert(valeurReel) }
        mDbWorkerThread.postTask(task)
    }

    override fun onDestroy() {
        mDbWorkerThread.quit()
        super.onDestroy()

    }
}