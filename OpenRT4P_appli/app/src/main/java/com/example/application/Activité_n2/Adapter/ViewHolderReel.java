package com.example.application.Activité_n2.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.application.R;

public class ViewHolderReel {
    final TextView id;

    final TextView speed;
    final TextView acceleration ;
    final TextView steps;
    final Switch rotationMode;
    final TextView nbRotation;
    final Switch direction;
    final Button selection;
    final Button suppression;

    public ViewHolderReel(View view) {
        id = view.findViewById(R.id.idReel);
        speed = view.findViewById(R.id.speedReel);
        acceleration = view.findViewById(R.id.accelerationReel);
        nbRotation = view.findViewById(R.id.rotationReel);
        steps = view.findViewById(R.id.stepsReel);
        rotationMode = view.findViewById(R.id.choixRotationReel);
        direction = view.findViewById(R.id.directionReel);
        selection = view.findViewById(R.id.okReel);
        suppression= view.findViewById(R.id.deleteReel);
    }
}
