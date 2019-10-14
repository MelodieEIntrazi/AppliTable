package com.example.application.Activité_n2.Instructions;

public abstract class Instruction {
    static private int lastid=0;
    protected int idCommande;
    protected int idInstruction;
    public int termine = 0;

    Instruction(){
    }

    public String getType(){
        return this.getClass().getSimpleName();
    }

    public abstract String createDatagramme();

    public int getIdInstruction() {
        return idInstruction;
    }

    public int getIdCommande() { return idCommande; }


}
