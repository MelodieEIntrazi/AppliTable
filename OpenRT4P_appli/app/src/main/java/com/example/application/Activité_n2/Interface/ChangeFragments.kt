package com.example.application.Activité_n2.Interface

import androidx.fragment.app.Fragment

//L'interface implémentée  dans la mainActivity pour changer de fragment
interface ChangeFragments {
    fun onChangeFragment(fragment: Fragment)
}