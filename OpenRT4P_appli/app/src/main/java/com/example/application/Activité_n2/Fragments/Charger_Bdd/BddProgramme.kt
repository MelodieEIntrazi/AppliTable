package com.example.application.Activité_n2.Fragments.Charger_Bdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ListView
import android.widget.Toast
import com.example.application.Activité_n2.Adapter.ValeurProgrammeAdapter
import com.example.application.Activité_n2.ChargementBDD.ChargementBDDVP
import com.example.application.Activité_n2.ChargementBDD.chargmentVP
import com.example.application.Activité_n2.Fragments.Programmé.Programme
import com.example.application.Activité_n2.Interface.SelectionProgramme
import com.example.application.R
import com.example.application.objets.valeurProgramme

/**
 * Permet de charger des informations gardées en mémoire lors d'une ancienne sauvegarde
 */
class BddProgramme : androidx.fragment.app.Fragment(), chargmentVP, SelectionProgramme {
    private var mBDDAsyncTask: ChargementBDDVP? = null
    private var mListView: ListView? = null
    var mListener2: chargmentVP = this
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bdd_programme, container, false)
        mListView = view.findViewById(R.id.ListeViewProgramme)
        return view
    }

    override fun onStart() {
        super.onStart()
        mBDDAsyncTask = ChargementBDDVP(mListener2)
        mBDDAsyncTask!!.execute()
    }

    override fun chargementBDDvaleursP(listeVP: List<valeurProgramme?>?) {
        if (listeVP!!.isEmpty()) {
            Toast.makeText(context, "La BDD est vide", Toast.LENGTH_LONG).show()
            val transaction = fragmentManager!!.beginTransaction()
            val fragment = Programme()
            transaction.replace(R.id.fragment, fragment).addToBackStack(null).commit()
        }
        val adapter = ValeurProgrammeAdapter(listeVP as List<valeurProgramme>?)
        mListView!!.adapter = adapter
        adapter.setmListener(this)
        mListView!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                println("test1")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("nothing")
            }
        }
        mListView!!.onItemClickListener = OnItemClickListener { parent, view, position, id -> Toast.makeText(context, position, Toast.LENGTH_SHORT).show() }
    }

    /*
    Permet de selectionner les informations concernant le mode Programmé pour les réutiliser lors du mode programmé
     */
    override fun onSelection(valeurP: valeurProgramme?) {
        val transaction = fragmentManager!!.beginTransaction()
        val fragment = Programme()
        val bundle = Bundle()
        bundle.putString("vitesse", valeurP!!.speed)
        bundle.putString("acceleration", valeurP.acceleration)
        bundle.putString("tempsEntrePhotos", valeurP.timeBetweenPhotosNumber)
        bundle.putString("frame", valeurP.frame)
        bundle.putString("camera", valeurP.camera_number)
        bundle.putBoolean("direction", valeurP.direction!!)
        bundle.putBoolean("focus", valeurP.focusStacking!!)
        bundle.putString("tableSteps", valeurP.tableSteps)
        fragment.arguments = bundle
        transaction.replace(R.id.fragment, fragment).addToBackStack(null).commit()
    }

    /*
    Est appelé lors du click sur le bouton delete present sur le fragment et permet ainsi de revenir sur le mode programmé
     */
    override fun onDelete() {
        fragmentManager!!.beginTransaction().replace(R.id.fragment, Programme.programme).addToBackStack(null).commit()
    }

    companion object {
        var bddProgramme = BddProgramme()
    }
}