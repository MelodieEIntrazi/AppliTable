package com.example.application.Activité_n2.Fragments.Programmé

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.application.Activité_n1.Bluetooth.Peripherique
import com.example.application.Activité_n2.Fragments.Charger_Bdd.BddProgramme
import com.example.application.Activité_n2.Fragments.Focus.FocusParametre
import com.example.application.Activité_n2.Fragments.Menu.Menu
import com.example.application.Activité_n2.Fragments.SauvegardeBDD.SauvegardeProgramme
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_programme, container, false)
        peripherique = Peripherique.peripherique
        val save = v.findViewById<Button>(R.id.save_programme)
        val send = v.findViewById<Button>(R.id.send_programme)
        val charger = v.findViewById<Button>(R.id.charger)
        val accelerationEditText = v.findViewById<EditText>(R.id.AccelerationProgramme)
        val vitesseEditText = v.findViewById<EditText>(R.id.VitesseProgramme)
        val directionSwitch = v.findViewById<Switch>(R.id.DirectionProgramme)
        val stepsEditText = v.findViewById<EditText>(R.id.StepsProgramme)
        val frameEditText = v.findViewById<EditText>(R.id.FrameProgramme)
        val camera_numberEditText = v.findViewById<EditText>(R.id.Camera_Number_Programme)
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
            camera_numberEditText.setText(camera)
            directionSwitch.isChecked = arguments!!.getBoolean("direction")
            focus_stackingSwitch!!.setChecked(arguments!!.getBoolean("focus"))
        }
        if (focus_stackingSwitch!!.isChecked()) {
            parametrage!!.setVisibility(View.VISIBLE)
        } else {
            parametrage!!.setVisibility(View.INVISIBLE)
        }
        /*
        switch du focus stacking
         */focus_stackingSwitch!!.setOnClickListener(View.OnClickListener {
            if (focus_stackingSwitch!!.isChecked()) {
                parametrage!!.setVisibility(View.VISIBLE)
            } else {
                parametrage!!.setVisibility(View.INVISIBLE)
            }
        })
        /*
        parametre utilisé pour le focus stacking et ouvre sur un nouveau fragment
         */parametrage!!.setOnClickListener(View.OnClickListener {
            parametrage!!.setVisibility(View.VISIBLE)
            fragmentManager!!.beginTransaction().add(R.id.fragment, FocusParametre.focusParametre).addToBackStack(null).commit()
        })
        /*
        Permet de lancer le fragment charger et de récuperer des informations présent dans la bdd
         */charger.setOnClickListener { fragmentManager!!.beginTransaction().replace(R.id.fragment, BddProgramme.bddProgramme).addToBackStack(null).commit() }
        /*
        Permet de le fragment 'sauvegarder' les informations du mode Programmé
         */save.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("AccelerationSaveProgramme", accelerationEditText.text.toString())
            bundle.putString("VitesseSaveProgramme", vitesseEditText.text.toString())
            bundle.putBoolean("DirectionSaveProgramme", directionSwitch.isChecked)
            bundle.putString("TableStepsSaveProgramme", stepsEditText.text.toString())
            bundle.putString("FrameSaveProgramme", frameEditText.text.toString())
            bundle.putString("CameraSaveProgramme", camera_numberEditText.text.toString())
            bundle.putString("TempsEntrePhotosSaveProgramme", pause_between_cameraEditText.text.toString())
            bundle.putBoolean("FocusSaveProgramme", focus_stackingSwitch!!.isChecked())
            Sauv_frag.arguments = bundle
            fragmentManager!!.beginTransaction().replace(R.id.fragment, Sauv_frag).addToBackStack(null).commit()
        }
        /*
        Envoie les informations du mode programmé au boitier de commande
         */send.setOnClickListener {
            accelerationInt = accelerationEditText.text.toString().toInt()
            vitesseInt = vitesseEditText.text.toString().toInt()
            stepsInt = stepsEditText.text.toString().toInt()
            frameInt = frameEditText.text.toString().toInt()
            camera_numberInt = camera_numberEditText.text.toString().toInt()
            pause_between_cameraInt = pause_between_cameraEditText.text.toString().toInt()
            val programmeOrder = ProgrammeOrder(accelerationInt, vitesseInt,
                    directionSwitch.isChecked, stepsInt, frameInt, camera_numberInt, pause_between_cameraInt, focus_stackingSwitch!!.isChecked())
            ListOrder.list.add(programmeOrder)
            Menu.orderAdapter!!.notifyDataSetChanged()
            data = ""
            data += programmeOrder.id.toString() + ","
            data += "0" + ","
            data += Integer.toString(accelerationInt) + ","
            data += Integer.toString(vitesseInt) + ","
            data += Integer.toString(stepsInt) + ","
            data += if (directionSwitch.isChecked) {
                "1" + ","
            } else {
                "0" + ","
            }
            data += "-1" + "," //choix rotation
            data += "-1" + "," //rotation number
            data += Integer.toString(frameInt) + ","
            data += Integer.toString(camera_numberInt) + ","
            data += Integer.toString(pause_between_cameraInt) + ","
            println(focus_stackingSwitch!!.isChecked())
            data += if (focus_stackingSwitch!!.isChecked()) {
                Integer.toString(FocusParametre.cameraAdapter!!.nombrePhotoFocus + 1)
            } else {
                "0"
            }
            println(data)
            peripherique!!.envoyer(data!!)
            fragmentManager!!.beginTransaction().remove(programme).addToBackStack(null).commit()
            focus_stackingSwitch!!.setChecked(false)
        }
        return v
    }

    companion object {
        var parametrage: Button? = null
        var focus_stackingSwitch: Switch? = null
        @JvmField
        var programme = Programme()
    }
}