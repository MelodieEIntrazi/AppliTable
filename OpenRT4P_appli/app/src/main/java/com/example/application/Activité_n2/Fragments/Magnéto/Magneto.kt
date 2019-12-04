package com.example.application.Activité_n2.Fragments.Magnéto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.application.Activité_n1.Bluetooth.Peripherique
import com.example.application.Activité_n2.Fragments.Focus.FocusParametre
import com.example.application.R

/**
 * Est appelé sur le fragment focus stacking et permet de faire rotater le moteur en fonction de la bonne caméra indiqué par le spinner
 * sur le mode focus stacking
 */
class Magneto : androidx.fragment.app.Fragment() {
    private var backward: ImageButton? = null
    private var backward2: ImageButton? = null
    private var forward: ImageButton? = null
    private var forward2: ImageButton? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_magneto, container, false)
        backward = v.findViewById(R.id.backward)
        backward2 = v.findViewById(R.id.backward2)
        forward = v.findViewById(R.id.forward)
        forward2 = v.findViewById(R.id.forward2)
        /*
        fleche pour avancer de 10 pas dans le sens horaire
         */forward2!!.setOnClickListener(View.OnClickListener {
            var data: String? = ""
            data += "-1" + "," // idCommande
            data += Integer.toString(5) + "," //mode magnéto
            data += Integer.toString(400) + "," //acceleration
            data += Integer.toString(400) + "," //speed
            data += Integer.toString(1) + "," //steps
            data += Integer.toString(0) + "," //direction
            data += "0" + ","
            data += Integer.toString(10) + "," //rotation number
            data += Integer.toString(-1) + "," //Frame
            data += Integer.toString(-1) + "," //camera number
            data += Integer.toString(-1) + "," //pause between camera
            data += "0" + "," //focus
            data += Integer.toString(FocusParametre.numeroCamera) //numero Camera
            FocusParametre.compteurPas += 10
            FocusParametre.compteur!!.text = FocusParametre.compteurPas.toString()
            Peripherique.peripherique!!.envoyer(data!!)
        })
        /*
        fleche pour avancer de 1 pas dans le sens horaire
         */forward!!.setOnClickListener(View.OnClickListener {
            var data: String? = ""
            data += "-1" + "," // idCommande
            data += Integer.toString(5) + "," //mode magnéto
            data += Integer.toString(400) + "," //acceleration
            data += Integer.toString(400) + "," //speed
            data += Integer.toString(1) + "," //steps
            data += Integer.toString(0) + "," //direction
            data += "0" + ","
            data += Integer.toString(1) + "," //rotation number
            data += Integer.toString(-1) + "," //Frame
            data += Integer.toString(-1) + "," //camera number
            data += Integer.toString(-1) + "," //pause between camera
            data += "0" + "," //focus
            data += Integer.toString(FocusParametre.numeroCamera) //numero Camera
            FocusParametre.compteurPas += 1
            FocusParametre.compteur!!.text = FocusParametre.compteurPas.toString()
            Peripherique.peripherique!!.envoyer(data!!)
        })
        /*
        fleche pour avancer de 10 pas dans le sens anti-horaire
         */backward2!!.setOnClickListener(View.OnClickListener {
            var data: String? = ""
            data += "-1" + "," // idCommande
            data += Integer.toString(5) + "," //mode magnéto
            data += Integer.toString(400) + "," //acceleration
            data += Integer.toString(400) + "," //speed
            data += Integer.toString(1) + "," //steps
            data += Integer.toString(1) + "," //direction
            data += "0" + ","
            data += Integer.toString(10) + "," //rotation number
            data += Integer.toString(-1) + "," //Frame
            data += Integer.toString(-1) + "," //camera number
            data += Integer.toString(-1) + "," //pause between camera
            data += "0" + "," //focus
            data += Integer.toString(FocusParametre.numeroCamera) //numero Camera
            FocusParametre.compteurPas -= 10
            FocusParametre.compteur!!.text = FocusParametre.compteurPas.toString()
            Peripherique.peripherique!!.envoyer(data!!)
        })
        /*
        fleche pour avancer de 1 pas dans le sens anti-horaire
         */backward!!.setOnClickListener(View.OnClickListener {
            var data: String? = ""
            data += "-1" + "," // idCommande
            data += Integer.toString(5) + "," //mode magnéto
            data += Integer.toString(400) + "," //acceleration
            data += Integer.toString(400) + "," //speed
            data += Integer.toString(1) + "," //steps
            data += Integer.toString(1) + "," //direction
            data += "0" + "," // choix rotation
            data += Integer.toString(1) + "," //rotation number
            data += Integer.toString(-1) + "," //Frame
            data += Integer.toString(-1) + "," //camera number
            data += Integer.toString(-1) + "," //pause between camera
            data += "0" + "," //focus
            data += Integer.toString(FocusParametre.numeroCamera) //numero Camera
            FocusParametre.compteurPas -= 1
            FocusParametre.compteur!!.text = FocusParametre.compteurPas.toString()
            Peripherique.peripherique!!.envoyer(data!!)
        })
        return v
    }
}