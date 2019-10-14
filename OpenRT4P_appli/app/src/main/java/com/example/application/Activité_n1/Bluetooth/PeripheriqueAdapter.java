package com.example.application.Activité_n1.Bluetooth;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.application.R;

import java.util.ArrayList;
import java.util.List;


/*
Il s'agit d'un 'adapter' pour avoir l'affichage souhaité des différents périphériques appairés au téléphone
 */

public class PeripheriqueAdapter extends RecyclerView.Adapter<PeripheriqueAdapter.MyViewHolder> {

    List<Peripherique> peripheriquesList;
    ArrayList<Peripherique> peripheriquesArray = new ArrayList<Peripherique>();
    Context context;
    LayoutInflater inflater;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView nameText;

        MyViewHolder(View v){
            super(v);
            nameText = v.findViewById(R.id.textName);
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_peripherique,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    public PeripheriqueAdapter(Context c,List<Peripherique> peripheriques) {
        context=c;
        this.peripheriquesList = peripheriques;
        inflater = LayoutInflater.from(context);
        this.peripheriquesArray.addAll(peripheriques);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Peripherique peripherique = peripheriquesList.get(i);
        myViewHolder.nameText.setText(peripherique.getNom());
    }

    @Override
    public int getItemCount() {
        return peripheriquesList.size();
    }
}
