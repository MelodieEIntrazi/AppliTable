package com.example.application.Activité_n2.Instructions;

public class InstructionMoteur extends Instruction {

    private int acceleration;
    private int vitesse;
    private int direction;
    private int choixRotation;
    private int stepsTime;


    public InstructionMoteur(int idCommande,int idInstruction,int acceleration,int vitesse,int direction,int choixRotation,int stepsTime) {

        super();
        this.idCommande=idCommande;
        this.idInstruction=idInstruction;
        this.acceleration=acceleration;
        this.vitesse=vitesse;
        this.direction=direction;
        this.choixRotation=choixRotation;
        this.stepsTime=stepsTime;
    }

    @Override
    public String createDatagramme() {
        return null;
    }

    @Override
    public int getIdCommande() {
        return idCommande;
    }

    @Override
    public int getIdInstruction() {
        return idInstruction;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public int getVitesse() {
        return vitesse;
    }

    public int getDirection() {
        return direction;
    }

    public int getChoixRotation() {
        return choixRotation;
    }

    public int getStepsTime() {
        return stepsTime;
    }
}
