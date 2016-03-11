package com.nacho.jugsgraphic.GarrafasEngine;

/**
 * Created by nacho on 8/19/15.
 */
public abstract class JugsNode {
    //static void initializeStaticValues(int[] caps) {}
    StringBuilder ruta;

    abstract void llenar(int i);
    abstract void vaciar(int i);
    abstract void volcarEn(int i, int j);
    public abstract int pruebaMeta();
    public String getRoute() {
        return this.ruta.toString();
    }
    public int[] getContent() {return null;}
    public String nodoTexto() { return "";}
    //abstract void compilaRuta();
}
