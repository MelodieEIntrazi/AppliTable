package com.example.application.Activité_n2.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
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
        var imageMoteur: ImageView = v.findViewById(R.id.imageView3)

    }

    internal inner class InstructionCameraHolder(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {
        var id = 0
        var commandeTextCamera: TextView = v.findViewById(R.id.commandeTextCamera)
        var instructionTextCamera: TextView = v.findViewById(R.id.instructionTextCamera)
        var numberPhotoTextCamera: TextView = v.findViewById(R.id.numberPhotoTextCamera)
        var pauseTextCamera: TextView = v.findViewById(R.id.PauseTextCamera)
        var etatInstructionCamera: Button = v.findViewById(R.id.Etat_InstructionCamera)
        var imageCamera: ImageView = v.findViewById(R.id.imageView2)

    }

    override fun getItemViewType(position: Int): Int {
        return when (instructionList!![position].type) {
            "InstructionMoteur" -> {
                0
            }
            "InstructionCamera" -> {
                1
            }
            else -> {
                2
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return when (i) {
            0 -> InstructionMoteurHolder(LayoutInflater.from(context).inflate(R.layout.instruction_moteur_liste, viewGroup, false))
            1 -> InstructionCameraHolder(LayoutInflater.from(context).inflate(R.layout.instruction_camera_liste, viewGroup, false))
            else -> InstructionMoteurHolder(LayoutInflater.from(context).inflate(R.layout.instruction_moteur_liste, viewGroup, false))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, i: Int) {
        when (viewHolder.itemViewType) {
            0 -> {
                val instructionMoteurHolder = viewHolder as InstructionMoteurHolder
                val instructionMoteur = instructionList!![i] as InstructionMoteur
                instructionMoteurHolder.commandeTextMoteur.text = "Commande n° " + instructionMoteur.idCommande.toString()
                instructionMoteurHolder.instructionTextMoteur.text = "Instruction n° " + instructionMoteur.idInstruction.toString()
                instructionMoteurHolder.vitesseTextMoteur.text = "Vitesse : " + instructionMoteur.vitesse.toString() + "pas/s"
                instructionMoteurHolder.stepsTextMoteur.text = "Nombre de pas : " + instructionMoteur.stepsTime.toString()
                when (instructionMoteur.termine) {
                    2 -> {
                        instructionMoteurHolder.etatMoteur.setBackgroundColor(Color.GREEN)
                    }
                    1 -> {
                        instructionMoteurHolder.etatMoteur.setBackgroundColor(Color.rgb(255, 128, 0))
                    }
                    0 -> {
                        instructionMoteurHolder.etatMoteur.setBackgroundColor(Color.RED)
                    }
                }
            }
            1 -> {
                val instructionCameraHolder = viewHolder as InstructionCameraHolder
                val instructionCamera = instructionList!![i] as InstructionCamera
                instructionCameraHolder.commandeTextCamera.text = "Commande n° " + instructionCamera.idCommande.toString()
                instructionCameraHolder.instructionTextCamera.text = "Instruction n° " + instructionCamera.idInstruction.toString()
                instructionCameraHolder.numberPhotoTextCamera.text = "Nombre de photos  " + instructionCamera.frame.toString()
                instructionCameraHolder.pauseTextCamera.text = "Pause : " + instructionCamera.pause.toString()
                when (instructionCamera.termine) {
                    2 -> {
                        instructionCameraHolder.etatInstructionCamera.setBackgroundColor(Color.GREEN)
                    }
                    1 -> {
                        instructionCameraHolder.etatInstructionCamera.setBackgroundColor(Color.rgb(255, 128, 0))
                    }
                    0 -> {
                        instructionCameraHolder.etatInstructionCamera.setBackgroundColor(Color.RED)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return when {
            instructionList == null -> {
                0
            }
            instructionList!!.isEmpty() -> {
                0
            }
            else -> {
                getById(instructionList!![0].idCommande)!!.listInstruction.size
            }
        }
    }

}