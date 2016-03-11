package com.nacho.jugsgraphic.GarrafasEngine;

/**
 * Created by nacho on 8/19/15.
 */
public abstract class JugsEngine {
    static int cotaSuperiorInicial = 30;
    public static void setCota(int cota) {
        cotaSuperiorInicial = cota;
    }

    public static int getCota() {
        return cotaSuperiorInicial;
    }
    public abstract JugsNode search();
}
