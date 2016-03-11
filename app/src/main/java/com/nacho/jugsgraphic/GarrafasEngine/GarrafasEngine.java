package com.nacho.jugsgraphic.GarrafasEngine;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 // PARAMETERS I NEED: META, JUGS CAP TABLE,

import android.view.View;

import com.nacho.jugsgraphic.Garrafas;

import java.util.Random;

/**
 *
 * @author nash
 */
public class GarrafasEngine extends JugsEngine {
    //static int maximaGarrafa = 100;
    StringBuilder s;
    Random r = new Random();
    NodoNumero raiz;



    public GarrafasEngine(int goal, int[] caps) {
        //int max =
        NodoNumero.initializeStaticValues(caps);
        //cotaSuperiorInicial = cota;
        raiz = new NodoNumero(0);
        Funciones.meta = goal;
        //System.out.println(raiz.nodoTexto());
        //NodoNumero[] soluciones = RyP();
    }


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
    }*/
    
    /*public static void nuevaPartida() {
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
        char op; int max;
            op = Funciones.leeCaracterCondicionado(sr, 'a','g','o','e','m','v');
            s = new StringBuilder();
            max = 0;
            switch (op) {
                case 'a': { 
                    tabla = new int[r.nextInt(8) + 1];
                    for(int i = 0; i < tabla.length; i ++) {
                        tabla[i] = r.nextInt(maximaGarrafa) + 1;
                        if(tabla[i] > max) {max = tabla[i];}
                    }
                    Funciones.meta = r.nextInt(max) + 1;
                    break;
                }
                
                case 'g': { 
                    tabla = new int[Funciones.leeEnteroCondicionado(
                    "Número de garrafas: ", 1, 8)];
                    for(int i = 0; i < tabla.length; i ++) {
                        tabla[i] = r.nextInt(maximaGarrafa) + 1;
                        if(tabla[i] > max) {max = tabla[i];}
                    }
                    Funciones.meta = r.nextInt(max) + 1;
                    break;
                }
                
                case 'o': { 
                    Funciones.meta = Funciones.leeEnteroCondicionado("Meta: ", 1, maximaGarrafa);
                    tabla = new int[r.nextInt(8) + 1];
                    for(int i = 0; i < tabla.length; i ++) {
                        tabla[i] = r.nextInt(maximaGarrafa - Funciones.meta) + Funciones.meta;
                        if(tabla[i] > max) {max = tabla[i];}
                    }
                    break;
                }
                
                case 'e': { 
                    tabla = new int[Funciones.leeEnteroCondicionado(
                    "Número de garrafas: ", 1, 8)];
                    Funciones.meta = Funciones.leeEnteroCondicionado("Meta: ", 1, maximaGarrafa);
                    for(int i = 0; i < tabla.length; i ++) {
                        tabla[i] = r.nextInt(maximaGarrafa - Funciones.meta) + Funciones.meta;
                        if(tabla[i] > max) {max = tabla[i];}
                    }
                    break;
                }
                
                case 'm': { 
                    tabla = new int[Funciones.leeEnteroCondicionado(
                    "Número de garrafas: ", 1, 8)];
                    for(int i = 0; i < tabla.length; i ++) {
                        tabla[i] = Funciones.leeEnteroCondicionado(
                        "capacidad de la garrafa " + i + ": ", 1, maximaGarrafa);
                        if(tabla[i] > max) {max = tabla[i];}
                    }
                    Funciones.meta = Funciones.leeEnteroCondicionado("Meta: ", 1, maximaGarrafa);
                    while(Funciones.meta > max) {
                        s = new StringBuilder();
                        s.append("la cantidad pedida no cabe en ninguna garrafa.\n");
                        s.append("escribe otra meta que quepa en alguna: ");
                        Funciones.meta = Funciones.leeEnteroCondicionado(s.toString(), 1, maximaGarrafa);
                    } 
                    break;
                }
                
                case 'v':{return;}
            }
        NodoNumero.ncifras = Funciones.ncifras(max); // MAX CAP OF JUG (I ALREADY HAVE SUCH FUNCTION)
        cotaSuperiorInicial = Funciones.leeEnteroCondicionado("Cota superior inicial: ", 1, 100);
        NodoNumero.capacidad = Funciones.construyeLong(tabla,NodoNumero.ncifras);
        NodoNumero.ngarrafas = tabla.length;
        raiz = new NodoNumero(0); 
        System.out.println(raiz.nodoTexto());
        NodoNumero[] soluciones = RyP();
    }*/

    public NodoNumero search() {
        return RyP()[0];
    }

    public NodoNumero[] RyP() { //esto hay que revisarlo por diosss
        TablaNumeros m = new TablaNumeros(20, 0.3);

        double cotaSuperior = cotaSuperiorInicial + 1;//NodoNumero.cotaInicial();
        NodoNumero[] soluciones = new NodoNumero[50];
        TablaNumeros vistos;
        if(NodoNumero.ngarrafas > 1) { 
            vistos = new TablaNumeros((int)Math.pow(10, NodoNumero.ngarrafas), 0.3);
        }
        else {
            vistos = new TablaNumeros(2, 0.5);
        }
        m.insertar(raiz);
        int nsol = 0, v = 0;
        //System.out.println("RyR va a comenzar el bucle");
        //System.out.println(m.arbol[0].nodoTexto());
        while(m.ocupado > 0) {
            NodoNumero nodo = m.extraerMejor();
            double ci = nodo.prof;//cotaInferior2();
            vistos.insertar(nodo);
            if(nodo.pruebaMeta() >= 0) {
                if((ci < cotaSuperior) && (nodo.esta(soluciones) < 0)) {
                    soluciones[nsol ++] = nodo;
                    cotaSuperior = ci;
                }
            }
            else { 
                if(ci < cotaSuperior) {
                    NodoNumero[] compleciones = nodo.compleciones();
                    /*System.out.println("compleciones:"); 
                    int i = 0;
                    while((i < compleciones.length) && (compleciones[i] != null)) {
                        System.out.println(compleciones[i].contenido);
                        i ++;
                    }*/
                    int i = 0;
                    while((i < compleciones.length) && (compleciones[i] != null)) {
                        //System.out.println("vamos con el hijo " + i);
                        NodoNumero c = compleciones[i];
                        //System.out.println("sus garrafas:\n" + c.nodoTexto());
                        int pos;
                        //System.out.println("busco en vistos");
                        pos = vistos.buscarNodo(c);
                        //System.out.println("el nodo está en " + pos);
                        if(pos >= 0) {
                        //if((pos = c.esta(vistos.arbol)) >= 0) {
                            //System.out.println("está repetido en vistos");
                            NodoNumero n = vistos.arbol[pos];
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
                                NodoNumero n = m.arbol[pos];
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
           // System.out.println("lo siento, no hay solucion");
            return soluciones;
        }
        // NOW WE HAVE TO ORDER THE SOLUTIONS BY DEPTH:
        nsol = 0; NodoNumero[] indice = new NodoNumero[10];
        int cm = 0, min = soluciones[nsol].prof + 1;
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

        Garrafas.processImage.setVisibility(View.INVISIBLE);
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
