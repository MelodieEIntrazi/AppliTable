package com.example.application.Activité_n2.Fragments.Menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.Activité_n1.Bluetooth.Peripherique
import com.example.application.Activité_n2.Adapter.InstructionAdapter
import com.example.application.Activité_n2.Adapter.OrderAdapter
import com.example.application.Activité_n2.Fragments.Charger_Bdd.BddProgramme
import com.example.application.Activité_n2.Fragments.Charger_Bdd.BddTempsReel
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
        statusText = v.findViewById(R.id.statusProgramme)
        deleteButton = v.findViewById(R.id.deleteInfos)
        spinnerMode = v.findViewById(R.id.spinner)
        spinnerBDD = v.findViewById(R.id.spinnerBDD)
        listOrder = v.findViewById<View>(R.id.orderList) as RecyclerView
        listInfos = v.findViewById<View>(R.id.infosInstructions) as RecyclerView
        pauseButton = v.findViewById(R.id.pause_menu)
        peripherique = Peripherique.peripherique
        val onChangeFragListener: ChangeFragments = MainActivity.listener!!
        //Permet de gerer la pause et d'envoyer l'information au boitier
        if (ListOrder.list.size != 0) {
            pauseButton!!.visibility = View.VISIBLE
            pauseButton!!.setOnClickListener {
                val data = "-1,3" //id commande pas utile mais necessaire
                if (pauseButton!!.background.constantState == resources.getDrawable(R.drawable.pause_icon).constantState) {
                    pauseButton!!.setBackgroundResource(R.drawable.play_icon)
                } else {
                    pauseButton!!.setBackgroundResource(R.drawable.pause_icon)
                }
                peripherique!!.envoyer(data)
            }
        } else {
            pauseButton!!.visibility = View.INVISIBLE
        }


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
        if (spinnerFirstTimeBDD) {
            spinnerModeItemsBDD.add("Choix de la BDD")
            spinnerModeItemsBDD.add("BDD programme")
            spinnerModeItemsBDD.add("BDD réel")
            spinnerFirstTimeBDD = false
        }
        /*
        spinner custom pour les différents menu et permet de changer de fragments en fonction du bon spinner
         */
        val adapter = ArrayAdapter(context!!, R.layout.custom_spinner, spinnerModeItems)
        val adapter2 = ArrayAdapter(context!!, R.layout.custom_spinner, spinnerModeItemsBDD)
        adapter.setDropDownViewResource(R.layout.custom_spinner)
        adapter2.setDropDownViewResource(R.layout.custom_spinner)
        spinnerMode!!.adapter = adapter
        spinnerBDD!!.adapter = adapter2
        adapter.setNotifyOnChange(true)
        adapter2.setNotifyOnChange(true)
        spinnerMode!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) =
                    when (position) {
                        1 -> {
                            onChangeFragListener.onChangeFragment(Programme.programme)
                            spinnerMode!!.setSelection(0)
                        }
                        2 -> {
                            onChangeFragListener.onChangeFragment(TempsReel.temps_reel)
                            spinnerMode!!.setSelection(0)
                        }
                        else -> {
                        }
                    }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerBDD!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    1 -> {
                        onChangeFragListener.onChangeFragment(BddProgramme.bddProgramme)
                        spinnerBDD!!.setSelection(0)
                    }
                    2 -> {
                        onChangeFragListener.onChangeFragment(BddTempsReel.bddTempsReel)
                        spinnerBDD!!.setSelection(0)
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
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        listOrder!!.layoutManager = layoutManager
        listOrder!!.itemAnimator = DefaultItemAnimator()
        listOrder!!.adapter = orderAdapter
        /*
        Permet de quitter le container contenant les différents infos
         */deleteButton!!.setOnClickListener {
            instructionAdapter!!.instructionList = null
            deleteButton!!.visibility = View.INVISIBLE
            Companion.view!!.visibility = View.INVISIBLE
            listInfos!!.visibility = View.INVISIBLE
        }
        /*
        adapter de la liste des différentes infos recu du boitier de commande dans la fonction 'decode' de l'activité 1
         */
        val layoutManager1: RecyclerView.LayoutManager = LinearLayoutManager(context)
        listInfos!!.layoutManager = layoutManager1
        listInfos!!.itemAnimator = DefaultItemAnimator()
        listInfos!!.adapter = instructionAdapter
        MainActivity.actionBars!!.setDisplayHomeAsUpEnabled(false)
        return v
    }

    override fun onDestroy() {
        MainActivity.actionBars!!.setDisplayHomeAsUpEnabled(true)
        super.onDestroy()
    }

    companion object {
        var menu = Menu()
        var spinnerMode: Spinner? = null
        var spinnerModeItems = ArrayList<String>()
        var spinnerFirstTime = true
        var spinnerBDD: Spinner? = null
        var spinnerModeItemsBDD = ArrayList<String>()
        var spinnerFirstTimeBDD = true
        var orderAdapter: OrderAdapter? = null
        var listOrder: RecyclerView? = null
        var statusText: TextView? = null
        @JvmField
        var instructionAdapter: InstructionAdapter? = null
        @JvmField
        var listInfos: RecyclerView? = null
        @JvmField
        var view: View? = null
        var deleteButton: ImageButton? = null
        var pauseButton: Button? = null
    }
}