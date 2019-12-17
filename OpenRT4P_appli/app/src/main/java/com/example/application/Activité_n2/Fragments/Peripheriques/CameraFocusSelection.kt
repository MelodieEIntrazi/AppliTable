package com.example.application.Activité_n2.Fragments.Peripheriques

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.application.Activité_n2.Adapter.PeripheriqueSelectionAdapter
import com.example.application.Activité_n2.Fragments.Focus.FocusParametre
import com.example.application.Activité_n2.Interface.ChangeFragments
import com.example.application.Activité_n2.MainActivity
import com.example.application.R
import java.util.*

class CameraFocusSelection : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_peripherique_selection, container, false)
        val onChangeFragListener: ChangeFragments = MainActivity.listener!!
        cameraFocusSelectionRecycler = v.findViewById(R.id.peripherique_selection)
        val layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        if (listCameraFocus.size == 0) {
            for (i in 0..8) {
                listCameraFocus.add(Peripherique("Camera Focus " + (i + 1).toString(), false))
            }
        }
        if (cameraFocusSelectionAdapter == null) {
            cameraFocusSelectionAdapter = PeripheriqueSelectionAdapter(MainActivity.context!!, listCameraFocus)
        }
        cameraFocusSelectionRecycler!!.layoutManager = layoutManager
        cameraFocusSelectionRecycler!!.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        cameraFocusSelectionRecycler!!.adapter = cameraFocusSelectionAdapter
        envoyer = v.findViewById(R.id.envoyer_peripherique_selection)
        envoyer!!.setOnClickListener(View.OnClickListener {
            var data = "0,7,1"
            for (i in 0..8) {
                data += ",0"
            }
            for (p in listCameraFocus) {
                data += if (p.isConnecte) {
                    ",1"
                } else ",0"
            }
            envoyer!!.isEnabled = false
            FocusParametre.spinnerFirstTime = true
            println("this is DATA :$data")
            com.example.application.Activité_n1.Bluetooth.Peripherique.peripherique!!.envoyer(data)
            onChangeFragListener.onChangeFragment(FocusParametre.focusParametre)


        })
        return v
    }

    companion object {
        var cameraFocusSelection = CameraFocusSelection()
        var cameraFocusSelectionRecycler: androidx.recyclerview.widget.RecyclerView? = null
        var cameraFocusSelectionAdapter: PeripheriqueSelectionAdapter? = null
        var envoyer: Button? = null
        var listCameraFocus = ArrayList<Peripherique>()
    }
}