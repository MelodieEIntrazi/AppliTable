package com.example.application.Activité_n2.Fragments.SauvegardeBDD

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.application.Activité_n2.AjoutBDD.ajoutBDDVP
import com.example.application.Activité_n2.AjoutBDD.ajoutVP
import com.example.application.Activité_n2.Fragments.Programmé.Programme
import com.example.application.R
import com.example.application.objets.valeurProgramme

/**
 * Permet de sauvegarder les information du Mode Prorgrammé en ajoutant un texte et en valider grace au bouton ok
 */
class SauvegardeProgramme : androidx.fragment.app.Fragment(), ajoutVP {
    private var majoutAsyncTask: ajoutBDDVP? = null
    var accelerationEditText: String? = null
    var vitesseEditText: String? = null
    var directionSwitch: Boolean? = null
    var stepsEditText: String? = null
    var frameEditText: String? = null
    var camera_numberEditText: String? = null
    var pause_between_cameraEditText: String? = null
    var focus_stackingSwitch: Boolean? = null
    var oKButton: Button? = null
    var idRentre: EditText? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sauvegarde_programme, container, false)
        accelerationEditText = arguments!!.getString("AccelerationSaveProgramme")
        vitesseEditText = arguments!!.getString("VitesseSaveProgramme")
        directionSwitch = arguments!!.getBoolean("DirectionSaveProgramme")
        stepsEditText = arguments!!.getString("TableStepsSaveProgramme")
        frameEditText = arguments!!.getString("FrameSaveProgramme")
        camera_numberEditText = arguments!!.getString("CameraSaveProgramme")
        pause_between_cameraEditText = arguments!!.getString("TempsEntrePhotosSaveProgramme")
        focus_stackingSwitch = arguments!!.getBoolean("FocusSaveProgramme")
        oKButton = view.findViewById(R.id.sauver)
        idRentre = view.findViewById(R.id.IDrentre)
        return view
    }

    /*
    lors de l'appuie sur le bouton ok, on enregistre les paramètres du mode programmé
     */
    override fun onStart() {
        super.onStart()
        majoutAsyncTask = ajoutBDDVP(this)
        oKButton!!.setOnClickListener {
            val nouvelEnregistrement = valeurProgramme()
            nouvelEnregistrement.acceleration = accelerationEditText
            println("nouvelle enregistrement : " + nouvelEnregistrement.acceleration)
            nouvelEnregistrement.camera_number = camera_numberEditText
            nouvelEnregistrement.direction = directionSwitch
            nouvelEnregistrement.frame = frameEditText
            nouvelEnregistrement.id = idRentre!!.text.toString()
            nouvelEnregistrement.speed = vitesseEditText
            nouvelEnregistrement.tableSteps = stepsEditText
            nouvelEnregistrement.timeBetweenPhotosNumber = pause_between_cameraEditText
            nouvelEnregistrement.focusStacking = focus_stackingSwitch
            majoutAsyncTask!!.execute(nouvelEnregistrement)
        }
    }

    /*
    Check si la bdd est pleine ou si l'élément existait déja
     */
    override fun ajoutBDDvaleursP(bool: Int?) {
        if (bool == 1) {
            Toast.makeText(context, "Impossible d'ajouter, supprimez un élément", Toast.LENGTH_LONG).show()
        } else if (bool == 2) {
            Toast.makeText(context, "Cet élément a écrasé l'élément de même nom", Toast.LENGTH_LONG).show()
        }
        fragmentManager!!.beginTransaction().replace(R.id.fragment, Programme.programme).addToBackStack(null).commit()
    }
}