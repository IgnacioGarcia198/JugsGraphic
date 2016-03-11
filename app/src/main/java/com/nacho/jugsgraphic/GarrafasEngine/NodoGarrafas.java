package com.nacho.jugsgraphic.GarrafasEngine;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author nash
 */

public class NodoGarrafas extends JugsNode {
    //Garrafa[] tabla;
    static int ngarrafas;
    static int[] capacidad;
    NodoGarrafas primerHijo, siguienteHermano, padre;
    int[] contenido;
    //StringBuilder ruta;
    int prof, aux;
    public NodoGarrafas(int[] tabl) {
        ngarrafas = tabl.length;
        capacidad = tabl;
        contenido = new int[ngarrafas];
        ruta = new StringBuilder();
        prof = 0;
    }
    
    private NodoGarrafas() {};
    
    public void copiaGarrafas(NodoGarrafas origen) {
        for(int i = 0; i < NodoGarrafas.ngarrafas; i ++) {
            contenido[i] = origen.contenido[i];
        }
    }
    
    public NodoGarrafas(NodoGarrafas n) {
        this.contenido = new int[ngarrafas];
        this.copiaGarrafas(n);
        this.ruta = new StringBuilder(n.ruta);
        this.prof = n.prof + 1;
    }
    
    /*public void nuevoHijo() {
        NodoGarrafas ng = new NodoGarrafas();
        ng.contenido = new int[ngarrafas];
        System.arraycopy(this.contenido, 0, ng.contenido, ngarrafas);
        ng.ruta = new StringBuilder(this.ruta);
        ng.prof = this.prof + 1;
        if(primerHijo == null) {
            primerHijo = ng;
            ultimoHijo = ng;
        }
        else {
            ultimoHijo.siguienteHermano = ng;
            ultimoHijo = ng;
        }
    }*/
    
    public void meteHijo(NodoGarrafas ng) {
        ng.padre = this;
        if(primerHijo == null) {
            primerHijo = ng;
        }
        else {
            NodoGarrafas actual = primerHijo;
            while(actual.siguienteHermano != null) {
                actual = actual.siguienteHermano;
            }
            actual.siguienteHermano = ng;
        }
    }
    
    /**
     * Procedimiento que quita el hijo ng a este nodo.
     * @param ng hijo a quitar.
     */
    public void quitaHijo(NodoGarrafas ng) {
        NodoGarrafas actual, anterior;
        actual = anterior = primerHijo;
        while((actual != null) && (ng.comparaGarrafas(actual) != 0)) {
            anterior = actual;    
            actual = actual.siguienteHermano;
        }
        anterior.siguienteHermano = actual.siguienteHermano;
        ng.padre = null;
    }
    
    /**
     * Procedimiento que roba el hijo ng a su padre y lo mete como hijo en este nodo.
     * @param ng hijo a robar
     */
    public void robaHijo(NodoGarrafas ng) {
        NodoGarrafas actual, anterior;
        actual = anterior = ng.padre.primerHijo;
        while((actual != null) && (ng.comparaGarrafas(actual) != 0)) {
            anterior = actual;    
            actual = actual.siguienteHermano;
        }
        anterior.siguienteHermano = actual.siguienteHermano;
        ng.padre = this;
        if(this.primerHijo == null) {
            this.primerHijo = ng;
        }
        else {
            actual = this.primerHijo;
            while(actual.siguienteHermano != null) {
                actual = actual.siguienteHermano;
            }
            actual.siguienteHermano = ng;
            corrigeRama(ng);
        }
    }
    
    /**
     * Procedimiento que roba toda la descendencia al nodo papi.
     * @param papi nodo que sufre el robo pobrecillo
     */
    public void robaDescendencia(NodoGarrafas papi) {
        this.primerHijo = papi.primerHijo;
        papi.primerHijo = null;
        NodoGarrafas actual = primerHijo;
        while(actual != null) {
            actual.padre = this;
            actual = actual.siguienteHermano;
        }
        corrigeHijos();
    }
    
    private void corrigeRutaHijo(NodoGarrafas hijo) {
        String s = hijo.ruta.toString();
        hijo.ruta = new StringBuilder();
        hijo.ruta.append(this.ruta);
        int i = s.lastIndexOf("\n\n", s.length());
        if(i > -1) {hijo.ruta.append(s.substring(i));}
    }
    
    public void corrigeHijos() {
        NodoGarrafas hijo = primerHijo;
        while(hijo != null) {
            corrigeRutaHijo(hijo);
            hijo.prof = this.prof + 1;
            hijo.corrigeHijos();
            hijo = hijo.siguienteHermano;
        }
    }
    
    /**
     * Procedimiento que corrige las rutas y profundidades de una sola rama, la que parte del hijo n.
     * @param n hijo que empieza la rama
     */
    public void corrigeRama(NodoGarrafas n) {
        corrigeRutaHijo(n);
        n.prof = this.prof + 1;
        n.corrigeHijos();
    }
    
    public int comparaGarrafas(NodoGarrafas n) {
        for(int i = 0; i < ngarrafas; i ++) {
            if(this.contenido[i] < n.contenido[i]) { return -1; }
            if(this.contenido[i] > n.contenido[i]) { return 1; }
        }
        return 0;
    }
    
    void llenar(int i) {
        contenido[i] = capacidad[i];
        //this.ruta.append("\n\nlleno " + escribeGarrafa(i));
        //System.out.println("vacio " + escribeGarrafa(i) + "\n");
    }
    
    void vaciar(int i) {
        contenido[i] = 0;
        //this.ruta.append("\n\nvacio " + escribeGarrafa(i));
        //System.out.println("lleno " + escribeGarrafa(i) + "\n");
    }
    
    void volcarEn(int origen, int destino) {
        if((capacidad[destino] - contenido[destino]) >= contenido[origen]) {
            contenido[destino] += contenido[origen];
            this.contenido[origen] = 0;
        }
        else {
            contenido[origen] -= capacidad[destino] - contenido[destino];
            contenido[destino] = capacidad[destino];
        }
        //this.ruta.append("\n\nvuelco " + escribeGarrafa(origen) + "\nen " + escribeGarrafa(destino));
        //System.out.println("vuelco " + escribeGarrafa(i) + "\nen " + escribeGarrafa(j) + "\n");
    }
    
    /*double cotaInferior() {
        int min = 100;
        for(int i = 0; i < ngarrafas; i ++) {
            if(capacidad[i] > Funciones.meta) {
                int res = Funciones.abs(contenido[i] - Funciones.meta);
                if(res < min) { min = res; }
            }
        }
        return min + prof;
    }*/
    
    /*double cotaInferior() { // impresionante, óptima.
        int suma = 0, cont = 0;
        for(int i = 0; i < ngarrafas; i ++) {
            if(contenido[i] > 0) {
                suma = suma + Funciones.abs(contenido[i] - Funciones.meta);
                cont ++;
            }
        }
        if(cont == 0) {return suma + prof;}
        else {return suma/cont + prof;}
    }*/
    
    /*double cotaInferior() { // ULTIMAS IDEAS HASTA EL MOMENTO
        int suma = 0, cont = 0;
        for(int i = 0; i < ngarrafas; i ++) {
            if(contenido[i] > 0) {
                suma = suma + Funciones.abs(contenido[i] - Funciones.meta);
                cont ++;
            }
        }
        if(cont == 0) {return suma + cont;}
        else {return suma/cont + cont;}
    }*/
    
    /*double cotaInferior() {
        int suma = 0;
        for(int i = 0; i < ngarrafas; i ++) {
            suma = suma + Funciones.abs(contenido[i] - Funciones.meta);
        }
        return suma/ngarrafas + prof;
    }*/
    
    /*double cotaInferior() { // cojonuda para hallar todas las soluciones posibles
        int suma = 0;
        for(int i = 0; i < ngarrafas; i ++) {
            suma = suma + contenido[i];
        }
        return Funciones.abs(suma/ngarrafas - Funciones.meta);// + prof;
    }*/
    
    /*double cotaInferior() { // ULTIMAS IDEAS HASTA EL MOMENTO
        int suma = 0, cont = 0;
        for(int i = 0; i < ngarrafas; i ++) {
            if(contenido[i] > 0) {
                suma = suma + contenido[i];
                cont ++;
            }
        }
        if(cont == 0) {return Funciones.abs(suma - Funciones.meta) + cont;}
        else {return Funciones.abs(suma/cont - Funciones.meta) + cont;}
    }*/
    
    /*double cotaInferior() { // ULTIMAS IDEAS HASTA EL MOMENTO
        int suma = 0, cont = 0;
        for(int i = 0; i < ngarrafas; i ++) {
            if(contenido[i] > 0) {
                suma = suma + contenido[i];
                cont ++;
            }
        }
        return Funciones.abs(suma/ngarrafas - Funciones.meta);
    }*/
    
    double cotaInferior() { // metemos la profundidad, xq si no busca muy abajo
        int suma = 0;
        for(int i = 0; i < ngarrafas; i ++) {
            if(contenido[i] > 0) {
                suma = suma + Funciones.abs(contenido[i] - Funciones.meta);
            }
        }
        return suma/ngarrafas;
    }

    double cotaInferior2() { // metemos la profundidad, xq si no busca muy abajo
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
        return Math.min(suma, suma2) / llenas;
        //return suma/ngarrafas;
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
    
    NodoGarrafas[] compleciones() {
        NodoGarrafas[] comp = new NodoGarrafas[ngarrafas*ngarrafas];
        NodoGarrafas primero, actual;
        int c = 0;
        for(int i = 0; i < ngarrafas; i ++) {
            for(int j = 0; j < ngarrafas; j ++) {
                if((i!= j) && (this.contenido[i] > 0) && 
                (this.contenido[j] < this.capacidad[j])) {
                    NodoGarrafas n = new NodoGarrafas(this);
                    n.volcarEn(i, j);
                    n.ruta.append(" "); n.ruta.append(i); n.ruta.append(">"); n.ruta.append(j);
                    //n.ruta.append(n.nodoTexto());
                    comp[c ++] = n;
                    //System.out.println("vuelco " + escribeGarrafa(i) + "\nen " +
                    //escribeGarrafa(j) + "\n");
                }
            }
            if(this.contenido[i] < this.capacidad[i]) {
                NodoGarrafas n = new NodoGarrafas(this);
                n.llenar(i);
                n.ruta.append(" >"); n.ruta.append(i);
                comp[c ++] = n;
                //System.out.println("lleno " + escribeGarrafa(i) + "\n");
            }
            if(this.contenido[i] > 0) {
                NodoGarrafas n = new NodoGarrafas(this);
                n.vaciar(i);
                n.ruta.append(" <"); n.ruta.append(i);
                comp[c ++] = n;
                //System.out.println("vacio " + escribeGarrafa(i) + "\n");
            }
        }
        return comp;
    }
    
    int esta(NodoGarrafas[] vistos) {
        int i = 0;
        while((i < vistos.length) && (vistos[i] != null)) {
            int j;
            for(j = 0; j < ngarrafas; j ++) {
                if(vistos[i].contenido[j] != this.contenido[j]) {
                    break;
                }
            }
            if(j == ngarrafas) {return i;}
            i ++;
        }
        return -1;
    }
    
    public String escribeGarrafa(int n) {
        StringBuilder s = new StringBuilder();
        int cap = capacidad[n];
        s.append("la garrafa "); s.append(n); s.append(" (capacidad: "); s.append(cap); 
        if(cap == 1) {s.append(" litro");}
        else {s.append(" litros");}
        int cont = contenido[n];
        s.append("; contiene "); s.append(cont);
        if(cont == 1) {s.append(" litro)");}
        else {s.append(" litros)");}
        return s.toString();
    }
    
    /*public void muestraNodo() {
        System.out.print("garrafa    ");
        for(int i = 0; i < ngarrafas; i ++) {
            System.out.print(Funciones.numeroNcifras(i, 3) + " ");
        }
        System.out.print("\ncapacidad  ");
        for(int i = 0; i < ngarrafas; i ++) {
            System.out.print(Funciones.numeroNcifras(capacidad[i], 3) + " ");
        }
        System.out.print("\ncontenido  ");
        for(int i = 0; i < ngarrafas; i ++) {
            System.out.print(Funciones.numeroNcifras(contenido[i], 3) + " ");
        }
        System.out.println();
    }*/
    
    public String nodoTexto() {
        StringBuilder s = new StringBuilder();
        s.append("\ngarrafa    ");
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
        s.append("\nruta "); s.append(ruta);
        return s.toString();
    }

    public int[] getContent() {
        return contenido;
    }


}
