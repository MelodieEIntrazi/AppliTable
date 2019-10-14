package com.example.application.Activité_n2.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.application.Activité_n2.Instructions.Instruction;
import com.example.application.Activité_n2.Instructions.InstructionCamera;
import com.example.application.Activité_n2.Instructions.InstructionMoteur;
import com.example.application.Activité_n2.Order.ListOrder;
import com.example.application.R;

import java.util.List;

public class InstructionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Instruction> instructionList;
    Context context;
    LayoutInflater inflater;

    public InstructionAdapter(Context c, List<Instruction> instructionList) {
        context=c;
        this.instructionList = instructionList;
        inflater = LayoutInflater.from(context);
    }

    class InstructionMoteurHolder extends RecyclerView.ViewHolder{
        public int id;
        public TextView commandeTextMoteur;
        public TextView instructionTextMoteur;
        public TextView vitesseTextMoteur;
        public TextView stepsTextMoteur;
        public Button etatMoteur;

        public InstructionMoteurHolder(View v) {
            super(v);
            commandeTextMoteur = v.findViewById(R.id.commandeTextMoteur);
            instructionTextMoteur = v.findViewById(R.id.InstructionTextMoteur);
            vitesseTextMoteur = v.findViewById(R.id.VitesseTextMoteur);
            stepsTextMoteur = v.findViewById(R.id.StepsTextMoteur);
            etatMoteur = v.findViewById(R.id.etatButton);
        }
    }

    class InstructionCameraHolder extends RecyclerView.ViewHolder{

        public int id;
        public TextView commandeTextCamera;
        public TextView instructionTextCamera;
        public TextView numberPhotoTextCamera;
        public TextView pauseTextCamera;
        public Button etatInstructionCamera;

        public InstructionCameraHolder(View v) {
            super(v);
            commandeTextCamera = v.findViewById(R.id.commandeTextCamera);
            instructionTextCamera = v.findViewById(R.id.instructionTextCamera);
            numberPhotoTextCamera = v.findViewById(R.id.numberPhotoTextCamera);
            pauseTextCamera = v.findViewById(R.id.PauseTextCamera);
            etatInstructionCamera = v.findViewById(R.id.Etat_InstructionCamera);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (instructionList.get(position).getType().equals("InstructionMoteur")){
            return 0;
        }
        else if (instructionList.get(position).getType().equals("InstructionCamera")){
            return 1;
        }
        else{
            return 2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch(i){
            case 0:
                return new InstructionMoteurHolder(LayoutInflater.from(context).inflate(R.layout.instruction_moteur_liste,viewGroup,false));
            case 1:
                return new InstructionCameraHolder(LayoutInflater.from(context).inflate(R.layout.instruction_camera_liste,viewGroup,false));
            default :
                return new InstructionMoteurHolder(LayoutInflater.from(context).inflate(R.layout.instruction_moteur_liste,viewGroup,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        switch (viewHolder.getItemViewType()) {
            case 0:
                InstructionMoteurHolder instructionMoteurHolder = (InstructionMoteurHolder) viewHolder;
                InstructionMoteur instructionMoteur = (InstructionMoteur)( instructionList.get(i));

                instructionMoteurHolder.commandeTextMoteur.setText("Commande n° "+Integer.toString(instructionMoteur.getIdCommande()));
                instructionMoteurHolder.instructionTextMoteur.setText("Instruction n° "+Integer.toString(instructionMoteur.getIdInstruction()));
                instructionMoteurHolder.vitesseTextMoteur.setText("Vitesse : "+Integer.toString(instructionMoteur.getVitesse())+"pas/s");
                instructionMoteurHolder.stepsTextMoteur.setText("Nombre de pas : "+Integer.toString(instructionMoteur.getStepsTime()));

                if (instructionMoteur.termine==2){
                    instructionMoteurHolder.etatMoteur.setBackgroundColor(Color.GREEN);
                }else if(instructionMoteur.termine==1){
                    instructionMoteurHolder.etatMoteur.setBackgroundColor(Color.rgb(255,128,0));
                }else if (instructionMoteur.termine==0){
                    instructionMoteurHolder.etatMoteur.setBackgroundColor(Color.RED);
                }
                break;
            case 1:
                InstructionCameraHolder instructionCameraHolder = (InstructionCameraHolder) viewHolder;
                InstructionCamera instructionCamera = (InstructionCamera) ( instructionList.get(i));

                instructionCameraHolder.commandeTextCamera.setText("Commande n° "+Integer.toString(instructionCamera.getIdCommande()));
                instructionCameraHolder.instructionTextCamera.setText("Instruction n° "+Integer.toString(instructionCamera.getIdInstruction()));
                instructionCameraHolder.numberPhotoTextCamera.setText("Nombre de photos  "+Integer.toString(instructionCamera.getFrame()));
                instructionCameraHolder.pauseTextCamera.setText("Pause : "+Integer.toString(instructionCamera.getPause()));

                if (instructionCamera.termine==2){
                    instructionCameraHolder.etatInstructionCamera.setBackgroundColor(Color.GREEN);
                }else if(instructionCamera.termine==1){
                    instructionCameraHolder.etatInstructionCamera.setBackgroundColor(Color.rgb(255,128,0));
                }else if (instructionCamera.termine==0){
                    instructionCameraHolder.etatInstructionCamera.setBackgroundColor(Color.RED);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (instructionList == null){
            return 0;
        }
        else if ( instructionList.size() == 0){
            return 0;
        }
        else{
            return ListOrder.getById(instructionList.get(0).getIdCommande()).listInstruction.size();
        }

    }
}
