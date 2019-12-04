package com.example.application.Activité_n2.Fragments.SauvegardeBDD

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.application.Activité_n2.AjoutBDD.ajoutBDDVR
import com.example.application.Activité_n2.AjoutBDD.ajoutVR
import com.example.application.Activité_n2.Fragments.Temps_réel.TempsReel
import com.example.application.R
import com.example.application.objets.valeurReel

/**
 * Permet de sauvegarder les information du Mode Temps réel en ajoutant un texte et en valider grace au bouton ok
 */
class SauvegardeReel : androidx.fragment.app.Fragment(), ajoutVR {
    private var majoutAsyncTask: ajoutBDDVR? = null
    var accelerationEditText: String? = null
    var vitesseEditText: String? = null
    var directionSwitch: Boolean? = null
    var stepsEditText: String? = null
    var rotation_numberEditText: String? = null
    var choix_rotationSwitch: Boolean? = null
    var oKButton: Button? = null
    var idRentre: EditText? = null
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
        return view
    }

    /*
    lors de l'appuie sur le bouton ok, on enregistre les paramètres du mode Temps réel
     */
    override fun onStart() {
        super.onStart()
        majoutAsyncTask = ajoutBDDVR(this)
        oKButton!!.setOnClickListener {
            val nouvelEnregistrement = valeurReel()
            nouvelEnregistrement.id = idRentre!!.text.toString()
            nouvelEnregistrement.acceleration = accelerationEditText
            nouvelEnregistrement.direction = directionSwitch
            nouvelEnregistrement.speed = vitesseEditText
            nouvelEnregistrement.tableSteps = stepsEditText
            nouvelEnregistrement.rotationNumber = rotation_numberEditText
            nouvelEnregistrement.rotationMode = choix_rotationSwitch
            majoutAsyncTask!!.execute(nouvelEnregistrement)
        }
    }

    /*
    Check si la bdd est pleine ou si l'élément existait déja
     */
    override fun ajoutBDDvaleursR(bool: Int?) {
        if (bool == 1) {
            Toast.makeText(context, "Impossible d'ajouter, supprimez un élément", Toast.LENGTH_LONG).show()
        } else if (bool == 2) {
            Toast.makeText(context, "Cet élément a écrasé l'élément de même nom", Toast.LENGTH_LONG).show()
        }
        fragmentManager!!.beginTransaction().replace(R.id.fragment, TempsReel.temps_reel).addToBackStack(null).commit()
    }
}