package com.example.application.Activité_n2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.Activité_n2.Fragments.Charger_Bdd.BddProgramme;
import com.example.application.Activité_n2.Fragments.Charger_Bdd.BddTempsReel;
import com.example.application.Activité_n2.Interface.SelectionProgramme;
import com.example.application.Activité_n2.Interface.SelectionReel;
import com.example.application.Activité_n2.MainActivity;
import com.example.application.Activité_n2.SupppressionBDD.SuppressionBDDP;
import com.example.application.Activité_n2.SupppressionBDD.SuppressionBDDR;
import com.example.application.R;
import com.example.application.objets.valeurProgramme;
import com.example.application.objets.valeurReel;

import java.util.List;

public class ValeurReelAdapter extends BaseAdapter implements View.OnClickListener {
    List<valeurReel> mListeVR;
    private LayoutInflater mInflater;
    private SelectionReel mListener;
    private SuppressionBDDR mBDDAsyncTask;

    public void setmListener(SelectionReel mListener) {
        this.mListener = mListener;
    }

    public ValeurReelAdapter(List<valeurReel> ListeVR) {
        this.mListeVR=ListeVR;
        mInflater= LayoutInflater.from(BddTempsReel.bddTempsReel.getContext());
        mBDDAsyncTask=new SuppressionBDDR();

    }

    @Override
    public int getCount() {
        return null != mListeVR ? mListeVR.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null != mListeVR ? mListeVR.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getViewUglyWay(int position, View convertView, ViewGroup parent) {

        final View view = mInflater.inflate(R.layout.valeur_reel_adapter, null);
        final valeurReel valeurR = (valeurReel) getItem(position);

        final TextView id = view.findViewById(R.id.idReel);
        id.setText(valeurR.id);
        final TextView speed = view.findViewById(R.id.speedReel);
        speed.setText(valeurR.speed);
        final TextView acceleration = view.findViewById(R.id.accelerationReel);
        acceleration.setText(valeurR.acceleration);
        final TextView nbRotation = view.findViewById(R.id.rotationReel);
        nbRotation.setText(valeurR.rotationNumber);
        final TextView steps = view.findViewById(R.id.stepsReel);
        steps.setText(valeurR.tableSteps);

        final Switch direction = view.findViewById(R.id.directionReel);

            direction.setChecked(valeurR.direction);
            direction.setClickable(false);


        final Switch choixRotation = view.findViewById(R.id.choixRotationReel);

            direction.setChecked(valeurR.rotationMode);
            direction.setClickable(false);

        final Button selection = view.findViewById(R.id.okReel);
        final Button deleteButton = view.findViewById(R.id.deleteReel);
        return view;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewBestWay(position,convertView,parent);
    }

    private View getViewBestWay(int position, View convertView, ViewGroup parent) {
        ViewHolderReel holder;
        if (null==convertView){
            convertView= mInflater.inflate(R.layout.valeur_reel_adapter,null);
            holder=new ViewHolderReel(convertView);
            convertView.setTag(holder);
        } else{
            holder=(ViewHolderReel) convertView.getTag();
        }

        final valeurReel valeurR = (valeurReel) getItem(position);

        holder.id.setText(valeurR.id);
        holder.acceleration.setText(valeurR.acceleration);
        holder.nbRotation.setText(valeurR.rotationNumber);
        holder.speed.setText(valeurR.speed);
        holder.steps.setText(valeurR.tableSteps);

            holder.direction.setChecked(valeurR.direction);
            holder.direction.setClickable(false);


            holder.rotationMode.setChecked(valeurR.rotationMode);
            holder.rotationMode.setClickable(false);

        holder.selection.setTag(position);
        holder.selection.setOnClickListener(this);
        holder.suppression.setTag(position);
        holder.suppression.setOnClickListener(this);

        return convertView;

    }

    @Override
    public void onClick(View v) {
        int position =(Integer) v.getTag();
        final valeurReel valeurR = (valeurReel) getItem(position);

        switch (v.getId()){
            case R.id.deleteReel :
                Toast.makeText(MainActivity.getContext(), "Suppression", Toast.LENGTH_LONG).show();
                if(null!=mListener){
                    mBDDAsyncTask.execute(valeurR);
                    mListener.onDelete();
                }
                break;
            case R.id.okReel:
                Toast.makeText(MainActivity.getContext(), "Selection", Toast.LENGTH_LONG).show();
                if(null!=mListener){
                    mListener.onSelection(valeurR);}
                break;
        }

    }
}
