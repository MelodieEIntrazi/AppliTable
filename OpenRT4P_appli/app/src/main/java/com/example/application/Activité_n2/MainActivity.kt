package com.example.application.Activité_n2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.application.Activité_n1.Bluetooth.Peripherique
import com.example.application.Activité_n1.Connexion
import com.example.application.Activité_n2.Interface.ChangeFragments
import com.example.application.R
import kotlinx.android.synthetic.main.activity_main.*

/*
Lancement de cette activité où des fragments seront ajoutés, supprimés, remplacés sur cette activité
Cette activité sert juste à lancer LE layout 'activity_main' où les fragments seront présents
dont le fragment par défaut : Peripherique (cf fragment Périphériques)
 */
class MainActivity : AppCompatActivity(), ChangeFragments {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = applicationContext
        listener = this
        setContentView(R.layout.activity_main)
        val menu: com.example.application.Activité_n2.Fragments.Menu.Menu = com.example.application.Activité_n2.Fragments.Menu.Menu.menu
        addFragment(menu, R.id.fragment)
        setSupportActionBar(toolbar)

        // Now get the support action bar
        actionBars = supportActionBar

        // Set toolbar title/app title
        actionBars!!.title = "Photogrammétrie"

        // Set action bar/toolbar sub title
        actionBars!!.subtitle = Peripherique.peripherique?.nom

        // Set action bar elevation
        actionBars!!.elevation = 4.0F

        // Display the app icon in action bar/toolbar
        actionBars!!.setDisplayShowHomeEnabled(true)
        actionBars!!.setDisplayHomeAsUpEnabled(true)
        actionBars!!.setLogo(R.mipmap.ic_launcher)
        actionBars!!.setDisplayUseLogoEnabled(true)
    }

    override fun onStart() {
        Peripherique.peripherique?.let {
            try {
                it.envoyer("0,7,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0")
            } catch (e: KotlinNullPointerException) {
                println("Error : " + e.message.toString())
                Toast.makeText(context, "Veuillez connecter la table ", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Connexion::class.java)
                startActivity(intent)
                finish()

            }

        }


        super.onStart()
    }
    companion object {
        var listener: ChangeFragments? = null
        var actionBars: ActionBar? = null
        var context: Context? = null
            private set
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    private fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }


    private fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { replace(frameId, fragment) }
    }


    override fun onChangeFragment(fragment: Fragment) {
        this.replaceFragment(fragment = fragment, frameId = R.id.fragment)
    }

    override fun onDestroy() {
        Peripherique.peripherique!!.deconnecter()
        Peripherique.peripherique = null
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.deconnection_menu -> {
                val intent = Intent(this@MainActivity, Connexion::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        replaceFragment(com.example.application.Activité_n2.Fragments.Menu.Menu.menu, R.id.fragment)
        return super.onSupportNavigateUp()
    }
}