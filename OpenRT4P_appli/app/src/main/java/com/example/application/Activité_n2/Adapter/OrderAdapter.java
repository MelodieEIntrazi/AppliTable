package com.example.application.Activité_n2.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.application.Activité_n2.Fragments.Menu.Menu;
import com.example.application.Activité_n2.Order.ListOrder;
import com.example.application.Activité_n2.Order.Order;
import com.example.application.Activité_n2.Order.ProgrammeOrder;
import com.example.application.Activité_n2.Order.TempsReelOrder;
import com.example.application.R;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Order> orderList;
    ArrayList<Order> orderArray = new ArrayList<Order>();
    Context context;
    LayoutInflater inflater;

    public OrderAdapter(Context c, List<Order> orderList) {
        context=c;
        this.orderList = orderList;
        inflater = LayoutInflater.from(context);
        this.orderArray.addAll(orderList);
    }

    class ProgrammedViewHolder extends RecyclerView.ViewHolder{
        public int id;
        public TextView nombre_de_prise;
        public TextView nombre_de_camera;
        public TextView focus_stacking;
        public ImageButton delete;
        public Button infosProgramme;

        public ProgrammedViewHolder(View v) {
            super(v);
            nombre_de_prise = v.findViewById(R.id.nombre_de_prise);
            nombre_de_camera = v.findViewById(R.id.nombre_de_camera);
            focus_stacking = v.findViewById(R.id.focus_stacking);
            delete = v.findViewById(R.id.delete_programme);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListOrder.delete(id);
                    Menu.pauseButton.setText("PAUSE");
                }
            });
            infosProgramme = v.findViewById(R.id.infos_programme);
            infosProgramme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Menu.view.setVisibility(View.VISIBLE);
                    Menu.listInfos.setVisibility(View.VISIBLE);
                    System.out.println("id :"+Integer.toString(id));
                    System.out.println(ListOrder.getById(id).listInstruction);
                    Menu.instructionAdapter.instructionList = ListOrder.getById(id).listInstruction;
                    Menu.deleteButton.setVisibility(View.VISIBLE);

                    Menu.instructionAdapter.notifyDataSetChanged();

                }
            });

        }
    }

    class RealTimeViewHolder extends RecyclerView.ViewHolder {
        public int id;
        public TextView vitesse;
        public TextView direction;
        public TextView temps_tour;
        public ImageButton delete;
        public Button infosTempsReel;

        public RealTimeViewHolder(View v) {
            super(v);
            vitesse = v.findViewById(R.id.vitesse);
            direction = v.findViewById(R.id.directionProgramme);
            temps_tour = v.findViewById(R.id.temps_tour);
            delete = v.findViewById(R.id.delete_temps_reel);
            infosTempsReel = v.findViewById(R.id.infos_temps_reel);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListOrder.delete(id);
                    Menu.pauseButton.setText("PAUSE");

                }
            });

            infosTempsReel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Menu.view.setVisibility(View.VISIBLE);
                    Menu.listInfos.setVisibility(View.VISIBLE);
                    System.out.println("id :"+Integer.toString(id));
                    System.out.println(ListOrder.getById(id).listInstruction);
                    Menu.instructionAdapter.instructionList = ListOrder.getById(id).listInstruction;
                    Menu.deleteButton.setVisibility(View.VISIBLE);

                    Menu.instructionAdapter.notifyDataSetChanged();

                }
            });

        }
    }

    class DefaultViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public DefaultViewHolder(View v) {
            super(v);

            textView = v.findViewById(R.id.commandeTextCamera);
            textView.setText("Commande suivante");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= ListOrder.list.size()) return -1;
        if (ListOrder.get(position).getType().equals("ProgrammeOrder")){
            return 0;
        }
        else if (ListOrder.get(position).getType().equals("TempsReelOrder")){
            return 1;
        }
        else{

            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return (ListOrder.list.size()+1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch(i){
            case 0:
                return new ProgrammedViewHolder(LayoutInflater.from(context).inflate(R.layout.ordre_programme_liste,viewGroup,false));
            case 1:
                return new RealTimeViewHolder(LayoutInflater.from(context).inflate(R.layout.ordre_temps_reel_liste,viewGroup,false));
            default:
                return new DefaultViewHolder(LayoutInflater.from(context).inflate(R.layout.ordre_defaut_liste,viewGroup,false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()){
            case 0:
                ProgrammedViewHolder vProgrammed = (ProgrammedViewHolder) viewHolder;
                ProgrammeOrder oProgrammed = ((ProgrammeOrder)ListOrder.list.get(i));

                if (oProgrammed.getFocus_stacking()){
                    vProgrammed.focus_stacking.setText("Mode focus stacking activé");
                }
                else{
                    vProgrammed.focus_stacking.setText("Mode focus stacking désactivé");
                }
                vProgrammed.nombre_de_camera.setText(Integer.toString(oProgrammed.getNombre_de_camera()) + " caméra(s)");
                vProgrammed.nombre_de_prise.setText(Integer.toString(oProgrammed.getNombre_de_prise()) + " prise(s) de vue(s)");
                vProgrammed.id = oProgrammed.getId();
                break;

            case 1:
                RealTimeViewHolder vRealTime = (RealTimeViewHolder) viewHolder;
                TempsReelOrder oRealTime = (TempsReelOrder)ListOrder.get(i);

                vRealTime.vitesse.setText(Integer.toString(oRealTime.getVitesse()) + " pas/s");
                if (oRealTime.isDirection()){
                    vRealTime.direction.setText("Rotation en sens horaire");
                }
                else vRealTime.direction.setText("Rotation en sens antihoraire");

                if (oRealTime.isTimeMode()){
                    vRealTime.temps_tour.setText(Integer.toString(oRealTime.getRotation_number()) + " s");
                }
                else vRealTime.temps_tour.setText(Integer.toString(oRealTime.getRotation_number()) + " tour(s)");

                vRealTime.id = oRealTime.getId();
                break;

        }
    }
}