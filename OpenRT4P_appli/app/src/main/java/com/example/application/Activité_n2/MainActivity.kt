package com.example.application.Activité_n2

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.application.Activité_n2.Interface.ChangeFragments
import com.example.application.R

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
    }

    companion object {
        var listener: ChangeFragments? = null
        var context: Context? = null
            private set
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }


    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { replace(frameId, fragment) }
    }

    override fun onChangeFragment(fragment: Fragment) {
        replaceFragment(fragment, R.id.fragment)
    }
}