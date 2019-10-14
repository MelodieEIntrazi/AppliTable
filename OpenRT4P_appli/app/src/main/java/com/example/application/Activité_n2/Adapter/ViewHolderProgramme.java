package com.example.application.Activité_n2.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.application.R;

public class ViewHolderProgramme {


    final TextView id;

    final TextView speed;
    final TextView acceleration ;
    final TextView frame;
    final TextView camera;
    final TextView steps;
    final TextView timeBetweenPhotos;
    final Switch direction;
    final Button selection;
    final Button suppression;
    final Switch focus;

        public ViewHolderProgramme(View view) {
            id = view.findViewById(R.id.idProgramme);
            speed = view.findViewById(R.id.speedProgramme);
            acceleration = view.findViewById(R.id.accelerationProgramme);
            frame = view.findViewById(R.id.frameProgramme);
            camera = view.findViewById(R.id.cameraProgramme);
            steps = view.findViewById(R.id.stepsProgramme);
            timeBetweenPhotos = view.findViewById(R.id.timeBetweenphotosProgramme);
            direction = view.findViewById(R.id.directionProgramme);
            selection = view.findViewById(R.id.okProgramme);
            suppression= view.findViewById(R.id.deleteProgramme);
            focus = view.findViewById(R.id.focusStackingChoix);
        }



}
