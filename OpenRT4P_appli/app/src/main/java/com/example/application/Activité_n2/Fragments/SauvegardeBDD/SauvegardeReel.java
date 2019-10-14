package com.example.application.Activité_n2.Fragments.SauvegardeBDD;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.application.Activité_n2.AjoutBDD.ajoutBDDVR;
import com.example.application.Activité_n2.AjoutBDD.ajoutVR;
import com.example.application.Activité_n2.Fragments.Temps_réel.TempsReel;
import com.example.application.R;
import com.example.application.objets.valeurReel;
/**
 * Permet de sauvegarder les information du Mode Temps réel en ajoutant un texte et en valider grace au bouton ok
 */

public class SauvegardeReel extends Fragment implements ajoutVR{

    private ajoutBDDVR majoutAsyncTask;

    String accelerationEditText;
    String vitesseEditText;
    Boolean directionSwitch;
    String stepsEditText;
    String rotation_numberEditText;
    Boolean choix_rotationSwitch;

    Button oKButton;
    EditText idRentre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sauvegarde_reel,container,false);

        accelerationEditText = getArguments().getString("AccelerationSaveTempsReel");
        vitesseEditText = getArguments().getString("VitesseSaveTempsReel");
        directionSwitch = getArguments().getBoolean("DirectionSaveTempsReel");
        stepsEditText = getArguments().getString("TableStepsSaveTempsReel");
        rotation_numberEditText = getArguments().getString("RotationNumberSaveTempsReel");
        choix_rotationSwitch = getArguments().getBoolean("RotationModeSaveTempsReel");

        oKButton = view.findViewById(R.id.sauverReel);
        idRentre=view.findViewById(R.id.IDrentreReel);

        return view;

    }

    /*
    lors de l'appuie sur le bouton ok, on enregistre les paramètres du mode Temps réel
     */
    @Override
    public void onStart() {
        super.onStart();


        majoutAsyncTask=new ajoutBDDVR(this);

        oKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                valeurReel nouvelEnregistrement = new valeurReel();
                nouvelEnregistrement.id=idRentre.getText().toString();
                nouvelEnregistrement.acceleration=accelerationEditText;


                    nouvelEnregistrement.direction=directionSwitch;

                nouvelEnregistrement.speed=vitesseEditText;
                nouvelEnregistrement.tableSteps=stepsEditText;
                nouvelEnregistrement.rotationNumber=rotation_numberEditText;

                    nouvelEnregistrement.rotationMode=choix_rotationSwitch;

                majoutAsyncTask.execute(nouvelEnregistrement);

            }
        });
    }

    /*
    Check si la bdd est pleine ou si l'élément existait déja
     */
    @Override
    public void ajoutBDDvaleursR(Integer bool) {

        if(bool.equals(1)){
            Toast.makeText(getContext(),"Impossible d'ajouter, supprimez un élément", Toast.LENGTH_LONG).show();
        }else if (bool.equals(2)){
            Toast.makeText(getContext(),"Cet élément a écrasé l'élément de même nom", Toast.LENGTH_LONG).show();
        }
        getFragmentManager().beginTransaction().replace(R.id.fragment, TempsReel.temps_reel).addToBackStack(null).commit();
    }

}
