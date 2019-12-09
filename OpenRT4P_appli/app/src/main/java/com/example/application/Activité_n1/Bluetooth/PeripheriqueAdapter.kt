package com.example.application.Activité_n1.Bluetooth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.application.Activité_n1.Bluetooth.PeripheriqueAdapter.MyViewHolder
import com.example.application.R
import java.util.*

/*
Il s'agit d'un 'adapter' pour avoir l'affichage souhaité des différents périphériques appairés au téléphone
 */
class PeripheriqueAdapter(var context: Context, var peripheriquesList: List<Peripherique>) : androidx.recyclerview.widget.RecyclerView.Adapter<MyViewHolder>() {
    var peripheriquesArray = ArrayList<Peripherique>()
    var inflater: LayoutInflater

    inner class MyViewHolder internal constructor(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {
        var nameText: TextView

        init {
            nameText = v.findViewById(R.id.textName)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewtype: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_peripherique, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        val peripherique = peripheriquesList[i]
        myViewHolder.nameText.text = peripherique.nom + " " + "(" + peripherique.adresse + ")"
    }

    override fun getItemCount(): Int {
        return peripheriquesList.size
    }

    init {
        inflater = LayoutInflater.from(context)
        peripheriquesArray.addAll(peripheriquesList)
    }
}