package com.example.application.Activité_n1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.application.Activité_n1.Bluetooth.Peripherique;
import com.example.application.Activité_n1.Bluetooth.PeripheriqueAdapter;
import com.example.application.Activité_n1.RecyclerView.RecyclerTouch;
import com.example.application.Activité_n2.MainActivity;
import com.example.application.R;

import java.util.ArrayList;
import java.util.Set;

/*
Activité 1 ayant pour but de choisir le bon périphérique parmi une liste de périphériques connus appareillé en bluetooth
Lorsque l'on click sur le bon périphérique, il s'affiche au dessus du bouton 'connecter' et on peut appuyer ainsi sur ce bouton pour
aller à l'activité suivante

 */

public class Connexion extends AppCompatActivity {
    private final static int REQUEST_CODE_ENABLE_BLUETOOTH = 0;
    private Set<BluetoothDevice> devices;
    private BluetoothAdapter adaptateurBluetooth;
    private BroadcastReceiver bluetoothReceiver;
    private ArrayList<Peripherique> peripheriques;
    private Peripherique peripherique;
    private ArrayList<String> noms;
    private Handler handler;

    private Button btnConnecter;
    private RecyclerView listePeripheriques;

    private TextView peripheriqueText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        peripheriqueText = findViewById(R.id.textPeripheriqueName);
        btnConnecter = findViewById(R.id.btnConnecter);

        btnConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click sur connecter");
                peripherique.connecter();
                while(!peripherique.isConnected);
                Peripherique.peripherique = peripherique;
                Intent intent = new Intent(Connexion.this, MainActivity.class);
                startActivity(intent);
            }
        });

        adaptateurBluetooth = BluetoothAdapter.getDefaultAdapter();
        if (adaptateurBluetooth == null)
        {
            Toast.makeText(getApplicationContext(), "Bluetooth non activé !", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (!adaptateurBluetooth.isEnabled())
            {
                Toast.makeText(getApplicationContext(), "Bluetooth non activé !", Toast.LENGTH_SHORT).show();
                Intent activeBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(activeBlueTooth, REQUEST_CODE_ENABLE_BLUETOOTH);
                //bluetoothAdapter.enable();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Bluetooth activé", Toast.LENGTH_SHORT).show();

                // Recherche des périphériques connus
                peripheriques = new ArrayList<>();
                noms = new ArrayList<>();
                devices = adaptateurBluetooth.getBondedDevices();
                for (BluetoothDevice blueDevice : devices)
                {
                    //Toast.makeText(getApplicationContext(), "Périphérique = " + blueDevice.getName(), Toast.LENGTH_SHORT).show();
                    peripheriques.add(new Peripherique(blueDevice, handler));
                    noms.add(blueDevice.getName());
                }

                if(peripheriques.size() == 0)
                    peripheriques.add(new Peripherique(null, handler));
                if(noms.size() == 0)
                    noms.add("Aucun");


                System.out.println(peripheriques.size());

                listePeripheriques = (RecyclerView) findViewById(R.id.bluetoothList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                listePeripheriques.setLayoutManager(layoutManager);
                listePeripheriques.setItemAnimator( new DefaultItemAnimator());
                final PeripheriqueAdapter peripheriqueAdapter = new PeripheriqueAdapter(this, peripheriques);
                listePeripheriques.setAdapter(peripheriqueAdapter);

                listePeripheriques.addOnItemTouchListener(new RecyclerTouch(this, listePeripheriques, new RecyclerTouch.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        peripherique = peripheriques.get(position);
                        peripheriqueText.setText(peripherique.getNom());
                    }
                    @Override
                    public void onLongClick(View view, int position) {
                        peripherique = peripheriques.get(position);
                        peripheriqueText.setText(peripherique.getNom());
                    }
                }));
            }
        }
    }

    /*
    Cette fonction permet d'afficher une notification lorsque le bluetooth du téléphone est activé ou non
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE_ENABLE_BLUETOOTH)
            return;
        if (resultCode == RESULT_OK)
        {
            Toast.makeText(getApplicationContext(), "Bluetooth activé", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Bluetooth non activé !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (adaptateurBluetooth != null){
            adaptateurBluetooth.cancelDiscovery();
        }
        if (bluetoothReceiver != null){
            unregisterReceiver(bluetoothReceiver);
        }
    }
}

