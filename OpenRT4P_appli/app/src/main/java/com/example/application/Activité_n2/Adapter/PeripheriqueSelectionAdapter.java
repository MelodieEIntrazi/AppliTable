package com.example.application.Activité_n2.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.application.Activité_n2.Fragments.Peripheriques.Peripherique;
import com.example.application.R;

import java.util.ArrayList;

public class PeripheriqueSelectionAdapter extends RecyclerView.Adapter<PeripheriqueSelectionAdapter.PeripheriqueHolder>  {
    public Context context;
    public ArrayList<Peripherique> listPeripheriques;
    LayoutInflater inflater;

    public PeripheriqueSelectionAdapter(Context c, ArrayList<Peripherique> listPeripheriques) {
        context = c;
        this.listPeripheriques = listPeripheriques;
        inflater = LayoutInflater.from(context);
    }

    public class PeripheriqueHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textPeripherique;
        public Switch switchPeripherique;
        public int indice;

        public PeripheriqueHolder(View v) {
            super(v);
            indice=getAdapterPosition();
            imageView = v.findViewById(R.id.imageView);
            textPeripherique = v.findViewById(R.id.textPeripheriqueListe);
            switchPeripherique = v.findViewById(R.id.switchPeripheriqueListe);

            switchPeripherique.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("indice : "+getAdapterPosition());
                    listPeripheriques.get(getAdapterPosition()).connecte=!listPeripheriques.get(getAdapterPosition()).connecte;
                    System.out.println(listPeripheriques.get(12).isConnecte());
                }
            });
            /*
            switchPeripherique.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    listPeripheriques.get(indice).setConnecte(isChecked);
                }
            });*/
        }
    }


    @Override
    public PeripheriqueHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PeripheriqueHolder(LayoutInflater.from(context).inflate(R.layout.peripherique_selection_liste,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(final PeripheriqueHolder v, int i) {
            System.out.println("i : "+i);
            System.out.println("indice : "+v.getAdapterPosition());
            v.switchPeripherique.setChecked(listPeripheriques.get(v.getAdapterPosition()).connecte);
            v.indice=v.getAdapterPosition();
            v.textPeripherique.setText(listPeripheriques.get(v.getAdapterPosition()).getNom());
            if (listPeripheriques.get(v.getAdapterPosition()).getNom().contains("Moteur")){
                v.imageView.setImageResource(R.drawable.moteur);
            }else if (listPeripheriques.get(v.getAdapterPosition()).getNom().contains("Camera")){
                v.imageView.setImageResource(R.drawable.camera);
            }

    }

    @Override
    public int getItemCount() {
        return listPeripheriques.size();
    }
}
