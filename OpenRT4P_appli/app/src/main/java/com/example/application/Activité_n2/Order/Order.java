package com.example.application.Activité_n2.Order;

import com.example.application.Activité_n2.Instructions.Instruction;

import java.util.ArrayList;

public abstract class Order {
    static private int lastid=0;
    private int id;
    public ArrayList<Instruction> listInstruction = new ArrayList<>();

    Order(){
        id=++lastid;
    }

    public String getType(){
        return this.getClass().getSimpleName();
    }

    public abstract String createDatagramme();

    public int getId() {
        return id;
    }
}
