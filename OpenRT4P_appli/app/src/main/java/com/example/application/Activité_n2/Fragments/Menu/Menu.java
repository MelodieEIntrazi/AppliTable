package com.example.application.Activité_n2.Fragments.Menu;


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

import com.example.application.Activité_n1.Bluetooth.Peripherique;
import com.example.application.Activité_n2.Adapter.InstructionAdapter;
import com.example.application.Activité_n2.Adapter.OrderAdapter;
import com.example.application.Activité_n2.Fragments.Peripheriques.PeripheriqueSelection;
import com.example.application.Activité_n2.Fragments.Programmé.Programme;
import com.example.application.Activité_n2.Fragments.Temps_réel.TempsReel;
import com.example.application.Activité_n2.Order.ListOrder;
import com.example.application.R;

import java.util.ArrayList;

/**
Dans ce fragment, divers boutons, spinners et recyclerView sont présents :
    - le spinners sert à choisir parmi les 2 modes programmés ou tems réel
    - les boutons sont la pour 'charger', 'sauvegarder' et 'envoyer'
    - le recyclerView est la pour l'affichage des différents modes une fois les paramétres (présent dans un autre fragment) ont été choisis
 */

public class Menu extends Fragment {

    static public Menu menu = new Menu();
    private Peripherique peripherique;

    static public Spinner spinnerMode;
    static public ArrayList<String> spinnerModeItems = new ArrayList<String>();
    static boolean spinnerFirstTime=true;

    static public OrderAdapter orderAdapter;
    static public RecyclerView listOrder;

    static public InstructionAdapter instructionAdapter;
    static public RecyclerView listInfos;

    static public View view;
    static public ImageButton deleteButton;
    static public Button pauseButton;
    static public Button moduleButton;
    static public Button peripheriqueButton;

    public Menu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_menu,container,false);

        view = v.findViewById(R.id.infos);
        deleteButton=v.findViewById(R.id.deleteInfos);

        spinnerMode=v.findViewById(R.id.spinner);
        listOrder = (RecyclerView) v.findViewById(R.id.orderList);
        listInfos = (RecyclerView) v.findViewById(R.id.infosInstructions);
        pauseButton = v.findViewById(R.id.pause_menu);
        moduleButton = v.findViewById(R.id.modules_menu);
        peripheriqueButton = v.findViewById(R.id.modules_menu);
        peripherique= Peripherique.peripherique;


        //Permet de gerer la pause et d'envoyer l'information au boitier
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListOrder.list.size()!=0){
                    String data="";
                    data+="-1"+",";//id commande pas utile mais necessaire
                    data+="3";
                    if (pauseButton.getText().equals("PAUSE")){
                        pauseButton.setText("START");
                    }else{
                        pauseButton.setText("PAUSE");
                    }
                    peripherique.envoyer(data);
                }

            }
        });

        //Permet de gerer le changement de fragment entre le menu et les périphériques
        peripheriqueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment, PeripheriqueSelection.peripheriqueSelection).commit();
            }
        });

        /*
        initialise les adapteur présent dans le menu
         */
        if (orderAdapter==null){
            orderAdapter = new OrderAdapter(getContext(),ListOrder.list);
        }

        if (instructionAdapter==null){
            instructionAdapter = new InstructionAdapter(getContext(),null);
        }

        if (spinnerFirstTime){
            spinnerModeItems.add("Nouvelle Commande");
            spinnerModeItems.add("Mode Programmé");
            spinnerModeItems.add("Mode Temps Réel");

            spinnerFirstTime=false;
        }

        /*
        spinner custom pour les différents menu et permet de changer de fragments en fonction du bon spinner
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner, spinnerModeItems);
        adapter.setDropDownViewResource(R.layout.custom_spinner);
        spinnerMode.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        spinnerMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        getFragmentManager().beginTransaction().add(R.id.fragment,Programme.programme).addToBackStack(null).commit();
                        spinnerMode.setSelection(0);
                        break;
                    case 2:
                        getFragmentManager().beginTransaction().add(R.id.fragment, TempsReel.temps_reel).addToBackStack(null).commit();
                        spinnerMode.setSelection(0);
                        break;
                    default:
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /*
        adapter de la liste des commandes en fonctions des 2 menu
         */
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        listOrder.setLayoutManager(layoutManager);
        listOrder.setItemAnimator( new DefaultItemAnimator());
        listOrder.setAdapter(orderAdapter);


        /*
        Permet de quitter le container contenant les différents infos
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu.instructionAdapter.instructionList = null;
                deleteButton.setVisibility(View.INVISIBLE);
                view.setVisibility(View.INVISIBLE);
                listInfos.setVisibility(View.INVISIBLE);
            }
        });

        /*
        adapter de la liste des différentes infos recu du boitier de commande dans la fonction 'decode' de l'activité 1
         */
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        listInfos.setLayoutManager(layoutManager1);
        listInfos.setItemAnimator( new DefaultItemAnimator());
        listInfos.setAdapter(instructionAdapter);

        return v;
    }
}
