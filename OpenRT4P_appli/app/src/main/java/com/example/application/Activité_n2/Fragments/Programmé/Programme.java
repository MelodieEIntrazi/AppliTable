package com.example.application.Activité_n2.Fragments.Programmé;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.application.Activité_n1.Bluetooth.Peripherique;
import com.example.application.Activité_n2.Fragments.Charger_Bdd.BddProgramme;
import com.example.application.Activité_n2.Fragments.Focus.FocusParametre;
import com.example.application.Activité_n2.Fragments.Menu.Menu;
import com.example.application.Activité_n2.Fragments.SauvegardeBDD.SauvegardeProgramme;
import com.example.application.Activité_n2.Order.ListOrder;
import com.example.application.Activité_n2.Order.ProgrammeOrder;
import com.example.application.R;

/**
Fragment utilisé pour le mode programmé
 */

public class Programme extends Fragment  {

    private Peripherique peripherique;

    String data;
    public int accelerationInt;
    public int vitesseInt;
    public int stepsInt;
    public int frameInt;
    public int camera_numberInt;
    public int pause_between_cameraInt;
    static public Button parametrage;
    static public Switch focus_stackingSwitch;

    SauvegardeProgramme Sauv_frag = new SauvegardeProgramme();


    static public Programme programme = new Programme();

    public Programme() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_programme, container, false);

        peripherique=Peripherique.peripherique;

        final Button save = v.findViewById(R.id.save_programme);
        final Button send = v.findViewById(R.id.send_programme);
        final Button charger = v.findViewById(R.id.charger);
        final EditText accelerationEditText = v.findViewById(R.id.AccelerationProgramme);
        final EditText vitesseEditText = v.findViewById(R.id.VitesseProgramme);
        final Switch directionSwitch = v.findViewById(R.id.DirectionProgramme);
        final EditText stepsEditText = v.findViewById(R.id.StepsProgramme);
        final EditText frameEditText = v.findViewById(R.id.FrameProgramme);
        final EditText camera_numberEditText = v.findViewById(R.id.Camera_Number_Programme);
        final EditText pause_between_cameraEditText = v.findViewById(R.id.Pause_between_camera_Programme);


        focus_stackingSwitch = v.findViewById(R.id.Focus_stacking_Programme);
        parametrage = v.findViewById(R.id.parametrage);

        /*
        Permet de recuperer les infos de la bdd présent dans le fragment charger_Bdd
         */
        if(getArguments()!=null){
            final String speed = getArguments().getString("vitesse");
            vitesseEditText.setText(speed);
            final String acceleration = getArguments().getString("acceleration");
            accelerationEditText.setText(acceleration);
            final String steps = getArguments().getString("tableSteps");
            stepsEditText.setText(steps);
            final String tempsPhotos = getArguments().getString("tempsEntrePhotos");
            pause_between_cameraEditText.setText(tempsPhotos);
            final String frame = getArguments().getString("frame");
            frameEditText.setText(frame);
            final String camera = getArguments().getString("camera");
            camera_numberEditText.setText(camera);
            directionSwitch.setChecked(getArguments().getBoolean("direction"));
            focus_stackingSwitch.setChecked(getArguments().getBoolean("focus"));
        }

        if (focus_stackingSwitch.isChecked()){
            parametrage.setVisibility(View.VISIBLE);
        }else{
            parametrage.setVisibility(View.INVISIBLE);
        }

        /*
        switch du focus stacking
         */
        focus_stackingSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (focus_stackingSwitch.isChecked()){
                    parametrage.setVisibility(View.VISIBLE);
                }else{
                    parametrage.setVisibility(View.INVISIBLE);
                }
            }
        });

        /*
        parametre utilisé pour le focus stacking et ouvre sur un nouveau fragment
         */
        parametrage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parametrage.setVisibility(View.VISIBLE);
                getFragmentManager().beginTransaction().add(R.id.fragment, FocusParametre.focusParametre).addToBackStack(null).commit();
            }
        });


        /*
        Permet de lancer le fragment charger et de récuperer des informations présent dans la bdd
         */
        charger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment, BddProgramme.bddProgramme).addToBackStack(null).commit();
            }
        });

        /*
        Permet de le fragment 'sauvegarder' les informations du mode Programmé
         */
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle bundle = new Bundle();
                bundle.putString("AccelerationSaveProgramme", accelerationEditText.getText().toString());
                bundle.putString("VitesseSaveProgramme", vitesseEditText.getText().toString());
                bundle.putBoolean("DirectionSaveProgramme", directionSwitch.isChecked());
                bundle.putString("TableStepsSaveProgramme", stepsEditText.getText().toString());
                bundle.putString("FrameSaveProgramme", frameEditText.getText().toString());
                bundle.putString("CameraSaveProgramme", camera_numberEditText.getText().toString());
                bundle.putString("TempsEntrePhotosSaveProgramme", pause_between_cameraEditText.getText().toString());
                bundle.putBoolean("FocusSaveProgramme", focus_stackingSwitch.isChecked());
                Sauv_frag.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.fragment, Sauv_frag ).addToBackStack(null).commit();
            }

        });

        /*
        Envoie les informations du mode programmé au boitier de commande
         */
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accelerationInt=Integer.parseInt(accelerationEditText.getText().toString());
                vitesseInt=Integer.parseInt(vitesseEditText.getText().toString());
                stepsInt=Integer.parseInt(stepsEditText.getText().toString());
                frameInt=Integer.parseInt(frameEditText.getText().toString());
                camera_numberInt=Integer.parseInt(camera_numberEditText.getText().toString());
                pause_between_cameraInt=Integer.parseInt(pause_between_cameraEditText.getText().toString());

                ProgrammeOrder programmeOrder = new ProgrammeOrder(accelerationInt,vitesseInt,
                        directionSwitch.isChecked(),stepsInt,frameInt,camera_numberInt,pause_between_cameraInt,focus_stackingSwitch.isChecked());
                ListOrder.list.add(programmeOrder);

                Menu.orderAdapter.notifyDataSetChanged();

                data="";
                data+=programmeOrder.getId()+",";
                data+="0"+",";
                data+=Integer.toString(accelerationInt)+",";
                data+=Integer.toString(vitesseInt)+",";
                data+=Integer.toString(stepsInt)+",";

                if (directionSwitch.isChecked()){
                    data+="1"+",";
                }
                else{
                    data+="0"+",";
                }

                data+="-1"+","; //choix rotation
                data+="-1"+","; //rotation number

                data+=Integer.toString(frameInt)+",";
                data+=Integer.toString(camera_numberInt)+",";
                data+=Integer.toString(pause_between_cameraInt)+",";
                System.out.println(focus_stackingSwitch.isChecked());
                if (focus_stackingSwitch.isChecked()){

                    data+=Integer.toString((FocusParametre.cameraAdapter.nombrePhotoFocus+1));
                }
                else{
                    data+="0";
                }

                System.out.println(data);

                peripherique.envoyer(data);
                getFragmentManager().beginTransaction().remove(Programme.programme).addToBackStack(null).commit();
                focus_stackingSwitch.setChecked(false);

            }
        });

        return v;

    }


}
