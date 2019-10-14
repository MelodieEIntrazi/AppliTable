package com.example.application.Activité_n2.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.application.Activité_n2.Fragments.Focus.FocusParametre;
import com.example.application.R;

import java.util.ArrayList;
import java.util.List;

public class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.CameraHolder> {

    public int numeroCamera;
    public int nombrePhotoFocus;
    public List<Integer> nombreDePas = new ArrayList<>();

    Context context;
    LayoutInflater inflater;

    public CameraAdapter(Context c, int nombreDePas[]) {
        context=c;
        this.nombreDePas.clear();
        for (int i=0; i<9; i++){
            this.nombreDePas.add(nombreDePas[i]);
        }
        inflater = LayoutInflater.from(context);
    }

    class CameraHolder extends RecyclerView.ViewHolder{

        public int position;
        public TextView nombreDePasText;
        public EditText nombreDePasEditText;

        public CameraHolder(View v){
            super(v);
            nombreDePasText = v.findViewById(R.id.nombreDePasText);
            nombreDePasEditText = v.findViewById(R.id.nombreDePasEditText);

        }
    }

    @Override
    public CameraHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CameraAdapter.CameraHolder(LayoutInflater.from(context).inflate(R.layout.parametre_camera_list,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(final CameraHolder cameraHolder, int i) {
        cameraHolder.position=i;
        cameraHolder.nombreDePasText.setText("Nombre de pas entre la photo "+Integer.toString(i+1)+" et "+Integer.toString(i+2));
        System.out.println(Integer.toString(nombreDePas.get(i)));
        cameraHolder.nombreDePasEditText.setText(Integer.toString(FocusParametre.cameraList.get(numeroCamera).param[i] ));
        cameraHolder.nombreDePasEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(""))return;
                FocusParametre.cameraList.get(numeroCamera).param[cameraHolder.position] = Integer.parseInt(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return nombrePhotoFocus;
    }
}
