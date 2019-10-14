package com.example.application.Activité_n2.Order;

public class ProgrammeOrder extends Order {

    private int acceleration;
    private int vitesse;
    private boolean direction;
    private int nombre_de_pas_table;
    private int nombre_de_prise;
    private int nombre_de_camera;
    private int temps_pause_entre_photos;
    private boolean focus_stacking;


    public ProgrammeOrder(int acceleration,int vitesse, boolean direction, int nombre_de_pas_table, int nombre_de_prise, int nombre_de_camera,
                          int temps_pause_entre_photos, boolean focus_stacking) {

        super();
        this.acceleration = acceleration;
        this.vitesse = vitesse;
        this.direction = direction;
        this.nombre_de_pas_table = nombre_de_pas_table;
        this.nombre_de_prise = nombre_de_prise;
        this.nombre_de_camera = nombre_de_camera;
        this.temps_pause_entre_photos = temps_pause_entre_photos;
        this.focus_stacking = focus_stacking;

    }

    public boolean getFocus_stacking() {
        return focus_stacking;
    }

    public int getNombre_de_camera() {
        return nombre_de_camera;
    }

    public int getNombre_de_prise() {
        return nombre_de_prise;
    }

    @Override
    public String createDatagramme(){
        return "";
    }
}
