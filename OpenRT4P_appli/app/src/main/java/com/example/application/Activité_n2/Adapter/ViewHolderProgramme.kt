package com.example.application.Activité_n2.Adapter

import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import com.example.application.R

class ViewHolderProgramme(view: View) {
    val id: TextView
    val speed: TextView
    val acceleration: TextView
    val frame: TextView
    val camera: TextView
    val steps: TextView
    val timeBetweenPhotos: TextView
    val direction: Switch
    val selection: Button
    val suppression: Button
    val focus: Switch

    init {
        id = view.findViewById(R.id.idProgramme)
        speed = view.findViewById(R.id.speedProgramme)
        acceleration = view.findViewById(R.id.accelerationProgramme)
        frame = view.findViewById(R.id.frameProgramme)
        camera = view.findViewById(R.id.cameraProgramme)
        steps = view.findViewById(R.id.stepsProgramme)
        timeBetweenPhotos = view.findViewById(R.id.timeBetweenphotosProgramme)
        direction = view.findViewById(R.id.directionProgramme)
        selection = view.findViewById(R.id.okProgramme)
        suppression = view.findViewById(R.id.deleteProgramme)
        focus = view.findViewById(R.id.focusStackingChoix)
    }
}