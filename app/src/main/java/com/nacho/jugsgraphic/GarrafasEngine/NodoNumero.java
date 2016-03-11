package com.nacho.jugsgraphic.GarrafasEngine;


/**
 * Write a description of class NodoNumeroN2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NodoNumero extends JugsNode
{
    static long capacidad;
    int aux;
    long contenido;
    int prof;
    NodoNumero primerHijo, siguienteHermano, padre;


    public static int ncifras, ngarrafas;

    static void initializeStaticValues(int[] caps) {
        ncifras = Funciones.ncifras(Funciones.maxInVector(caps)); // MAX CAP OF JUG (I ALREADY HAVE SUCH FUNCTION)
        //cotaSuperiorInicial = Funciones.leeEnteroCondicionado("Cota superior inicial: ", 1, 100);
        capacidad = Funciones.construyeLong(caps, NodoNumero.ncifras);
        ngarrafas = caps.length;
    }

    public static void setest(long cap, int nc, int ng) {
        capacidad = cap;
        ncifras = nc;
        ngarrafas = ng;
    }

    public NodoNumero(long contenido) {
        this.contenido = contenido;
        this.ruta = new StringBuilder();
        prof = 0;
    }
    
    public NodoNumero(NodoNumero n) {
        this.contenido = n.contenido;
        this.ruta = new StringBuilder(n.ruta);
        this.prof = n.prof + 1;
    }
    
    private int min(int a, int b) {
        if(a < b) {return a;}
        else {return b;}
    }
    
    public int comparaCon(NodoNumero ng) {
        if(this.contenido > ng.contenido) {return 1;}
        else if(this.contenido < ng.contenido) {return -1;}
        else{return 0;}
    }
    
    private int cap(int i) {
        return (int)(capacidad % Math.pow(10,(i+1)*ncifras)/Math.pow(10,i*ncifras));
    }
    
    private int cont(int i) {
        return (int)(contenido % Math.pow(10,(i+1)*ncifras)/Math.pow(10,i*ncifras));
    }

    double cotaInferior2() { // metemos la profundidad, xq si no busca muy abajo
        int suma = 0;
        int suma2 = 0;
        int llenas = 0;
        for(int i = 0; i < ngarrafas; i ++) {
            int con = cont(i);
            int ca = cap(i);
            if(con > 0) {
                suma = suma + Funciones.abs(con - Funciones.meta);
                suma2 = suma2 + Funciones.abs(ca - con - Funciones.meta);
                llenas ++;
            }
        }
        if(llenas == 0) {
            return Math.min(suma, suma2) + prof;
        }
        return Math.min(suma, suma2) / llenas + prof;
        //return suma/ngarrafas;
    }
    
    public void meteHijo(NodoNumero ng) {
        ng.padre = this;
        if(primerHijo == null) {
            primerHijo = ng;
        }
        else {
            NodoNumero actual = primerHijo;
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
    public void quitaHijo(NodoNumero ng) {
        NodoNumero actual, anterior;
        actual = anterior = primerHijo;
        while((actual != null) && (ng.comparaCon(actual) != 0)) {
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
    public void robaHijo(NodoNumero ng) {
        NodoNumero actual, anterior;
        actual = anterior = ng.padre.primerHijo;
        while((actual != null) && (ng.comparaCon(actual) != 0)) {
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
    public void robaDescendencia(NodoNumero papi) {
        this.primerHijo = papi.primerHijo;
        papi.primerHijo = null;
        NodoNumero actual = primerHijo;
        while(actual != null) {
            actual.padre = this;
            actual = actual.siguienteHermano;
        }
        corrigeHijos();
    }
    
    private void corrigeRutaHijo(NodoNumero hijo) {
        String s = hijo.ruta.toString();
        hijo.ruta = new StringBuilder();
        hijo.ruta.append(this.ruta);
        int i = s.lastIndexOf(" ", s.length());
        if(i > -1) {hijo.ruta.append(s.substring(i));}
    }
    
    public void corrigeHijos() {
        NodoNumero hijo = primerHijo;
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
    public void corrigeRama(NodoNumero n) {
        corrigeRutaHijo(n);
        n.prof = this.prof + 1;
        n.corrigeHijos();
    }
    
    double cotaInferior() { // metemos la profundidad, xq si no busca muy abajo
        int suma = 0; long contactual = contenido;
        for(int i = 0; i < ngarrafas; i ++) {
            int cifra = (int)(contactual % Math.pow(10, ncifras));
            contactual = (long)(contactual/Math.pow(10, ncifras));
            suma = suma + Funciones.abs(cifra - Funciones.meta);
        }
        return suma/ngarrafas;
    }

    static int cotaInicial() {
        int suma = 0; long contactual = capacidad;
        for(int i = 0; i < ngarrafas; i ++) {
            int cifra = (int)(contactual % Math.pow(10, ncifras));
            contactual = (long)(contactual/Math.pow(10, ncifras));
            suma = suma + cifra;
        }
        return suma;
    }
    
    public int pruebaMeta() {
        //System.out.println("pruebo solucion: tengo\n" + nodoTexto());
        long contactual = contenido;
        for(int i = 0; i < ngarrafas; i ++) {
            int cifra = (int)(contactual % Math.pow(10, ncifras));
            if(cifra == Funciones.meta) {return i;/*ngarrafas - i - 1;*/}
            contactual = (long)(contactual/Math.pow(10, ncifras));
        }
        return -1;
    }
    
    void volcarEn(int i, int j) {
        int ci = cont(i);
        int cj = cont(j); 
        int caj = cap(j);
        if((ci > 0) && (cj < caj) && (i != j)) {
            int trasv = min(ci, caj-cj);
            contenido += trasv*(Math.pow(10, j*ncifras)- Math.pow(10, i*ncifras));
            //ruta.append(i); ruta.append(">"); ruta.append(j); n.ruta.append(" ");
            //System.out.println("vuelco " + escribeGarrafa(i) + "\nen " +
           // escribeGarrafa(j));
            //System.out.println(nodoTexto());
        }
    }
    
    void llenar(int i) {
        int ca = cap(i);
        int co = cont(i);
        if(co < ca) {
            contenido += (ca-co)*Math.pow(10,i*ncifras);
            //n.ruta.append(">"); n.ruta.append(i); n.ruta.append(" ");
            //System.out.println("lleno " + escribeGarrafa(i));
            //System.out.println(nodoTexto());
        }
    }
    
    void vaciar(int i) {
        int ca = cap(i);
        int co = cont(i);
        if(co > 0) {
            contenido -= co*Math.pow(10,i*ncifras);
            //n.ruta.append("<"); ruta.append(i); ruta.append(" ");
            //System.out.println("vacío " + escribeGarrafa(i));
            //System.out.println(nodoTexto());
        }
    }
    
    NodoNumero[] compleciones() {
      NodoNumero[] comp = new NodoNumero[ngarrafas*ngarrafas];
      int c = 0;
      for(int i = 0; i < ngarrafas; i ++) {
        for(int j = 0; j < ngarrafas; j ++) {
          // empieza volcar         
          int ci = cont(i);
          int cj = cont(j);    
          int caj = cap(j);
          if((i != j) && (ci > 0) && (cj < caj)) {
            NodoNumero n = new NodoNumero(this);
            int trasv = min(ci, caj-cj);
            n.contenido += trasv*(Math.pow(10, j*ncifras)- Math.pow(10, i*ncifras));
            n.ruta.append(" "); n.ruta.append(i); n.ruta.append(">"); n.ruta.append(j);
            comp[c ++] = n;
            //System.out.println("vuelco " + escribeGarrafa(i) + "\nen " +
            //escribeGarrafa(j) + "\n");
          }
        }
        // fin volcar
        // empieza llenar
        int ca = cap(i);
        int co = cont(i);
        if(co < ca) {
            NodoNumero n = new NodoNumero(this); 
            n.contenido += (ca-co)*Math.pow(10,i*ncifras);
            n.ruta.append(" >"); n.ruta.append(i);
            comp[c ++] = n;
            //System.out.println("lleno " + escribeGarrafa(i) + "\n");
        }
        // fin llenar
        // empieza vaciar
        if(co > 0) {
            NodoNumero n = new NodoNumero(this); 
            n.contenido -= co*Math.pow(10,i*ncifras);
            n.ruta.append(" <"); n.ruta.append(i);
            comp[c ++] = n;
            //System.out.println("vacío " + escribeGarrafa(i) + "\n");
        }
          // fin vaciar
      }
      return comp;
    }
    
    void compilaRuta() {
        String[]tablaRuta = this.ruta.toString().split(" ");
        int estado = 0;
        NodoNumero ns = new NodoNumero(0);
        for(int i = 0; i < tablaRuta.length; i ++) {
            String str = tablaRuta[i];
            if(str.matches(">\\d+")) {
                ns.llenar(Integer.parseInt(str.substring(1)));
            }
            else if(str.matches("<\\d+")) {
                ns.vaciar(Integer.parseInt(str.substring(1)));
            } 
            else if(str.matches("\\d+>\\d+")) {
                String[] ts = str.split(">");
                ns.volcarEn(Integer.parseInt(ts[0]), Integer.parseInt(ts[1]));
            }
        }
    }
    
    int esta(NodoNumero[] vistos) {
        int i = 0;
        while((i < vistos.length) && (vistos[i] != null)) {
            if(this.comparaCon(vistos[i]) == 0) {return i;}
            i ++;
        }
        return -1;
    }
    
    public String escribeGarrafa(int n) {
        StringBuilder s = new StringBuilder();
        int capa = cap(n);
        s.append("la garrafa "); s.append(n); s.append(" (capacidad: "); s.append(capa); 
        if(capa == 1) {s.append(" litro");}
        else {s.append(" litros");}
        int conte = cont(n);
        s.append("; contiene "); s.append(conte);
        if(conte == 1) {s.append(" litro)");}
        else {s.append(" litros)");}
        return s.toString();
    }
    
    public String nodoTexto() { // esta función hay que mejorarla con el compilador de rutillas XD
        StringBuilder s = new StringBuilder();
        s.append("\ngarrafa    ");
        for(int i = 0; i < ngarrafas; i ++) {
            s.append(Funciones.numeroNcifras(i, ncifras) + " ");
        }
        s.append("\ncapacidad  ");
        for(int i = 0; i < ngarrafas; i ++) {
            s.append(Funciones.numeroNcifras(cap(i), ncifras) + " ");
        }
        s.append("\ncontenido  ");
        for(int i = 0; i < ngarrafas; i ++) {
            s.append(Funciones.numeroNcifras(cont(i), ncifras) + " ");
        }
        s.append("\n");
        return s.toString();
    }


}
