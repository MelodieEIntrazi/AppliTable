package com.example.application.Activité_n2.Fragments.SauvegardeBDD;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.application.Activité_n2.AjoutBDD.ajoutBDDVP;
import com.example.application.Activité_n2.AjoutBDD.ajoutVP;
import com.example.application.Activité_n2.Fragments.Programmé.Programme;
import com.example.application.R;
import com.example.application.objets.valeurProgramme;

/**
 * Permet de sauvegarder les information du Mode Prorgrammé en ajoutant un texte et en valider grace au bouton ok
 */
public class SauvegardeProgramme extends Fragment implements ajoutVP{

    private ajoutBDDVP majoutAsyncTask;

    String accelerationEditText;
    String vitesseEditText;
    Boolean directionSwitch;
    String stepsEditText;
    String frameEditText;
    String camera_numberEditText;
    String pause_between_cameraEditText;
    Boolean focus_stackingSwitch;
    Button oKButton;
    EditText idRentre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sauvegarde_programme,container,false);

        accelerationEditText = getArguments().getString("AccelerationSaveProgramme");
        vitesseEditText = getArguments().getString("VitesseSaveProgramme");
        directionSwitch = getArguments().getBoolean("DirectionSaveProgramme");
        stepsEditText = getArguments().getString("TableStepsSaveProgramme");
        frameEditText = getArguments().getString("FrameSaveProgramme");
        camera_numberEditText = getArguments().getString("CameraSaveProgramme");
        pause_between_cameraEditText = getArguments().getString("TempsEntrePhotosSaveProgramme");
        focus_stackingSwitch = getArguments().getBoolean("FocusSaveProgramme");
        oKButton = view.findViewById(R.id.sauver);
        idRentre=view.findViewById(R.id.IDrentre);

        return view;

    }
    /*
    lors de l'appuie sur le bouton ok, on enregistre les paramètres du mode programmé
     */
    @Override
    public void onStart() {
        super.onStart();

        majoutAsyncTask=new ajoutBDDVP(this);

        oKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                valeurProgramme nouvelEnregistrement = new valeurProgramme();
                nouvelEnregistrement.acceleration = accelerationEditText;
                System.out.println("nouvelle enregistrement : "+nouvelEnregistrement.acceleration);
                nouvelEnregistrement.camera_number=camera_numberEditText;
                nouvelEnregistrement.direction=directionSwitch;
                nouvelEnregistrement.frame=frameEditText;
                nouvelEnregistrement.id=idRentre.getText().toString();
                nouvelEnregistrement.speed= vitesseEditText;
                nouvelEnregistrement.tableSteps=stepsEditText;
                nouvelEnregistrement.timeBetweenPhotosNumber=pause_between_cameraEditText;
                nouvelEnregistrement.focusStacking=focus_stackingSwitch;


                majoutAsyncTask.execute(nouvelEnregistrement);

            }
        });
    }

    /*
    Check si la bdd est pleine ou si l'élément existait déja
     */
    @Override
    public void ajoutBDDvaleursP(Integer bool) {
        if(bool.equals(1)){
            Toast.makeText(getContext(),"Impossible d'ajouter, supprimez un élément", Toast.LENGTH_LONG).show();
        }else if (bool.equals(2)){
            Toast.makeText(getContext(),"Cet élément a écrasé l'élément de même nom", Toast.LENGTH_LONG).show();
        }
        getFragmentManager().beginTransaction().replace(R.id.fragment, Programme.programme).addToBackStack(null).commit();
    }
}
