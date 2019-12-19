package com.example.application.Activité_n2.Fragments.Peripheriques

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.application.Activité_n2.Adapter.PeripheriqueSelectionAdapter
import com.example.application.Activité_n2.Fragments.Charger_Bdd.BddProgramme
import com.example.application.Activité_n2.Fragments.Menu.Menu
import com.example.application.Activité_n2.Fragments.Programmé.Programme
import com.example.application.Activité_n2.Interface.ChangeFragments
import com.example.application.Activité_n2.MainActivity
import com.example.application.R
import java.util.*

class CameraSelection : androidx.fragment.app.Fragment() {
    private val onChangeFragListener: ChangeFragments = MainActivity.listener!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_peripherique_selection, container, false)

        peripheriquesRecycler = v.findViewById(R.id.peripherique_selection)
        val layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        if (listCamera.size == 0) {
            for (i in 0..8) {
                listCamera.add(Peripherique("Camera " + (i + 1).toString(), false))
            }
        }
        if (peripheriqueAdapter == null) {
            peripheriqueAdapter = PeripheriqueSelectionAdapter(MainActivity.context!!, listCamera)
        }
        peripheriquesRecycler!!.layoutManager = layoutManager
        peripheriquesRecycler!!.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        peripheriquesRecycler!!.adapter = peripheriqueAdapter
        envoyer = v.findViewById(R.id.envoyer_peripherique_selection)


        return v
    }

    override fun onStart() {
        super.onStart()
        numberofCamera = 0
        envoyer!!.setOnClickListener {
            var data = "0,7,1"
            for (p in listCamera) {
                if (p.isConnecte) {
                    data += ",1"
                    numberofCamera++
                } else {
                    data += ",0"
                }
            }
            for (i in 0..8) {
                data += ",0"
            }

            envoyer!!.isEnabled = false
            println("this is DATA Cameras :$data, $numberofCamera")
            com.example.application.Activité_n1.Bluetooth.Peripherique.peripherique!!.envoyer(data)
            when (backTopage) {
                1 -> {
                    onChangeFragListener.onChangeFragment(Programme.programme)
                    Programme.programme.numberOfCamera = numberofCamera.toString()
                }
                2 -> {
                    onChangeFragListener.onChangeFragment(BddProgramme.bddProgramme)
                }
                else -> {
                    println("Error")
                    onChangeFragListener.onChangeFragment(Menu.menu)
                }
            }


        }
    }

    companion object {
        var cameraSelection = CameraSelection()
        var peripheriquesRecycler: androidx.recyclerview.widget.RecyclerView? = null
        var peripheriqueAdapter: PeripheriqueSelectionAdapter? = null
        var envoyer: Button? = null
        var listCamera = ArrayList<Peripherique>()
        var backTopage = 0
        var numberofCamera = 0
    }
}