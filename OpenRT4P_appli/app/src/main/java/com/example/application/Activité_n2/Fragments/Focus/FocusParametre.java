package com.example.application.Activité_n2.Fragments.Focus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.application.Activité_n2.Adapter.CameraAdapter;
import com.example.application.Activité_n2.Camera.Camera;
import com.example.application.Activité_n1.Bluetooth.Peripherique;
import com.example.application.Activité_n2.Fragments.Peripheriques.PeripheriqueSelection;
import com.example.application.R;
import java.util.ArrayList;
import java.util.List;

/**
 Est appelé une fois qu'on se trouve sur le Mode programmé et qu'on selectionne le bouton paramètre
 Permet de programmer le focus stacking des différents caméra connectés au préalable
 */

public class FocusParametre extends Fragment {

    static public FocusParametre focusParametre = new FocusParametre();
    static public int compteurPas;
    static public TextView compteur;
    static public Spinner spinnerCamera;
    static boolean spinnerFirstTime=true;
    static public int numeroCamera;

    static public CameraAdapter cameraAdapter;
    static public RecyclerView listCamera;

    static public ImageButton ajoutPhotoFocus;
    static public ImageButton deletePhotoFocus;

    static public Button valider;
    static public Button reset;
    static public Button sendPhotoFocus;

    static public ArrayList<String> spinnerCameraItems = new ArrayList<String>();
    static public List<Integer> indiceCamera = new ArrayList<>();
    static public List<Camera> cameraList = new ArrayList<>();

    public FocusParametre() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_focus_parametre, container, false);


        valider = v.findViewById(R.id.sendFocus);
        reset = v.findViewById(R.id.reset);
        compteur = v.findViewById(R.id.compteur);
        compteur.setEnabled(false);
        spinnerCamera=v.findViewById(R.id.spinnerFocus);
        listCamera = v.findViewById(R.id.listCamera);
        ajoutPhotoFocus = v.findViewById(R.id.AjoutePhotoFocus);
        deletePhotoFocus=v.findViewById(R.id.DeletePhotoFocus);
        sendPhotoFocus = v.findViewById(R.id.sendPhotoFocus);

        /*
        initialisation du spinner permettant de choisir la caméra ayant le focus stacking
         */
        if (spinnerFirstTime){
            for (int i=0;i<9;i++){
                if (PeripheriqueSelection.listPeripheriques.get(i+10).isConnecte()){
                    spinnerCameraItems.add("Camera "+(i+1));
                    indiceCamera.add(i);
                }
            }

            for (int i = 0; i<9;i++){
                cameraList.add(new Camera());
            }


            spinnerFirstTime=false;
        }

        /*
        ajouter une rotation avec un maximum de 8 rotation car il y a 9 photo focus possible
         */
        ajoutPhotoFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cameraAdapter.nombrePhotoFocus<8){
                    cameraAdapter.nombrePhotoFocus++;
                    cameraAdapter.notifyDataSetChanged();
                }
            }
        });
        /*
        supprimer une rotation avec un minimum de 1 rotation possible
         */
        deletePhotoFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cameraAdapter.nombrePhotoFocus>1){
                    cameraAdapter.nombrePhotoFocus--;
                    cameraAdapter.notifyDataSetChanged();
                }
            }
        });


        /*
        initialiser la liste d'affichage des rotations de caméra
         */
        if (cameraAdapter==null){
            cameraAdapter = new CameraAdapter(getContext(),cameraList.get(0).param);
            cameraAdapter.nombrePhotoFocus=1;
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        listCamera.setLayoutManager(layoutManager);
        listCamera.setItemAnimator( new DefaultItemAnimator());
        listCamera.setAdapter(cameraAdapter);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner, spinnerCameraItems);
        adapter.setDropDownViewResource(R.layout.custom_spinner);
        spinnerCamera.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        spinnerCamera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i=0; i<9; i++){
                    cameraAdapter.nombreDePas.set(i,0);
                }
                for (int i=0; i<9; i++){
                    cameraAdapter.nombreDePas.set(i, cameraList.get(indiceCamera.get(position)).param[i]);
                }
                cameraAdapter.numeroCamera = indiceCamera.get(position);
                cameraAdapter.notifyDataSetChanged();
                numeroCamera = indiceCamera.get(position);
                compteurPas=0;
                compteur.setText(Integer.toString(compteurPas));

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        /*
        envoie les paramètres de rotation au boitier de commande qui les envera à la caméra sélectionner par le spinner
         */
        sendPhotoFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "";
                data+="-1"+",";
                data+="8"+",";
                data+=Integer.toString(numeroCamera);
                for (int i=0;i<8;i++){
                    data+=",";
                    data+=cameraList.get(numeroCamera).param[i];
                }
                Peripherique.peripherique.envoyer(data);
                Toast.makeText(getContext(),"MESSAGE ENVOYE",Toast.LENGTH_LONG).show();
            }
        });

        /*
        Sert à retourner sur le fragment mode Programmé une fois que les paramètres du focus stacking ont été choisis
         */
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(FocusParametre.focusParametre).addToBackStack(null).commit();
            }
        });

        /*
        Permet de réinitialiser les pas lorsque l'on a appuyé sur des touches du magnéto
         */
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compteurPas = 0;
                compteur.setText(Integer.toString(compteurPas));

            }
        });


        return v;
    }

}
