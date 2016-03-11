package com.nacho.jugsgraphic.GarrafasEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Created by nacho on 8/20/15.
 */
public class NodoG2 implements Comparable<NodoG2>
{
    
    @Override
    public int compareTo(NodoG2 n)
    {
        // TODO: Implement this method
        //NodoG2 n = (NodoG2) p1;
        for(int i = 0; i < ngarrafas; i ++) {
            if(this.contenido[i] > n.contenido[i]) {
                return 1;
            }
            else if(this.contenido[i] < n.contenido[i]) {
                return -1;
            }
        }
        return 0;
    }

    static final int FILL=2, POUR=1, EMPTY=3;
    int nchild;
    boolean remChildren = true;
    StringBuilder ruta;
    static int ngarrafas;
    static int[] capacidad;
    int[] contenido;
    //StringBuilder ruta;
    int prof;
    //int lastorigin, lastDest, lastOp;
    NodoG2 parent;


    public NodoG2(int[] tabl) {
        ngarrafas = tabl.length;
        capacidad = tabl;
        contenido = new int[ngarrafas];
        ruta = new StringBuilder();
        prof = 0;
        //lastDest = 0;
        //lastorigin = 0;
        //lastOp = FILL;
    }

    private NodoG2() {};

    public void copiaGarrafas(NodoG2 origen) {
        for(int i = 0; i < NodoG2.ngarrafas; i ++) {
            contenido[i] = origen.contenido[i];
        }
    }

    public NodoG2(NodoG2 n) {
        this.contenido = new int[ngarrafas];
        this.copiaGarrafas(n);
        this.ruta = new StringBuilder(n.ruta);
        this.prof = n.prof + 1;
        //lastDest = 0;
        //lastorigin = 0;
        //lastOp = POUR;
        this.parent = n;
    }

    boolean llenar(int i) {
        if(contenido[i] < capacidad[i]) {
            contenido[i] = capacidad[i];
            ruta.append(" >"); ruta.append(i);
            return true;
        }
        return false;
    }

    boolean vaciar(int i) {
        if(contenido[i] > 0) {
            contenido[i] = 0;
            ruta.append(" <"); ruta.append(i);
            return true;
        }
        return false;
    }

    boolean volcarEn(int origen, int destino) {
        if((origen != destino) && (contenido[origen] > 0) && (contenido[destino] < capacidad[destino])) {
            if ((capacidad[destino] - contenido[destino]) >= contenido[origen]) {
                contenido[destino] += contenido[origen];
                this.contenido[origen] = 0;
            } else {
                contenido[origen] -= capacidad[destino] - contenido[destino];
                contenido[destino] = capacidad[destino];
            }
            ruta.append(" "); ruta.append(origen); ruta.append(">"); ruta.append(destino);
            return true;
        }
        return false;
    }


    public int pruebaMeta() {
        //System.out.println("pruebo solucion: tengo\n" + nodoTexto());
        for(int i = 0; i < ngarrafas; i ++) {
            if(contenido[i] == Funciones.meta) {
                //System.out.println("es solucion");
                return i;
            }
        }
        return -1;
    }

    ArrayList<NodoG2> compleciones() {
        //NodoG2[] comp = new NodoG2[ngarrafas*ngarrafas];

        TreeSet<NodoG2> tree = new TreeSet<NodoG2>(new JugsComparator());
        //tree.comparator()
        NodoG2 primero, actual;
        int c = 0;
        for(int i = 0; i < ngarrafas; i ++) {
            for(int j = 0; j < ngarrafas; j ++) {
                if((i!= j) && (this.contenido[i] > 0) &&
                        (this.contenido[j] < this.capacidad[j])) {
                    NodoG2 n = new NodoG2(this);
                    n.volcarEn(i, j);
                    //n.ruta.append(n.nodoTexto());
                    if(!n.isRepe())
                        tree.add(n);
                    else
                        n.parent = null;
                    //System.out.println("vuelco " + escribeGarrafa(i) + "\nen " +
                    //escribeGarrafa(j) + "\n");
                }
            }
            if(this.contenido[i] < this.capacidad[i]) {
                NodoG2 n = new NodoG2(this);
                n.llenar(i);
                if(!n.isRepe())
                    tree.add(n);
                else
                    n.parent = null;
                //System.out.println("lleno " + escribeGarrafa(i) + "\n");
            }
            if(this.contenido[i] > 0) {
                NodoG2 n = new NodoG2(this);
                n.vaciar(i);
                if(!n.isRepe())
                    tree.add(n);
                else
                    n.parent = null;
                //System.out.println("vacio " + escribeGarrafa(i) + "\n");
            }
        }
        ArrayList<NodoG2> comp = new ArrayList<NodoG2>(tree);
        Collections.sort(comp, new NodeComparator());
        return comp;
    }

    public NodoG2 nextChild() {
        ArrayList<NodoG2> comp = compleciones();
        NodoG2 child = null;
        if(comp!= null && comp.size() > 0) {
            child = comp.get(nchild);

            if(nchild == comp.size()-1) {
                remChildren = false;
            }
            nchild ++;
        }
        else {
            remChildren = false;
        }

        return child;
    }

    /*public NodoG2 nextChild() {


        boolean sib = false;
        while(true) {
            if(lastorigin >= ngarrafas) {
                return null;
            }
            NodoG2 n = new NodoG2(this);
            if(lastOp == POUR) {
                sib = n.volcarEn(lastorigin,lastDest);

                if (lastDest == ngarrafas - 1) {
                    if (lastorigin < ngarrafas - 1) {
                        lastorigin++;
                    }
                    else {
                        lastOp = FILL;
                        lastorigin = 0;
                        //continue;
                    }
                    lastDest = 0;
                }
                else {
                    lastDest++;
                }
                boolean repe = n.isRepe();
                if(sib && !repe) {
                    return n;
                }
                else {
                    n.parent = null;
                }
				*//*else if(repe) {
					System.out.println("repe: ");
					System.out.println(n.nodoTexto());
				}*//*
                // else
                continue;
            }

            else if(lastOp == FILL) {

                sib = n.llenar(lastorigin);
                if (lastorigin < ngarrafas - 1) {
                    lastorigin++;
                }
                else {
                    lastOp = EMPTY;
                    lastorigin = 0;
                    //continue;
                }
                boolean repe = n.isRepe();
                if(sib && !repe) {
                    return n;
                }
                else {
                    n.parent = null;
                }
				*//*else if(repe) {
					System.out.println("repe: ");
					System.out.println(n.nodoTexto());
				}*//*
                // else
                continue;

            }

            else if(lastOp == EMPTY) {
                sib = n.vaciar(lastorigin);


                if (lastorigin == ngarrafas - 1) {
                    System.out.println("LAST CHILD");
                    if(!sib) {
                        return null;
                    }
                }

                lastorigin++;

                boolean repe = n.isRepe();
                if(sib && !repe) {
                    return n;
                }
                else {
                    n.parent = null;
                }
				*//*else if(repe) {
					System.out.println("repe: ");
					System.out.println(n.nodoTexto());
				}*//*

                // else
                //continue;

            }
        }
        //return null;
    }*/

    public String nodoTexto() {
        StringBuilder s = new StringBuilder();
        s.append("garrafa    ");
        for(int i = 0; i < ngarrafas; i ++) {
            s.append(Funciones.numeroNcifras(i, 3) + " ");
        }
        s.append("\ncapacidad  ");
        for(int i = 0; i < ngarrafas; i ++) {
            s.append(Funciones.numeroNcifras(capacidad[i], 3) + " ");
        }
        s.append("\ncontenido  ");
        for(int i = 0; i < ngarrafas; i ++) {
            s.append(Funciones.numeroNcifras(contenido[i], 3) + " ");
        }
        s.append("\nruta "); s.append(ruta); s.append("\n\n");
        return s.toString();
    }

    boolean isRepe() {
        NodoG2 current = this;
        while(current.parent != null) {
            current = current.parent;
            if(this.compareTo(current) == 0) {
                return true;
            }
        }
        return false;
    }

    double cotaInferior() { // metemos la profundidad, xq si no busca muy abajo
        int suma = 0;
        int suma2 = 0;
        int llenas = 0;
        for(int i = 0; i < ngarrafas; i ++) {
            if(contenido[i] > 0) {
                suma = suma + Funciones.abs(contenido[i] - Funciones.meta);
                suma2 = suma2 + Funciones.abs(capacidad[i] - contenido[i] - Funciones.meta);
                llenas ++;
            }
        }
        if(llenas == 0) {
            return Math.min(suma, suma2);
        }
        return Math.min(suma, suma2) / llenas;
        //return suma/ngarrafas;
    }

    double cotaInferiorProf() { // metemos la profundidad, xq si no busca muy abajo
        int suma = 0;
        int suma2 = 0;
        int llenas = 0;
        for(int i = 0; i < ngarrafas; i ++) {
            if(contenido[i] > 0) {
                suma = suma + Funciones.abs(contenido[i] - Funciones.meta);
                suma2 = suma2 + Funciones.abs(capacidad[i] - contenido[i] - Funciones.meta);
                llenas ++;
            }
        }
        if(llenas == 0) {
            return Math.min(suma, suma2) + prof;
        }
        return Math.min(suma, suma2) / llenas + prof;
        //return suma/ngarrafas;
    }

    class JugsComparator implements Comparator<NodoG2> {

        @Override
        public int compare(NodoG2 lhs, NodoG2 rhs) {

            return lhs.compareTo(rhs);
        }
    }

    class NodeComparator implements Comparator<NodoG2> {

        @Override
        public int compare(NodoG2 lhs, NodoG2 rhs) {
            double c1 = lhs.cotaInferior();
            double c2 = rhs.cotaInferior();
            if(c1 > c2) {return 1;}
            else if(c2 < c1) {return -1;}
            return 0;
        }
    }

    static int cotaInicial() {
        int suma = 0;
        for(int i = 0; i < ngarrafas; i ++) {
            suma += capacidad[i];
        }
        return suma;
    }


}
