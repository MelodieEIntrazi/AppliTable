package com.example.application.Activité_n2.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.example.application.Activité_n2.Adapter.PeripheriqueSelectionAdapter.PeripheriqueHolder
import com.example.application.Activité_n2.Fragments.Peripheriques.Peripherique
import com.example.application.R
import java.util.*

//Adapte et Affiche la liste des périphériques dans une view
class PeripheriqueSelectionAdapter(var context: Context, var listPeripheriques: ArrayList<Peripherique>) : androidx.recyclerview.widget.RecyclerView.Adapter<PeripheriqueHolder>() {
    var inflater: LayoutInflater = LayoutInflater.from(context)

    inner class PeripheriqueHolder(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {
        var imageView: ImageView = v.findViewById(R.id.imageView)
        var textPeripherique: TextView = v.findViewById(R.id.textPeripheriqueListe)
        var switchPeripherique: Switch = v.findViewById(R.id.switchPeripheriqueListe)
        var indice: Int = adapterPosition

        init {
            switchPeripherique.setOnClickListener {
                println("indice : $adapterPosition")
                listPeripheriques[adapterPosition].isConnecte = !listPeripheriques[adapterPosition].isConnecte
                //println(listPeripheriques[12].isConnecte)
            }
            /*
            switchPeripherique.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    listPeripheriques.get(indice).setConnecte(isChecked);
                }
            });*/
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PeripheriqueHolder {
        return PeripheriqueHolder(LayoutInflater.from(context).inflate(R.layout.peripherique_selection_liste, viewGroup, false))
    }

    override fun onBindViewHolder(v: PeripheriqueHolder, i: Int) {
        println("i : $i")
        println("indice : " + v.adapterPosition)
        v.switchPeripherique.isChecked = listPeripheriques[v.adapterPosition].isConnecte
        v.indice = v.adapterPosition
        v.textPeripherique.text = listPeripheriques[v.adapterPosition].nom
        if (listPeripheriques[v.adapterPosition].nom.contains("Moteur")) {
            v.imageView.setImageResource(R.drawable.moteur)
        } else if (listPeripheriques[v.adapterPosition].nom.contains("Camera")) {
            v.imageView.setImageResource(R.drawable.camera)
        }
    }

    override fun getItemCount(): Int {
        return listPeripheriques.size
    }

}