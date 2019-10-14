package com.example.application.Activité_n1.Bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.application.Activité_n2.Fragments.Menu.Menu;
import com.example.application.Activité_n2.Instructions.InstructionCamera;
import com.example.application.Activité_n2.Instructions.InstructionMoteur;
import com.example.application.Activité_n2.Order.ListOrder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/*
la class périphérique sert de communication entre le téléphone et le boitier de commande
Plusieurs fonctions sont présentes comme :
'envoyer' qui permet d'envoyer des informations du téléphone vers le boitier de commande
'decode' qui permet de decoder les informations reçues du boitier et un possible affichage sur le téléphone
connecter et decconecter sont appeler lors de la connexion au boitier de commande :
    avant chaque connexion, une deconnexion de sécurité est appelé puis la connexion avec le boitier de commande se fait
 */


public class Peripherique {
    public static Peripherique peripherique;


    private String nom;
    private String adresse;
    private Handler handler;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private InputStream receiveStream = null;
    private OutputStream sendStream = null;
    private TReception tReception;
    public boolean isConnected = false;


    public Peripherique(BluetoothDevice device, Handler handler)
    {
        if(device != null)
        {
            this.device = device;
            this.nom = device.getName();
            this.adresse = device.getAddress();
            this.handler = handler;
        }
        else
        {
            this.device = device;
            this.nom = "Aucun";
            this.adresse = "";
            this.handler = handler;
        }

        try
        {
            socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            receiveStream = socket.getInputStream();
            sendStream = socket.getOutputStream();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            socket = null;
        }

        if(socket != null)
            tReception = new TReception(handler);
    }

    public String getNom()
    {
        return nom;
    }

    public String toString()
    {
        return "\nNom : " + nom + "\nAdresse : " + adresse;
    }

    public void envoyer(String data)
    {
        if(socket == null)
            return;

        try
        {
            sendStream.write(data.getBytes());
            sendStream.flush();
        }
        catch (IOException e)
        {
            System.out.println("<Socket> error send");
            e.printStackTrace();
        }
    }

    public void connecter()
    {
        System.out.println("Connecter");
        new Thread()
        {
            @Override public void run()
            {
                deconnecter();
                System.out.println("connexion en cours");
                try
                {
                    try {
                        socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                        tReception = new TReception(handler);
                    }catch(Exception e){
                        e.printStackTrace();
                        return;
                    }
                    socket.connect();
                    sendStream = socket.getOutputStream();
                    receiveStream = socket.getInputStream();
                    tReception.start();


                    isConnected=true;
                    System.out.println("connexion établie");

                }
                catch (IOException e)
                {
                    System.out.println("<Socket> error connect");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public boolean deconnecter()
    {
        System.out.println("déconnexion");

        isConnected=false;

        try {
            tReception.arreter();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (receiveStream != null) {
            try {receiveStream.close();} catch (Exception e) {}
            receiveStream = null;
        }

        if (sendStream != null) {
            try {sendStream.close();} catch (Exception e) {}
            sendStream = null;
        }

        if (socket != null) {
            try {socket.close();} catch (Exception e) {}
            socket = null;
        }
        System.out.println("déconnexion réussie");
        return true;

    }



    private class TReception extends Thread {
        Handler handlerUI;
        private boolean fini;

        TReception(android.os.Handler h) {
            handlerUI = h;
            fini = false;
        }

        @Override
        public void run() {

            while (!fini) {
                try {
                    if (receiveStream.available() > 0) {
                        byte buffer[] = new byte[100];
                        int k = receiveStream.read(buffer, 0, 100);
                        if (k > 0) {
                            byte rawdata[] = new byte[k];
                            for (int i = 0; i < k; i++)
                                rawdata[i] = buffer[i];

                            String data = new String(rawdata);

                            decode(data);
                        }

                    }
                    /*try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                } catch (IOException e) {
                    //System.out.println("<Socket> error read");
                    e.printStackTrace();
                }
            }
        }

        public void arreter() {
            if (fini == false) {
                fini = true;
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        public void decode(String data) {

            final String tableauDonnees[] = data.split(",");

            System.out.println(data);
            if (tableauDonnees.length == 0) return;


            if (tableauDonnees[0].equals("fini")) {

                handlerUI = new Handler(Looper.getMainLooper());
                handlerUI.post(new Runnable() {
                    @Override
                    public void run() {
                        Menu.pauseButton.setText("PAUSE");
                    }
                });

                Menu.instructionAdapter.instructionList = null;

                handlerUI = new Handler(Looper.getMainLooper());
                handlerUI.post(new Runnable() {
                    @Override
                    public void run() {
                        ListOrder.delete(Integer.parseInt(tableauDonnees[1]));
                        Menu.view.setVisibility(View.INVISIBLE);
                        Menu.listInfos.setVisibility(View.INVISIBLE);
                        Menu.deleteButton.setVisibility(View.INVISIBLE);
                    }
                });
            } else if (tableauDonnees[0].equals("creation")) {
                int idCommande = Integer.parseInt(tableauDonnees[1]);
                int idInstruction = Integer.parseInt(tableauDonnees[2]);

                if (tableauDonnees[3].equals("moteur")) {
                    int acceleration = Integer.parseInt(tableauDonnees[4]);
                    int vitesse = Integer.parseInt(tableauDonnees[5]);
                    int direction = Integer.parseInt(tableauDonnees[6]);
                    int choixRotation = Integer.parseInt(tableauDonnees[7]);
                    int stepsTime = Integer.parseInt(tableauDonnees[8]);

                    InstructionMoteur instructionMoteur = new InstructionMoteur(idCommande, idInstruction, acceleration,
                            vitesse, direction, choixRotation, stepsTime);
                    ListOrder.getById(idCommande).listInstruction.add(instructionMoteur);

                } else if (tableauDonnees[3].contains("camera")) {

                    int pause = Integer.parseInt(tableauDonnees[5]);
                    int nombre_de_photos = Integer.parseInt(tableauDonnees[6]);

                    InstructionCamera instructionCamera = new InstructionCamera(idCommande, idInstruction, nombre_de_photos, pause);
                    ListOrder.getById(idCommande).listInstruction.add(instructionCamera);

                }
                handlerUI = new Handler(Looper.getMainLooper());
                handlerUI.post(new Runnable() {
                    @Override
                    public void run() {
                        Menu.instructionAdapter.notifyDataSetChanged();
                    }
                });


            } else if (tableauDonnees[0].equals("en cours")) {



                int idCommande = Integer.parseInt(tableauDonnees[1]);
                int idInstruction = Integer.parseInt(tableauDonnees[2]);
                if (idCommande==-1){

                }
                else{
                    if (idInstruction<ListOrder.getById(idCommande).listInstruction.size()){
                        if (idCommande>=1){
                            if (ListOrder.getById(idCommande).listInstruction.size()==1){
                                ListOrder.getById(idCommande).listInstruction.get(idInstruction-1).termine = 1;
                            }
                            else if (idInstruction > 1) {
                                ListOrder.getById(idCommande).listInstruction.get(idInstruction - 2).termine = 2;
                                ListOrder.getById(idCommande).listInstruction.get(idInstruction - 1).termine = 1;
                            }
                        }
                    }
                }


                handlerUI = new Handler(Looper.getMainLooper());
                handlerUI.post(new Runnable() {
                    @Override
                    public void run() {
                        Menu.pauseButton.setText("PAUSE");
                        Menu.instructionAdapter.notifyDataSetChanged();
                    }
                });

            } else if (tableauDonnees[0].equals("connexion")){
                handlerUI = new Handler(Looper.getMainLooper());
                handlerUI.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Menu.menu.getContext(),"CONNEXION DES PERIPHERIQUES : SUCCESS",Toast.LENGTH_LONG).show();

                    }
                });

            }

        }
    }
}