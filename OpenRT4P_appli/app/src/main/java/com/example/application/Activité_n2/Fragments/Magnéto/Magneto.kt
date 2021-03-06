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
         */forward2!!.setOnClickListener {
            var data: String? = ""
            data += "-1" + "," // idCommande
            data += 5.toString() + "," //mode magnéto
            data += 400.toString() + "," //acceleration
            data += 400.toString() + "," //speed
            data += 1.toString() + "," //steps
            data += 0.toString() + "," //direction
            data += "0" + ","
            data += 10.toString() + "," //rotation number
            data += (-1).toString() + "," //Frame
            data += (-1).toString() + "," //camera number
            data += (-1).toString() + "," //pause between camera
            data += "0" + "," //focus
            data += FocusParametre.numeroCamera.toString() //numero Camera
            FocusParametre.compteurPas += 10
            FocusParametre.compteur!!.text = FocusParametre.compteurPas.toString()
            Peripherique.peripherique!!.envoyer(data!!)
        }
        /*
        fleche pour avancer de 1 pas dans le sens horaire
         */forward!!.setOnClickListener {
            var data: String? = ""
            data += "-1" + "," // idCommande
            data += 5.toString() + "," //mode magnéto
            data += 400.toString() + "," //acceleration
            data += 400.toString() + "," //speed
            data += 1.toString() + "," //steps
            data += 0.toString() + "," //direction
            data += "0" + ","
            data += 1.toString() + "," //rotation number
            data += (-1).toString() + "," //Frame
            data += (-1).toString() + "," //camera number
            data += (-1).toString() + "," //pause between camera
            data += "0" + "," //focus
            data += FocusParametre.numeroCamera.toString() //numero Camera
            FocusParametre.compteurPas += 1
            FocusParametre.compteur!!.text = FocusParametre.compteurPas.toString()
            Peripherique.peripherique!!.envoyer(data!!)
        }
        /*
        fleche pour avancer de 10 pas dans le sens anti-horaire
         */backward2!!.setOnClickListener {
            var data: String? = ""
            data += "-1" + "," // idCommande
            data += 5.toString() + "," //mode magnéto
            data += 400.toString() + "," //acceleration
            data += 400.toString() + "," //speed
            data += 1.toString() + "," //steps
            data += 1.toString() + "," //direction
            data += "0" + ","
            data += 10.toString() + "," //rotation number
            data += (-1).toString() + "," //Frame
            data += (-1).toString() + "," //camera number
            data += (-1).toString() + "," //pause between camera
            data += "0" + "," //focus
            data += FocusParametre.numeroCamera.toString() //numero Camera
            FocusParametre.compteurPas -= 10
            FocusParametre.compteur!!.text = FocusParametre.compteurPas.toString()
            Peripherique.peripherique!!.envoyer(data!!)
        }
        /*
        fleche pour avancer de 1 pas dans le sens anti-horaire
         */backward!!.setOnClickListener(View.OnClickListener {
            var data: String? = ""
            data += "-1" + "," // idCommande
            data += 5.toString() + "," //mode magnéto
            data += 400.toString() + "," //acceleration
            data += 400.toString() + "," //speed
            data += 1.toString() + "," //steps
            data += 1.toString() + "," //direction
            data += "0" + "," // choix rotation
            data += 1.toString() + "," //rotation number
            data += (-1).toString() + "," //Frame
            data += (-1).toString() + "," //camera number
            data += (-1).toString() + "," //pause between camera
            data += "0" + "," //focus
            data += FocusParametre.numeroCamera.toString() //numero Camera
            FocusParametre.compteurPas -= 1
            FocusParametre.compteur!!.text = FocusParametre.compteurPas.toString()
            Peripherique.peripherique!!.envoyer(data!!)
        })
        return v
    }
}