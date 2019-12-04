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
import com.example.application.Activité_n2.Adapter.ValeurReelAdapter
import com.example.application.Activité_n2.ChargementBDD.ChargementBDDVR
import com.example.application.Activité_n2.ChargementBDD.chargmentVR
import com.example.application.Activité_n2.Fragments.Temps_réel.TempsReel
import com.example.application.Activité_n2.Interface.SelectionReel
import com.example.application.R
import com.example.application.objets.valeurReel

/**
 * Permet de charger des informations gardées en mémoire lors d'une ancienne sauvegarde
 */
class BddTempsReel : androidx.fragment.app.Fragment(), chargmentVR, SelectionReel {
    private var mBDDAsyncTask: ChargementBDDVR? = null
    private var mListView: ListView? = null
    var mListener2: chargmentVR = this
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bdd_temps_reel, container, false)
        mListView = view.findViewById(R.id.ListeViewReel)
        return view
    }

    override fun onStart() {
        super.onStart()
        mBDDAsyncTask = ChargementBDDVR(mListener2)
        mBDDAsyncTask!!.execute()
    }

    override fun chargementBDDvaleursR(listeVR: List<valeurReel?>?) {
        if (listeVR!!.isEmpty()) {
            Toast.makeText(context, "La BDD est vide", Toast.LENGTH_LONG).show()
            val transaction = fragmentManager!!.beginTransaction()
            val fragment = TempsReel()
            transaction.replace(R.id.fragment, fragment).addToBackStack(null).commit()
        }
        val adapter = ValeurReelAdapter(listeVR as List<valeurReel>?)
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
    Permet de selectionner les informations concernant le mode Temps réel pour les réutiliser lors du mode Temps réel
     */
    override fun onSelection(valeurR: valeurReel?) {
        val transaction = fragmentManager!!.beginTransaction()
        val fragment = TempsReel()
        val bundle = Bundle()
        bundle.putString("vitesse", valeurR!!.speed)
        bundle.putString("acceleration", valeurR.acceleration)
        bundle.putString("rotationNumber", valeurR.rotationNumber)
        bundle.putBoolean("rotationMode", valeurR.rotationMode!!)
        bundle.putBoolean("direction", valeurR.direction!!)
        bundle.putString("tableSteps", valeurR.tableSteps)
        fragment.arguments = bundle
        transaction.replace(R.id.fragment, fragment).addToBackStack(null).commit()
    }

    /*
    Est appelé lors du click sur le bouton delete present sur le fragment et permet ainsi de revenir sur le mode temps réel
     */
    override fun onDelete() {
        fragmentManager!!.beginTransaction().replace(R.id.fragment, TempsReel.temps_reel).addToBackStack(null).commit()
    }

    companion object {
        @JvmField
        var bddTempsReel = BddTempsReel()
    }
}