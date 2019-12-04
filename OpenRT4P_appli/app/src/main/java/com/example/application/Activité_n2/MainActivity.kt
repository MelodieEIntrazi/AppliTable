package com.example.application.Activité_n2

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.application.R

/*
Lancement de cette activité où des fragments seront ajoutés, supprimés, remplacés sur cette activité
Cette activité sert juste à lancer LE layout 'activity_main' où les fragments seront présents
dont le fragment par défaut : Peripherique (cf fragment Périphériques)
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = applicationContext
        setContentView(R.layout.activity_main)
    }

    companion object {
        var context: Context? = null
            private set
    }
}