package com.example.application.Activité_n2.Adapter

import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import com.example.application.R

class ViewHolderReel(view: View) {
    @JvmField
    val id: TextView = view.findViewById(R.id.idReel)
    @JvmField
    val speed: TextView
    @JvmField
    val acceleration: TextView
    @JvmField
    val steps: TextView
    @JvmField
    val rotationMode: Switch
    @JvmField
    val nbRotation: TextView
    @JvmField
    val direction: Switch
    @JvmField
    val selection: Button
    @JvmField
    val suppression: Button

    init {
        speed = view.findViewById(R.id.speedReel)
        acceleration = view.findViewById(R.id.accelerationReel)
        nbRotation = view.findViewById(R.id.rotationReel)
        steps = view.findViewById(R.id.stepsReel)
        rotationMode = view.findViewById(R.id.choixRotationReel)
        direction = view.findViewById(R.id.directionReel)
        selection = view.findViewById(R.id.okReel)
        suppression = view.findViewById(R.id.deleteReel)
    }
}