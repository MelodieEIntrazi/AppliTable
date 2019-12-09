package com.example.application.Activité_n2.Fragments.Peripheriques

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.application.Activité_n1.Connexion
import com.example.application.Activité_n2.Adapter.PeripheriqueSelectionAdapter
import com.example.application.Activité_n2.Fragments.Menu.Menu
import com.example.application.Activité_n2.Interface.ChangeFragments
import com.example.application.Activité_n2.MainActivity
import com.example.application.R
import java.util.*

/*
Ce fragment est appelé en 1er et sert à choisir quel(s) caméra(s) ou focus l'utilisateur compte utiliser
les class utilisés sont :
'Périphérique' pour avoir le nom et l'état des camera(s)
'PeripheriqueSelectionAdapter' pour avoir l'affichage souhaité des différents périphériques

Une fois que les périphériques ont été choisis, on appuie sur le bouton connecter qui envoie une trame au boiter commande grace
à la fonction envoyer dans la class Péripherique de l'activité 1.
la class suivante est donc le menu
 */
class PeripheriqueSelection : androidx.fragment.app.Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_peripherique_selection, container, false)
        val onChangeFragListener: ChangeFragments = MainActivity.listener!!
        peripheriquesRecycler = v.findViewById(R.id.peripherique_selection)
        val layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        if (listPeripheriques.size == 0) {
            println("test")
            listPeripheriques.add(Peripherique("Moteur", false))
            for (i in 0..8) {
                listPeripheriques.add(Peripherique("Camera " + Integer.toString(i + 1), false))
            }
            for (i in 0..8) {
                listPeripheriques.add(Peripherique("Camera Focus " + Integer.toString(i + 1), false))
            }
        }
        if (peripheriqueAdapter == null) {
            peripheriqueAdapter = PeripheriqueSelectionAdapter(MainActivity.context!!, listPeripheriques)
        }
        peripheriquesRecycler!!.layoutManager = layoutManager
        peripheriquesRecycler!!.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        peripheriquesRecycler!!.adapter = peripheriqueAdapter
        envoyer = v.findViewById(R.id.envoyer_peripherique_selection)
        envoyer!!.setOnClickListener(View.OnClickListener {
            var data = "0,7"
            for (p in listPeripheriques) {
                data += if (p.isConnecte) {
                    ",1"
                } else ",0"
            }
            try {
                envoyer!!.isEnabled = false
                com.example.application.Activité_n1.Bluetooth.Peripherique.peripherique!!.envoyer(data)
                //fragmentManager!!.beginTransaction().replace(R.id.fragment, Menu.menu).commit()
                onChangeFragListener.onChangeFragment(Menu.menu)

            } catch (e: KotlinNullPointerException) {
                Toast.makeText(MainActivity.context, "Veuillez Connecter la table", Toast.LENGTH_SHORT).show()
                val intent = Intent(MainActivity.context, Connexion::class.java)
                startActivity(intent)
            }

        })
        return v
    }

    companion object {
        var peripheriqueSelection = PeripheriqueSelection()
        var peripheriquesRecycler: androidx.recyclerview.widget.RecyclerView? = null
        var peripheriqueAdapter: PeripheriqueSelectionAdapter? = null
        var envoyer: Button? = null
        var listPeripheriques = ArrayList<Peripherique>()
    }
}