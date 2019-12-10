package com.example.application.Activité_n2.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.application.Activité_n2.Instructions.Instruction
import com.example.application.Activité_n2.Instructions.InstructionCamera
import com.example.application.Activité_n2.Instructions.InstructionMoteur
import com.example.application.Activité_n2.Order.ListOrder.getById
import com.example.application.R

class InstructionAdapter(var context: Context, var instructionList: List<Instruction>?) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    var inflater: LayoutInflater = LayoutInflater.from(context)

    internal inner class InstructionMoteurHolder(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {
        var id = 0
        var commandeTextMoteur: TextView = v.findViewById(R.id.commandeTextMoteur)
        var instructionTextMoteur: TextView = v.findViewById(R.id.InstructionTextMoteur)
        var vitesseTextMoteur: TextView = v.findViewById(R.id.VitesseTextMoteur)
        var stepsTextMoteur: TextView = v.findViewById(R.id.StepsTextMoteur)
        var etatMoteur: Button = v.findViewById(R.id.etatButton)

    }

    internal inner class InstructionCameraHolder(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {
        var id = 0
        var commandeTextCamera: TextView
        var instructionTextCamera: TextView
        var numberPhotoTextCamera: TextView
        var pauseTextCamera: TextView
        var etatInstructionCamera: Button

        init {
            commandeTextCamera = v.findViewById(R.id.commandeTextCamera)
            instructionTextCamera = v.findViewById(R.id.instructionTextCamera)
            numberPhotoTextCamera = v.findViewById(R.id.numberPhotoTextCamera)
            pauseTextCamera = v.findViewById(R.id.PauseTextCamera)
            etatInstructionCamera = v.findViewById(R.id.Etat_InstructionCamera)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (instructionList!![position].type == "InstructionMoteur") {
            0
        } else if (instructionList!![position].type == "InstructionCamera") {
            1
        } else {
            2
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return when (i) {
            0 -> InstructionMoteurHolder(LayoutInflater.from(context).inflate(R.layout.instruction_moteur_liste, viewGroup, false))
            1 -> InstructionCameraHolder(LayoutInflater.from(context).inflate(R.layout.instruction_camera_liste, viewGroup, false))
            else -> InstructionMoteurHolder(LayoutInflater.from(context).inflate(R.layout.instruction_moteur_liste, viewGroup, false))
        }
    }

    override fun onBindViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, i: Int) {
        when (viewHolder.itemViewType) {
            0 -> {
                val instructionMoteurHolder = viewHolder as InstructionMoteurHolder
                val instructionMoteur = instructionList!![i] as InstructionMoteur
                instructionMoteurHolder.commandeTextMoteur.text = "Commande n° " + Integer.toString(instructionMoteur.idCommande)
                instructionMoteurHolder.instructionTextMoteur.text = "Instruction n° " + Integer.toString(instructionMoteur.idInstruction)
                instructionMoteurHolder.vitesseTextMoteur.text = "Vitesse : " + Integer.toString(instructionMoteur.vitesse) + "pas/s"
                instructionMoteurHolder.stepsTextMoteur.text = "Nombre de pas : " + Integer.toString(instructionMoteur.stepsTime)
                if (instructionMoteur.termine == 2) {
                    instructionMoteurHolder.etatMoteur.setBackgroundColor(Color.GREEN)
                } else if (instructionMoteur.termine == 1) {
                    instructionMoteurHolder.etatMoteur.setBackgroundColor(Color.rgb(255, 128, 0))
                } else if (instructionMoteur.termine == 0) {
                    instructionMoteurHolder.etatMoteur.setBackgroundColor(Color.RED)
                }
            }
            1 -> {
                val instructionCameraHolder = viewHolder as InstructionCameraHolder
                val instructionCamera = instructionList!![i] as InstructionCamera
                instructionCameraHolder.commandeTextCamera.text = "Commande n° " + Integer.toString(instructionCamera.idCommande)
                instructionCameraHolder.instructionTextCamera.text = "Instruction n° " + Integer.toString(instructionCamera.idInstruction)
                instructionCameraHolder.numberPhotoTextCamera.text = "Nombre de photos  " + Integer.toString(instructionCamera.frame)
                instructionCameraHolder.pauseTextCamera.text = "Pause : " + Integer.toString(instructionCamera.pause)
                if (instructionCamera.termine == 2) {
                    instructionCameraHolder.etatInstructionCamera.setBackgroundColor(Color.GREEN)
                } else if (instructionCamera.termine == 1) {
                    instructionCameraHolder.etatInstructionCamera.setBackgroundColor(Color.rgb(255, 128, 0))
                } else if (instructionCamera.termine == 0) {
                    instructionCameraHolder.etatInstructionCamera.setBackgroundColor(Color.RED)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (instructionList == null) {
            0
        } else if (instructionList!!.size == 0) {
            0
        } else {
            getById(instructionList!![0].idCommande)!!.listInstruction.size
        }
    }

}