package com.example.application.Activité_n2.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.application.Activité_n2.Adapter.CameraAdapter.CameraHolder
import com.example.application.Activité_n2.Fragments.Focus.FocusParametre
import com.example.application.R
import java.util.*

class CameraAdapter(var context: Context, nombreDePas: IntArray) : androidx.recyclerview.widget.RecyclerView.Adapter<CameraHolder>() {
    var numeroCamera = 0
    var nombrePhotoFocus = 0
    var nombreDePas: MutableList<Int> = ArrayList()
    var inflater: LayoutInflater

    inner class CameraHolder(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {
        var position: Int? = 0
        var nombreDePasText: TextView
        var nombreDePasEditText: EditText

        init {
            nombreDePasText = v.findViewById(R.id.nombreDePasText)
            nombreDePasEditText = v.findViewById(R.id.nombreDePasEditText)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CameraHolder {
        return CameraHolder(LayoutInflater.from(context).inflate(R.layout.parametre_camera_list, viewGroup, false))
    }

    override fun onBindViewHolder(cameraHolder: CameraHolder, i: Int) {
        cameraHolder.position = i
        cameraHolder.nombreDePasText.text = "Nombre de pas entre la photo " + Integer.toString(i + 1) + " et " + Integer.toString(i + 2)
        println(Integer.toString(nombreDePas[i]))
        cameraHolder.nombreDePasEditText.setText(FocusParametre.cameraList[numeroCamera].param[i].toString())
        cameraHolder.nombreDePasEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString() == "") return
                FocusParametre.cameraList[numeroCamera].param[cameraHolder.position!!] = s.toString().toInt()
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun getItemCount(): Int {
        return nombrePhotoFocus
    }

    init {
        this.nombreDePas.clear()
        for (i in 0..8) {
            this.nombreDePas.add(nombreDePas[i])
        }
        inflater = LayoutInflater.from(context)
    }
}