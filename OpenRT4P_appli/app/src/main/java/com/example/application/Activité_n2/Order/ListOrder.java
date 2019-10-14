package com.example.application.Activité_n2.Order;

import com.example.application.Activité_n2.Fragments.Menu.Menu;

import java.util.ArrayList;
import java.util.List;

public class ListOrder {
    static public List<Order> list = new ArrayList<Order>();

    static public Order get(int i){
        return list.get(i);
    }
    static public void delete(int id){
        Order targetOrder = null;
        for (Order o: list){
            if (o.getId() == id) targetOrder = o;
        }
        if (targetOrder != null) list.remove(targetOrder);

        Menu.orderAdapter.notifyDataSetChanged();
    }
    static public Order getById(int id){
        Order targetOrder = null;
        for (Order o: list){
            if (o.getId() == id) targetOrder = o;
        }
        return targetOrder;
    }
}
