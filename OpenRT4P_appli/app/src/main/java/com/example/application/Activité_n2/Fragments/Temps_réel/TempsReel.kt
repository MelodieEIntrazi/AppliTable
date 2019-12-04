package com.example.application.Activité_n2.Fragments.Temps_réel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.example.application.Activité_n1.Bluetooth.Peripherique
import com.example.application.Activité_n2.Fragments.Charger_Bdd.BddTempsReel
import com.example.application.Activité_n2.Fragments.Menu.Menu
import com.example.application.Activité_n2.Fragments.SauvegardeBDD.SauvegardeReel
import com.example.application.Activité_n2.Order.ListOrder
import com.example.application.Activité_n2.Order.TempsReelOrder
import com.example.application.R

/**
 * Fragment utilisé pour le mode temps réel
 */
class TempsReel : androidx.fragment.app.Fragment() {
    var peripherique: Peripherique? = null
    var data: String? = null
    var accelerationInt = 0
    var vitesseInt = 0
    var stepsInt = 0
    var rotation_numberInt = 0
    var Sauv_frag = SauvegardeReel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_temps_reel, container, false)
        peripherique = Peripherique.peripherique
        val save = v.findViewById<Button>(R.id.save_temps_reel)
        val send = v.findViewById<Button>(R.id.send_temps_reel)
        val charger = v.findViewById<Button>(R.id.charger)
        val accelerationEditText = v.findViewById<EditText>(R.id.AccelerationTempsReel)
        val vitesseEditText = v.findViewById<EditText>(R.id.VitesseTempsReel)
        val directionSwitch = v.findViewById<Switch>(R.id.DirectionTempsReel)
        val stepsEditText = v.findViewById<EditText>(R.id.StepsTempsReel)
        val choix_rotationSwitch = v.findViewById<Switch>(R.id.choix_rotation_TempsReel)
        val rotation_numberEditText = v.findViewById<EditText>(R.id.Rotation_number_TempsReel)
        val rotationText = v.findViewById<TextView>(R.id.Choix_RotationText)
        /*
        Permet de recuperer les infos de la bdd présent dans le fragment charger_Bdd
         */if (arguments != null) {
            val speed = arguments!!.getString("vitesse")
            vitesseEditText.setText(speed)
            val acceleration = arguments!!.getString("acceleration")
            accelerationEditText.setText(acceleration)
            val steps = arguments!!.getString("tableSteps")
            stepsEditText.setText(steps)
            val tempsrotat = arguments!!.getString("rotationNumber")
            rotation_numberEditText.setText(tempsrotat)
            directionSwitch.isChecked = arguments!!.getBoolean("direction")
            choix_rotationSwitch.isChecked = arguments!!.getBoolean("rotationMode")
        }
        /*
        Permet de lancer le fragment charger et de récuperer des informations présent dans la bdd
         */charger.setOnClickListener { fragmentManager!!.beginTransaction().replace(R.id.fragment, BddTempsReel.bddTempsReel).addToBackStack(null).commit() }
        /*
        Permet de choisir si l'on veut envoyer un nombre de tours de table ou un temps de fonctionnement du moteur
         */choix_rotationSwitch.setOnClickListener {
            if (choix_rotationSwitch.isChecked) {
                rotationText.text = "Temps de rotation (ms)"
            } else {
                rotationText.text = "Nombre de tours"
            }
        }
        /*
        Permet de le fragment 'sauvegarder' les informations du mode temps Réel
         */save.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("AccelerationSaveTempsReel", accelerationEditText.text.toString())
            bundle.putString("VitesseSaveTempsReel", vitesseEditText.text.toString())
            bundle.putBoolean("DirectionSaveTempsReel", directionSwitch.isChecked)
            bundle.putString("TableStepsSaveTempsReel", stepsEditText.text.toString())
            bundle.putString("RotationNumberSaveTempsReel", rotation_numberEditText.text.toString())
            bundle.putBoolean("RotationModeSaveTempsReel", choix_rotationSwitch.isChecked)
            Sauv_frag.arguments = bundle
            fragmentManager!!.beginTransaction().replace(R.id.fragment, Sauv_frag).addToBackStack(null).commit()
        }
        /*
        Envoie les informations du mode temps réel au boitier de commande
         */send.setOnClickListener {
            accelerationInt = accelerationEditText.text.toString().toInt()
            vitesseInt = vitesseEditText.text.toString().toInt()
            stepsInt = stepsEditText.text.toString().toInt()
            rotation_numberInt = rotation_numberEditText.text.toString().toInt()
            val tempsReelOrder = TempsReelOrder(accelerationInt, vitesseInt,
                    directionSwitch.isChecked, stepsInt, choix_rotationSwitch.isChecked, rotation_numberInt)
            ListOrder.list.add(tempsReelOrder)
            fragmentManager!!.beginTransaction().replace(R.id.fragment, Menu.menu).addToBackStack(null).commit()
            Menu.orderAdapter!!.notifyDataSetChanged()
            data = ""
            data += tempsReelOrder.id.toString() + ","
            data += "1" + ","
            data += Integer.toString(accelerationInt) + ","
            data += Integer.toString(vitesseInt) + ","
            data += Integer.toString(stepsInt) + ","
            data += if (directionSwitch.isChecked) {
                "1" + "," // Time mode
            } else {
                "0" + "," // turn mode
            }
            data += if (choix_rotationSwitch.isChecked) {
                "1" + ","
            } else {
                "0" + ","
            }
            data += Integer.toString(rotation_numberInt) + ","
            data += "-1" + ","
            data += "-1" + ","
            data += "-1" + ","
            data += "-1"
            println(data)
            peripherique!!.envoyer(data!!)
        }
        return v
    }

    companion object {
        @JvmField
        var temps_reel = TempsReel()
    }
}