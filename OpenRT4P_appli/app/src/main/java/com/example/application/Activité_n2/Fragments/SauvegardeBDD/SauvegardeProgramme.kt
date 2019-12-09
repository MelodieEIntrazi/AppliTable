package com.example.application.Activité_n2.Fragments.SauvegardeBDD

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.application.Activité_n2.Fragments.Programmé.Programme
import com.example.application.Activité_n2.Interface.ChangeFragments
import com.example.application.Activité_n2.MainActivity
import com.example.application.BDD.DbThread
import com.example.application.BDD.ValeurReelAndProgDataBase
import com.example.application.Objets.ValeurProgramme
import com.example.application.R

/**
 * Permet de sauvegarder les information du Mode Prorgrammé en ajoutant un texte et en valider grace au bouton ok
 */
class SauvegardeProgramme : androidx.fragment.app.Fragment() {
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
    private var valeurReelAndProgDataBase: ValeurReelAndProgDataBase? = null
    private lateinit var mDbWorkerThread: DbThread
    private val changeFragments: ChangeFragments = MainActivity.listener!!
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
        valeurReelAndProgDataBase = ValeurReelAndProgDataBase.getDatabase(context = MainActivity.context!!)
        mDbWorkerThread = DbThread("dbWorkerThread")
        mDbWorkerThread.start()
        return view
    }

    /*
    lors de l'appuie sur le bouton ok, on enregistre les paramètres du mode programmé
     */
    override fun onStart() {
        super.onStart()
        oKButton!!.setOnClickListener {
            val nouvelEnregistrement = ValeurProgramme(idRentre!!.text.toString(), stepsEditText, accelerationEditText, vitesseEditText, directionSwitch, pause_between_cameraEditText, camera_numberEditText, frameEditText, focus_stackingSwitch)
            insertWeatherDataInDb(nouvelEnregistrement)
            changeFragments.onChangeFragment(Programme.programme)

        }
    }

    /*
    Check si la bdd est pleine ou si l'élément existait déja
     */
    private fun insertWeatherDataInDb(valeurProgramme: ValeurProgramme) {
        val task = Runnable { valeurReelAndProgDataBase?.vPDao()?.insert(valeurProgramme) }
        mDbWorkerThread.postTask(task)
    }

    override fun onDestroy() {
        mDbWorkerThread.quit()
        super.onDestroy()
    }
}