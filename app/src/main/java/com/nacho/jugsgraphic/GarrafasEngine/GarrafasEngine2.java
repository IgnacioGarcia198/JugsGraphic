package com.nacho.jugsgraphic.GarrafasEngine;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 

import android.util.Log;

import java.util.*;

/**
 *
 * @author nash
 */
public class GarrafasEngine2 extends JugsEngine {
    //static StringBuilder s;
    Random r = new Random();
    NodoGarrafas raiz;
    String tagg = "";
    //static int cotaSuperiorInicial;
    /*public static void menuPrincipal() {
        s = new StringBuilder();
        s.append("                  MENÚ PRINCIPAL\n");
        s.append("                  ==============\n\n");
        s.append("'n': Nueva partida\n");
        s.append("'s': Salir\n\n");
        s.append("Introduzca una opción: ");
        String sr = s.toString();
        char op;
        do {
            op = Funciones.leeCaracterCondicionado(sr, 'n','s');
            s = new StringBuilder();
            switch (op) {
                case 'n': { 
                    nuevaPartida();
                    break;
                }
                
            }
        } while(op != 's');
    }
    
    public static void nuevaPartida() {
        int[] tabla = null; int cont;
        s = new StringBuilder();
        s.append("                  NUEVA PARTIDA\n");
        s.append("                  =============\n\n");
        s.append("'a': Todo automático\n");
        s.append("'g': Elegir número de garrafas y lo demás automático\n");
        s.append("'o': Elegir el objetivo y lo demás automático\n");
        s.append("'e': Elegir número de garrafas y el objetivo y lo demás automático\n");
        s.append("'m': Todo manual\n");
        s.append("'v': Volver al menú principal\n\n");
        s.append("Introduzca una opción: ");
        String sr = s.toString();
        char op;
        do {
            op = Funciones.leeCaracterCondicionado(sr, 'a','g','o','e','m','v');
            s = new StringBuilder();
            switch (op) {
                case 'a': { 
                    Funciones.meta = r.nextInt(99) + 1;
                    tabla = new int[r.nextInt(99) + 1];
                    for(int i = 0; i < tabla.length; i ++) {
                        tabla[i] = r.nextInt(100) + 1;
                    }
                    break;
                }
                
                case 'g': { 
                    Funciones.meta = r.nextInt(99) + 1;
                    tabla = new int[Funciones.leeEnteroCondicionado(
                    "Número de garrafas: ", 1, 100)];
                    for(int i = 0; i < tabla.length; i ++) {
                        tabla[i] = r.nextInt(100) + 1;
                    }
                    break;
                }
                
                case 'o': { 
                    Funciones.meta = Funciones.leeEnteroCondicionado("Meta: ", 1, 100);
                    tabla = new int[r.nextInt(99) + 1];
                    for(int i = 0; i < tabla.length; i ++) {
                        tabla[i] = r.nextInt(100) + 1;
                    }
                    break;
                }
                
                case 'e': { 
                    tabla = new int[Funciones.leeEnteroCondicionado(
                    "Número de garrafas: ", 1, 100)];
                    for(int i = 0; i < tabla.length; i ++) {
                        tabla[i] = r.nextInt(100) + 1;
                    }
                    Funciones.meta = Funciones.leeEnteroCondicionado("Meta: ", 1, 100);
                    break;
                }
                
                case 'm': { 
                    tabla = new int[Funciones.leeEnteroCondicionado(
                    "Número de garrafas: ", 1, 100)];
                    for(int i = 0; i < tabla.length; i ++) {
                        tabla[i] = Funciones.leeEnteroCondicionado(
                        "capacidad de la garrafa " + i + ": ", 1, 100);
                    }
                    Funciones.meta = Funciones.leeEnteroCondicionado("Meta: ", 1, 100);
                    break;
                }
            }
            cont = 0;
            while((cont < tabla.length) && (tabla[cont] < Funciones.meta)) {
                cont ++;
            }
            if(cont == tabla.length) {
                s = new StringBuilder();
                s.append("la cantidad pedida no cabe en ninguna garrafa.");
                s.append("\nEl problema no se puede resolver.\n");
                s.append("¿generar una nueva partida con otros parámetros? (<s>,<n>)");
                op = Funciones.leeCaracterCondicionado(s.toString(),'s','n');
                if(op == 'n') {
                    System.out.println("saliendo al menú principal");
                    break;
                }
            }
        } while(cont == tabla.length);
        cotaSuperiorInicial = Funciones.leeEnteroCondicionado("Cota superior inicial: ", 1, 400);
        raiz = new NodoGarrafas(tabla); 
        raiz.nodoTexto();
        //NodoGarrafas.ngarrafas = tabla.length;
        NodoGarrafas[] soluciones = RyP();
    }*/

    public GarrafasEngine2(int goal, int[] caps) {
        Funciones.meta = goal;
        raiz = new NodoGarrafas(caps);
    }

    public NodoGarrafas search() {
        return RyP()[0];
    }
    
    NodoGarrafas[] RyP() { //esto hay que revisarlo por diosss
        TablaGarrafas m = new TablaGarrafas(20, 0.3);
        double cotaSuperior = cotaSuperiorInicial + 1;
        NodoGarrafas[] soluciones = new NodoGarrafas[50];
        TablaGarrafas vistos;
        if(NodoGarrafas.ngarrafas > 1) { 
            vistos = new TablaGarrafas((int)Math.pow(10, NodoGarrafas.ngarrafas), 0.3);
        }
        else {
            vistos = new TablaGarrafas(1, 0.3); 
        }
        m.insertar(raiz);
        int nsol = 0, v = 0;
        while(m.ocupado > 0) {
            NodoGarrafas nodo = m.extraerMejor();
            double ci = nodo.prof;
            vistos.insertar(nodo);
            if(nodo.pruebaMeta() >= 0) {
                if((ci < cotaSuperior) && (nodo.esta(soluciones) < 0)) {
                    soluciones[nsol ++] = nodo;
                    cotaSuperior = ci;
                }
            }
            else { 
                if(ci < cotaSuperior) {
                    NodoGarrafas[] compleciones = nodo.compleciones();
                    int i = 0;
                    while((i < compleciones.length) && (compleciones[i] != null)) {
                        //System.out.println("vamos con el hijo " + i);
                        NodoGarrafas c = compleciones[i];
                        //System.out.println("sus garrafas:\n" + c.nodoTexto());
                        int pos;
                        //System.out.println("busco en vistos");
                        pos = vistos.buscarNodo(c);
                        Log.d(tagg, "buenos dias");
                        //System.out.println("el nodo está en " + pos);
                        if(pos >= 0) {
                        //if((pos = c.esta(vistos.arbol)) >= 0) {
                            //System.out.println("está repetido en vistos");
                            NodoGarrafas n = vistos.arbol[pos];
                            if(c.prof < n.prof) {
                                //System.out.println("y es menos profundo");
                                n.padre.quitaHijo(n);
                                if(n.primerHijo != null) {
                                    //System.out.println("el otro tiene hijos, se los asigno y lo reemplazo");
                                    c.robaDescendencia(n);
                                    vistos.reemplazar(pos, c);
                                }
                                else {
                                    //System.out.println("no tenía hijos, así que meto este hijo en m y elimino el otro de vistos");
                                    m.insertar(c);
                                    vistos.eliminar(pos);
                                }
                                //System.out.println("le meto como hijo de su padre");
                                nodo.meteHijo(c);
                            }
                            else {
                                //System.out.println("pero es más profundo así que no le meto en vistos");
                            }
                        }
                        else{
                            //System.out.println("ahora busco en m");
                            pos = m.buscarNodo(c);
                            //System.out.println("el nodo está en " + pos);
                        if(pos >= 0) {
                        //if((pos = c.esta(m.arbol)) >= 0) {
                            //System.out.println("no está repetido en vistos pero sí en m");
                            if(c.prof < m.arbol[pos].prof) {
                                NodoGarrafas n = m.arbol[pos];
                                //System.out.println("y es menos profundo, así que lo reemplazo en m y se lo asigno a su padre");
                                n.padre.quitaHijo(n);
                                m.reemplazar(pos, c);
                                nodo.meteHijo(c);
                            }
                            else {
                                //System.out.println("pero es más profundo, así que no lo meto en m");
                            }
                        }
                        else {
                            //System.out.println("no está repe, lo meto en m y como hijo a su padre");
                            m.insertar(c);
                            nodo.meteHijo(c);
                        }
                        }   
                        //compleciones[i] = null;
                        i ++;
                    }
                }
                //else {System.out.println("el nodo se pasa de cota");}
            }
        }

        if(nsol == 0) {
            //System.out.println("lo siento, no hay solucion");
            return soluciones;
        }
        nsol = 0; NodoGarrafas[] indice = new NodoGarrafas[10];
        int cm = 0, min = soluciones[nsol].prof;
        while((nsol < 50) && (soluciones[nsol] != null)) {
            if(soluciones[nsol].prof < min) {
                cm = 0;
                min = soluciones[nsol].prof;
                indice[cm] = soluciones[nsol];
                indice[cm ++].aux = nsol;
            }
            else if(soluciones[nsol].prof == min) {
                indice[cm] = soluciones[nsol];
                indice[cm ++].aux = nsol;
            }
            nsol ++;
        }

        //String st = Funciones.leeTexto("dsd");
        return indice;
    }
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        menuPrincipal();
    }*/
}
