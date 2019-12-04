package com.example.application.Activité_n2.Fragments.Liste

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.application.R

/**
 * Il s'agit d'un fragment présent sur le menu permettant un résumé des information du mode Temps réel
 */
class Ordre_temps_reel_liste : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle): View {
        return inflater.inflate(R.layout.ordre_temps_reel_liste, container, false)
    }
}