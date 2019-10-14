package com.example.application.Activité_n2.Fragments.Liste;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.application.R;

/**
 Il s'agit d'un fragment présent sur le menu permettant un résumé des information du mode Temps réel
 */
public class Ordre_temps_reel_liste extends Fragment {


    public Ordre_temps_reel_liste() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ordre_temps_reel_liste, container,false);
        return view;
    }

}
