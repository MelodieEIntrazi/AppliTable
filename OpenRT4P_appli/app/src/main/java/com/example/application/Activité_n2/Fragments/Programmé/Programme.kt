package com.example.application.Activité_n2.Fragments.Programmé

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.application.Activité_n1.Bluetooth.Peripherique
import com.example.application.Activité_n2.Fragments.Focus.FocusParametre
import com.example.application.Activité_n2.Fragments.Menu.Menu
import com.example.application.Activité_n2.Fragments.Peripheriques.CameraSelection
import com.example.application.Activité_n2.Fragments.SauvegardeBDD.SauvegardeProgramme
import com.example.application.Activité_n2.Interface.ChangeFragments
import com.example.application.Activité_n2.MainActivity
import com.example.application.Activité_n2.Order.ListOrder
import com.example.application.Activité_n2.Order.ProgrammeOrder
import com.example.application.R

/**
 * Fragment utilisé pour le mode programmé
 */

class Programme : androidx.fragment.app.Fragment() {
    private var peripherique: Peripherique? = null
    var data: String? = null
    var accelerationInt = 0
    var vitesseInt = 0
    var stepsInt = 0
    var frameInt = 0
    var camera_numberInt = 0
    var pause_between_cameraInt = 0
    var Sauv_frag = SauvegardeProgramme()
    var camera_numberEditText: EditText? = null
    val changeFragments: ChangeFragments = MainActivity.listener!!
    var numberOfCamera = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_programme, container, false)
        peripherique = Peripherique.peripherique
        val save = v.findViewById<Button>(R.id.save_programme)
        val send = v.findViewById<Button>(R.id.send_programme)
        val accelerationEditText = v.findViewById<EditText>(R.id.AccelerationProgramme)
        val vitesseEditText = v.findViewById<EditText>(R.id.VitesseProgramme)
        val directionSwitch = v.findViewById<Switch>(R.id.DirectionProgramme)
        val stepsEditText = v.findViewById<EditText>(R.id.StepsProgramme)
        val frameEditText = v.findViewById<EditText>(R.id.FrameProgramme)
        camera_numberEditText = v.findViewById<EditText>(R.id.Camera_Number_Programme)
        val pause_between_cameraEditText = v.findViewById<EditText>(R.id.Pause_between_camera_Programme)
        focus_stackingSwitch = v.findViewById(R.id.Focus_stacking_Programme)
        parametrage = v.findViewById(R.id.parametrage)

        /*
        Permet de recuperer les infos de la bdd présent dans le fragment charger_Bdd
         */if (arguments != null) {
            val speed = arguments!!.getString("vitesse")
            vitesseEditText.setText(speed)
            val acceleration = arguments!!.getString("acceleration")
            accelerationEditText.setText(acceleration)
            val steps = arguments!!.getString("tableSteps")
            stepsEditText.setText(steps)
            val tempsPhotos = arguments!!.getString("tempsEntrePhotos")
            pause_between_cameraEditText.setText(tempsPhotos)
            val frame = arguments!!.getString("frame")
            frameEditText.setText(frame)
            val camera = arguments!!.getString("camera")
            camera_numberEditText!!.setText(camera)
            directionSwitch.isChecked = arguments!!.getBoolean("direction")
            focus_stackingSwitch!!.isChecked = arguments!!.getBoolean("focus")
        }
        if (focus_stackingSwitch!!.isChecked) {
            parametrage!!.visibility = View.VISIBLE
        } else {
            parametrage!!.visibility = View.INVISIBLE
        }

        /*
        switch du focus stacking
         */focus_stackingSwitch!!.setOnClickListener {
            if (focus_stackingSwitch!!.isChecked) {
                parametrage!!.visibility = View.VISIBLE
            } else {
                parametrage!!.visibility = View.INVISIBLE
            }
        }
        /*Transforme la zone de texte en bouton*/
        camera_numberEditText!!.inputType = InputType.TYPE_NULL
        camera_numberEditText!!.setOnClickListener {
            changeFragments.onChangeFragment(CameraSelection.cameraSelection)
            CameraSelection.backTopage = 1 //Identifie sur quel fragment retourner
        }
        /*
        parametre utilisé pour le focus stacking et ouvre sur un nouveau fragment
         */parametrage!!.setOnClickListener {
            parametrage!!.visibility = View.VISIBLE
            changeFragments.onChangeFragment(FocusParametre.focusParametre)
        }
        /*
        Permet de le fragment 'sauvegarder' les informations du mode Programmé
         */save.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("AccelerationSaveProgramme", accelerationEditText.text.toString())
            bundle.putString("VitesseSaveProgramme", vitesseEditText.text.toString())
            bundle.putBoolean("DirectionSaveProgramme", directionSwitch.isChecked)
            bundle.putString("TableStepsSaveProgramme", stepsEditText.text.toString())
            bundle.putString("FrameSaveProgramme", frameEditText.text.toString())
            bundle.putString("CameraSaveProgramme", camera_numberEditText!!.text.toString())
            bundle.putString("TempsEntrePhotosSaveProgramme", pause_between_cameraEditText.text.toString())
            bundle.putBoolean("FocusSaveProgramme", focus_stackingSwitch!!.isChecked)
            Sauv_frag.arguments = bundle
            changeFragments.onChangeFragment(Sauv_frag)
        }
        /*
        Envoie les informations du mode programmé au boitier de commande
         */send.setOnClickListener {
            accelerationInt = accelerationEditText.text.toString().toInt()
            vitesseInt = vitesseEditText.text.toString().toInt()
            stepsInt = stepsEditText.text.toString().toInt()
            frameInt = frameEditText.text.toString().toInt()
            camera_numberInt = camera_numberEditText!!.text.toString().toInt()
            pause_between_cameraInt = pause_between_cameraEditText.text.toString().toInt()
            val programmeOrder = ProgrammeOrder(accelerationInt, vitesseInt,
                    directionSwitch.isChecked, stepsInt, frameInt, camera_numberInt, pause_between_cameraInt, focus_stackingSwitch!!.isChecked)
            ListOrder.list.add(programmeOrder)
            Menu.orderAdapter!!.notifyDataSetChanged()
            data = ""
            data += programmeOrder.id.toString() + ","
            data += "0" + ","
            data += "$accelerationInt,"
            data += "$vitesseInt,"
            data += "$stepsInt,"
            data += if (directionSwitch.isChecked) {
                "1" + ","
            } else {
                "0" + ","
            }
            data += "-1" + "," //choix rotation
            data += "-1" + "," //rotation number
            data += "$frameInt,"
            data += "$camera_numberInt,"
            data += "$pause_between_cameraInt,"
            println(focus_stackingSwitch!!.isChecked)
            data += if (focus_stackingSwitch!!.isChecked) {
                (FocusParametre.cameraAdapter!!.nombrePhotoFocus + 1).toString()
            } else {
                "0"
            }

            println("This is data : $data")
            peripherique!!.envoyer(data!!)
            changeFragments.onChangeFragment(Menu.menu)
            focus_stackingSwitch!!.isChecked = false
        }

        return v
    }

    override fun onStart() {
        super.onStart()
        /*Place le nombre correcte de caméra en fonction de la selection réalisé par l'utilisateur*/
        if (numberOfCamera.isNotEmpty()) {
            camera_numberEditText!!.setText(numberOfCamera)
        } else {
            camera_numberEditText!!.setText("0")
        }
    }

    companion object {
        var parametrage: Button? = null
        var focus_stackingSwitch: Switch? = null
        var programme = Programme()
    }


}