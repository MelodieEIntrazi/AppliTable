package com.example.application.Activité_n2.Order

import android.view.View
import com.example.application.Activité_n2.Fragments.Menu.Menu
import java.util.*

/*Cette Objet permet de d'utiliser des fonctions et d'implémenter la liste des ordres*/
object ListOrder {
    @JvmField
    var list: MutableList<Order> = ArrayList()

    @JvmStatic
    operator fun get(i: Int): Order {
        return list[i]
    }
    @JvmStatic
    fun delete(id: Int) {
        var targetOrder: Order? = null
        for (o in list) {
            if (o.id == id) targetOrder = o
        }
        if (targetOrder != null) list.remove(targetOrder)
        if (list.size == 0) {
            Menu.pauseButton!!.visibility = View.INVISIBLE
        }

        Menu.orderAdapter!!.notifyDataSetChanged()
    }

    @JvmStatic
    fun getById(id: Int): Order? {
        var targetOrder: Order? = null
        for (o in list) {
            if (o.id == id) targetOrder = o
        }
        return targetOrder
    }
}