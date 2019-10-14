package com.example.application.Activité_n2.Instructions;

public class InstructionCamera extends Instruction {


    private int frame;
    private int pause;

    public InstructionCamera(int idCommande,int idInstruction,int frame,int pause) {

        super();
        this.idCommande=idCommande;
        this.idInstruction=idInstruction;
        this.frame=frame;
        this.pause=pause;
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

    public int getFrame() {
        return frame;
    }

    public int getPause() {
        return pause;
    }
}
