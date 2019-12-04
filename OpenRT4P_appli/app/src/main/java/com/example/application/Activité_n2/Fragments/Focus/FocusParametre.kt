package com.example.application.Activité_n2.Fragments.Focus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.example.application.Activité_n1.Bluetooth.Peripherique
import com.example.application.Activité_n2.Adapter.CameraAdapter
import com.example.application.Activité_n2.Camera.Camera
import com.example.application.Activité_n2.Fragments.Peripheriques.PeripheriqueSelection
import com.example.application.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Est appelé une fois qu'on se trouve sur le Mode programmé et qu'on selectionne le bouton paramètre
 * Permet de programmer le focus stacking des différents caméra connectés au préalable
 */
class FocusParametre : androidx.fragment.app.Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_focus_parametre, container, false)
        valider = v.findViewById(R.id.sendFocus)
        reset = v.findViewById(R.id.reset)
        compteur = v.findViewById(R.id.compteur)
        compteur!!.setEnabled(false)
        spinnerCamera = v.findViewById(R.id.spinnerFocus)
        listCamera = v.findViewById(R.id.listCamera)
        ajoutPhotoFocus = v.findViewById(R.id.AjoutePhotoFocus)
        deletePhotoFocus = v.findViewById(R.id.DeletePhotoFocus)
        sendPhotoFocus = v.findViewById(R.id.sendPhotoFocus)
        /*
        initialisation du spinner permettant de choisir la caméra ayant le focus stacking
         */if (spinnerFirstTime) {
            for (i in 0..8) {
                if (PeripheriqueSelection.listPeripheriques[i + 10].isConnecte) {
                    spinnerCameraItems.add("Camera " + (i + 1))
                    indiceCamera.add(i)
                }
            }
            for (i in 0..8) {
                cameraList.add(Camera())
            }
            spinnerFirstTime = false
        }
        /*
        ajouter une rotation avec un maximum de 8 rotation car il y a 9 photo focus possible
         */ajoutPhotoFocus!!.setOnClickListener(View.OnClickListener {
            if (cameraAdapter!!.nombrePhotoFocus < 8) {
                cameraAdapter!!.nombrePhotoFocus++
                cameraAdapter!!.notifyDataSetChanged()
            }
        })
        /*
        supprimer une rotation avec un minimum de 1 rotation possible
         */deletePhotoFocus!!.setOnClickListener(View.OnClickListener {
            if (cameraAdapter!!.nombrePhotoFocus > 1) {
                cameraAdapter!!.nombrePhotoFocus--
                cameraAdapter!!.notifyDataSetChanged()
            }
        })
        /*
        initialiser la liste d'affichage des rotations de caméra
         */if (cameraAdapter == null) {
            cameraAdapter = CameraAdapter(MaintActivity.context, cameraList[0].param)
            cameraAdapter!!.nombrePhotoFocus = 1
        }
        val layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        listCamera!!.setLayoutManager(layoutManager)
        listCamera!!.setItemAnimator(androidx.recyclerview.widget.DefaultItemAnimator())
        listCamera!!.setAdapter(cameraAdapter)
        val adapter = ArrayAdapter(MaintActivity.context, R.layout.custom_spinner, spinnerCameraItems)
        adapter.setDropDownViewResource(R.layout.custom_spinner)
        spinnerCamera!!.setAdapter(adapter)
        adapter.setNotifyOnChange(true)
        spinnerCamera!!.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                for (i in 0..8) {
                    cameraAdapter!!.nombreDePas[i] = 0
                }
                for (i in 0..8) {
                    cameraAdapter!!.nombreDePas[i] = cameraList[indiceCamera[position]].param[i]
                }
                cameraAdapter!!.numeroCamera = indiceCamera[position]
                cameraAdapter!!.notifyDataSetChanged()
                numeroCamera = indiceCamera[position]
                compteurPas = 0
                compteur!!.setText(Integer.toString(compteurPas))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        /*
        envoie les paramètres de rotation au boitier de commande qui les envera à la caméra sélectionner par le spinner
         */sendPhotoFocus!!.setOnClickListener(View.OnClickListener {
            var data: String? = ""
            data += "-1" + ","
            data += "8" + ","
            data += Integer.toString(numeroCamera)
            for (i in 0..7) {
                data += ","
                data += cameraList[numeroCamera].param[i]
            }
            Peripherique.peripherique!!.envoyer(data!!)
            Toast.makeText(context, "MESSAGE ENVOYE", Toast.LENGTH_LONG).show()
        })
        /*
        Sert à retourner sur le fragment mode Programmé une fois que les paramètres du focus stacking ont été choisis
         */valider!!.setOnClickListener(View.OnClickListener { fragmentManager!!.beginTransaction().remove(focusParametre).addToBackStack(null).commit() })
        /*
        Permet de réinitialiser les pas lorsque l'on a appuyé sur des touches du magnéto
         */reset!!.setOnClickListener(View.OnClickListener {
            compteurPas = 0
            compteur!!.setText(Integer.toString(compteurPas))
        })
        return v
    }

    companion object {
        var focusParametre = FocusParametre()
        var compteurPas = 0
        var compteur: TextView? = null
        var spinnerCamera: Spinner? = null
        var spinnerFirstTime = true
        var numeroCamera = 0
        var cameraAdapter: CameraAdapter? = null
        var listCamera: androidx.recyclerview.widget.RecyclerView? = null
        var ajoutPhotoFocus: ImageButton? = null
        var deletePhotoFocus: ImageButton? = null
        var valider: Button? = null
        var reset: Button? = null
        var sendPhotoFocus: Button? = null
        var spinnerCameraItems = ArrayList<String>()
        var indiceCamera: MutableList<Int> = ArrayList()
        @JvmField
        var cameraList: MutableList<Camera> = ArrayList()
    }
}