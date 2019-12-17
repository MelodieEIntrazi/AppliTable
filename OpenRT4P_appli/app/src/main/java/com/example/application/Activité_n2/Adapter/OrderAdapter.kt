package com.example.application.Activité_n2.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.application.Activité_n2.Fragments.Menu.Menu
import com.example.application.Activité_n2.Order.ListOrder
import com.example.application.Activité_n2.Order.ListOrder.delete
import com.example.application.Activité_n2.Order.ListOrder.get
import com.example.application.Activité_n2.Order.ListOrder.getById
import com.example.application.Activité_n2.Order.Order
import com.example.application.Activité_n2.Order.ProgrammeOrder
import com.example.application.Activité_n2.Order.TempsReelOrder
import com.example.application.R
import java.util.*

class OrderAdapter(var context: Context, var orderList: List<Order>) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    var orderArray = ArrayList<Order>()
    var inflater: LayoutInflater = LayoutInflater.from(context)

    internal inner class ProgrammedViewHolder(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {
        var id = 0
        var nombre_de_prise: TextView = v.findViewById(R.id.nombre_de_prise)
        var nombre_de_camera: TextView = v.findViewById(R.id.nombre_de_camera)
        var focus_stacking: TextView = v.findViewById(R.id.focus_stacking)
        var delete: ImageButton = v.findViewById(R.id.delete_programme)
        var infosProgramme: Button

        init {
            delete.setOnClickListener {
                delete(id)
                Menu.pauseButton!!.setBackgroundResource(R.drawable.pause_icon)
            }
            infosProgramme = v.findViewById(R.id.infos_programme)
            infosProgramme.setOnClickListener {
                Menu.view!!.visibility = View.VISIBLE
                Menu.listInfos!!.visibility = View.VISIBLE
                println("id :" + Integer.toString(id))
                println(getById(id)!!.listInstruction)
                Menu.instructionAdapter!!.instructionList = getById(id)!!.listInstruction
                Menu.deleteButton!!.visibility = View.VISIBLE
                Menu.instructionAdapter!!.notifyDataSetChanged()
            }
        }
    }

    internal inner class RealTimeViewHolder(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {
        var id = 0
        var vitesse: TextView = v.findViewById(R.id.vitesse)
        var direction: TextView = v.findViewById(R.id.directionProgramme)
        var temps_tour: TextView = v.findViewById(R.id.temps_tour)
        var delete: ImageButton = v.findViewById(R.id.delete_temps_reel)
        var infosTempsReel: Button = v.findViewById(R.id.infos_temps_reel)

        init {
            delete.setOnClickListener {
                delete(id)
                Menu.pauseButton!!.setBackgroundResource(R.drawable.pause_icon)
            }
            infosTempsReel.setOnClickListener {
                Menu.view!!.visibility = View.VISIBLE
                Menu.listInfos!!.visibility = View.VISIBLE
                println("id :$id")
                println(getById(id)!!.listInstruction)
                Menu.instructionAdapter!!.instructionList = getById(id)!!.listInstruction
                Menu.deleteButton!!.visibility = View.VISIBLE
                Menu.instructionAdapter!!.notifyDataSetChanged()
            }
        }
    }

    internal inner class DefaultViewHolder(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {
        var textView: TextView

        init {
            textView = v.findViewById(R.id.commandeTextCamera)
            textView.text = "Commande suivante"
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position >= ListOrder.list.size) return -1
        return if (get(position).type == "ProgrammeOrder") {
            0
        } else if (get(position).type == "TempsReelOrder") {
            1
        } else {
            2
        }
    }

    override fun getItemCount(): Int {
        return ListOrder.list.size + 1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return when (i) {
            0 -> ProgrammedViewHolder(LayoutInflater.from(context).inflate(R.layout.ordre_programme_liste, viewGroup, false))
            1 -> RealTimeViewHolder(LayoutInflater.from(context).inflate(R.layout.ordre_temps_reel_liste, viewGroup, false))
            else -> DefaultViewHolder(LayoutInflater.from(context).inflate(R.layout.ordre_defaut_liste, viewGroup, false))
        }
    }

    override fun onBindViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, i: Int) {
        when (viewHolder.itemViewType) {
            0 -> {
                val vProgrammed = viewHolder as ProgrammedViewHolder
                val oProgrammed = ListOrder.list[i] as ProgrammeOrder
                if (oProgrammed.focus_stacking) {
                    vProgrammed.focus_stacking.text = "Mode focus stacking activé"
                } else {
                    vProgrammed.focus_stacking.text = "Mode focus stacking désactivé"
                }
                vProgrammed.nombre_de_camera.text = oProgrammed.nombre_de_camera.toString() + " caméra(s)"
                vProgrammed.nombre_de_prise.text = oProgrammed.nombre_de_prise.toString() + " prise(s) de vue(s)"
                vProgrammed.id = oProgrammed.id
            }
            1 -> {
                val vRealTime = viewHolder as RealTimeViewHolder
                val oRealTime = get(i) as TempsReelOrder
                vRealTime.vitesse.text = oRealTime.vitesse.toString() + " pas/s"
                if (oRealTime.isDirection) {
                    vRealTime.direction.text = "Rotation en sens horaire"
                } else vRealTime.direction.text = "Rotation en sens antihoraire"
                if (oRealTime.isTimeMode) {
                    vRealTime.temps_tour.text = oRealTime.rotation_number.toString() + " s"
                } else vRealTime.temps_tour.text = oRealTime.rotation_number.toString() + " tour(s)"
                vRealTime.id = oRealTime.id
            }
        }
    }

    init {
        orderArray.addAll(orderList)
    }
}