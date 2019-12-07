package com.example.application.Activité_n2.Fragments.Menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.example.application.Activité_n1.Bluetooth.Peripherique
import com.example.application.Activité_n2.Adapter.InstructionAdapter
import com.example.application.Activité_n2.Adapter.OrderAdapter
import com.example.application.Activité_n2.Fragments.Peripheriques.PeripheriqueSelection
import com.example.application.Activité_n2.Fragments.Programmé.Programme
import com.example.application.Activité_n2.Fragments.Temps_réel.TempsReel
import com.example.application.Activité_n2.Interface.ChangeFragments
import com.example.application.Activité_n2.MainActivity
import com.example.application.Activité_n2.Order.ListOrder
import com.example.application.R
import java.util.*

/**
 * Dans ce fragment, divers boutons, spinners et recyclerView sont présents :
 * - le spinners sert à choisir parmi les 2 modes programmés ou tems réel
 * - les boutons sont la pour 'charger', 'sauvegarder' et 'envoyer'
 * - le recyclerView est la pour l'affichage des différents modes une fois les paramétres (présent dans un autre fragment) ont été choisis
 */
class Menu : androidx.fragment.app.Fragment() {
    private var peripherique: Peripherique? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_menu, container, false)
        Companion.view = v.findViewById(R.id.infos)
        deleteButton = v.findViewById(R.id.deleteInfos)
        spinnerMode = v.findViewById(R.id.spinner)
        listOrder = v.findViewById<View>(R.id.orderList) as androidx.recyclerview.widget.RecyclerView
        listInfos = v.findViewById<View>(R.id.infosInstructions) as androidx.recyclerview.widget.RecyclerView
        pauseButton = v.findViewById(R.id.pause_menu)
        moduleButton = v.findViewById(R.id.modules_menu)
        peripheriqueButton = v.findViewById(R.id.modules_menu)
        peripherique = Peripherique.peripherique
        val onChangeFragListener: ChangeFragments = MainActivity.listener!!
        //Permet de gerer la pause et d'envoyer l'information au boitier
        pauseButton!!.setOnClickListener(View.OnClickListener {
            if (ListOrder.list.size != 0) {
                var data = ""
                data += "-1" + "," //id commande pas utile mais necessaire
                data += "3"
                if (pauseButton!!.text == "PAUSE") {
                    pauseButton!!.text = "START"
                } else {
                    pauseButton!!.text = "PAUSE"
                }
                peripherique!!.envoyer(data)
            }
        })
        //Permet de gerer le changement de fragment entre le menu et les périphériques
        peripheriqueButton!!.setOnClickListener(View.OnClickListener { fragmentManager!!.beginTransaction().replace(R.id.fragment, PeripheriqueSelection.peripheriqueSelection).commit() })
        /*
        initialise les adapteur présent dans le menu
         */if (orderAdapter == null) {
            orderAdapter = OrderAdapter(MainActivity.context!!, ListOrder.list)
        }
        if (instructionAdapter == null) {
            instructionAdapter = InstructionAdapter(MainActivity.context!!, null)
        }
        if (spinnerFirstTime) {
            spinnerModeItems.add("Nouvelle Commande")
            spinnerModeItems.add("Mode Programmé")
            spinnerModeItems.add("Mode Temps Réel")
            spinnerFirstTime = false
        }
        /*
        spinner custom pour les différents menu et permet de changer de fragments en fonction du bon spinner
         */
        val adapter = ArrayAdapter(context, R.layout.custom_spinner, spinnerModeItems)
        adapter.setDropDownViewResource(R.layout.custom_spinner)
        spinnerMode!!.adapter = adapter
        adapter.setNotifyOnChange(true)
        spinnerMode!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    1 -> {
                        //fragmentManager!!.beginTransaction().add(R.id.fragment, Programme.programme).addToBackStack(null).commit()
                        onChangeFragListener.onChangeFragment(Programme.programme)
                        spinnerMode!!.setSelection(0)
                    }
                    2 -> {
                        onChangeFragListener.onChangeFragment(TempsReel.temps_reel)
                        //fragmentManager!!.beginTransaction().add(R.id.fragment, TempsReel.temps_reel).addToBackStack(null).commit()
                        spinnerMode!!.setSelection(0)
                    }
                    else -> {
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        /*
        adapter de la liste des commandes en fonctions des 2 menu
         */
        val layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        listOrder!!.layoutManager = layoutManager
        listOrder!!.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        listOrder!!.adapter = orderAdapter
        /*
        Permet de quitter le container contenant les différents infos
         */deleteButton!!.setOnClickListener(View.OnClickListener {
            instructionAdapter!!.instructionList = null
            deleteButton!!.visibility = View.INVISIBLE
            view?.visibility = View.INVISIBLE
            listInfos!!.visibility = View.INVISIBLE
        })
        /*
        adapter de la liste des différentes infos recu du boitier de commande dans la fonction 'decode' de l'activité 1
         */
        val layoutManager1: androidx.recyclerview.widget.RecyclerView.LayoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        listInfos!!.layoutManager = layoutManager1
        listInfos!!.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        listInfos!!.adapter = instructionAdapter
        return v
    }

    companion object {
        var menu = Menu()
        var spinnerMode: Spinner? = null
        var spinnerModeItems = ArrayList<String>()
        var spinnerFirstTime = true
        var orderAdapter: OrderAdapter? = null
        var listOrder: androidx.recyclerview.widget.RecyclerView? = null
        @JvmField
        var instructionAdapter: InstructionAdapter? = null
        @JvmField
        var listInfos: androidx.recyclerview.widget.RecyclerView? = null
        @JvmField
        var view: View? = null
        @JvmField
        var deleteButton: ImageButton? = null
        @JvmField
        var pauseButton: Button? = null
        var moduleButton: Button? = null
        var peripheriqueButton: Button? = null
    }
}