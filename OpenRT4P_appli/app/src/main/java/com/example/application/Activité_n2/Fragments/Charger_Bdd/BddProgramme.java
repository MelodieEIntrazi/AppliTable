package com.example.application.Activité_n2.Fragments.Charger_Bdd;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.application.Activité_n2.Adapter.ValeurProgrammeAdapter;
import com.example.application.Activité_n2.ChargementBDD.chargementBDDVP;
import com.example.application.Activité_n2.ChargementBDD.chargmentVP;
import com.example.application.Activité_n2.Fragments.Programmé.Programme;
import com.example.application.Activité_n2.Interface.SelectionProgramme;
import com.example.application.R;
import com.example.application.objets.valeurProgramme;

import java.util.List;

/**
 Permet de charger des informations gardées en mémoire lors d'une ancienne sauvegarde
 */

public class BddProgramme extends Fragment implements chargmentVP, SelectionProgramme {

    public static BddProgramme bddProgramme = new BddProgramme();
    private chargementBDDVP mBDDAsyncTask;
    private ListView mListView;
    chargmentVP mListener2=this;

    public BddProgramme() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_bdd_programme, container, false);
        mListView = view.findViewById(R.id.ListeViewProgramme);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBDDAsyncTask=new chargementBDDVP(mListener2);
        mBDDAsyncTask.execute();
    }

    @Override
    public void chargementBDDvaleursP(List<valeurProgramme> listeVP) {
        if(listeVP.size()==0){
            Toast.makeText(getContext(),"La BDD est vide",Toast.LENGTH_LONG).show();
            final FragmentTransaction transaction = getFragmentManager().beginTransaction();
            final Programme fragment = new Programme();
            transaction.replace(R.id.fragment,fragment).addToBackStack(null).commit();
        }

        final ValeurProgrammeAdapter adapter = new ValeurProgrammeAdapter(listeVP);
        mListView.setAdapter(adapter);

        adapter.setmListener(this);

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("test1");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("nothing");
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), position, Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public Context getContext() {
        return super.getContext();
    }

    /*
    Permet de selectionner les informations concernant le mode Programmé pour les réutiliser lors du mode programmé
     */
    @Override
    public void onSelection(valeurProgramme valeurP) {
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        final Programme fragment = new Programme();
        final Bundle bundle = new Bundle();
        bundle.putString("vitesse", valeurP.speed);
        bundle.putString("acceleration", valeurP.acceleration);
        bundle.putString("tempsEntrePhotos", valeurP.timeBetweenPhotosNumber);
        bundle.putString("frame", valeurP.frame);
        bundle.putString("camera", valeurP.camera_number);
        bundle.putBoolean("direction", valeurP.direction);
        bundle.putBoolean("focus", valeurP.focusStacking);
        bundle.putString("tableSteps", valeurP.tableSteps);
        fragment.setArguments(bundle);

        transaction.replace(R.id.fragment,fragment).addToBackStack(null).commit();

    }

    /*
    Est appelé lors du click sur le bouton delete present sur le fragment et permet ainsi de revenir sur le mode programmé
     */
    @Override
    public void onDelete() {
        getFragmentManager().beginTransaction().replace(R.id.fragment, Programme.programme).addToBackStack(null).commit();
    }
}
