package com.example.application.Activité_n2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.application.Activité_n2.Fragments.Menu.Menu;
import com.example.application.R;

/*
Lancement de cette activité où des fragments seront ajoutés, supprimés, remplacés sur cette activité
Cette activité sert juste à lancer LE layout 'activity_main' où les fragments seront présents
dont le fragment par défaut : Peripherique (cf fragment Périphériques)
 */

public class MainActivity extends AppCompatActivity {

    private Menu menu = new Menu();
    private static Context sContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext = getApplicationContext();


        setContentView(R.layout.activity_main);


    }

    public static Context getContext() {
        return sContext;
    }
}
