package com.nacho.jugsgraphic.GarrafasEngine;

/**
 * Una tabla de nodos de garrafas ampliable, ordenada por la cantidad de agua de sus garrafas.
 */
public class TablaNumeros
{   
    //                       CAMPOS Y MÉTODOS PÚBLICOS
    //============================================================================
    NodoNumero[] arbol;
    int longitud, ocupado;
    double crecimiento;

    /**
     * Constructor: pide la capacidad inicial de la tabla y el porcentaje de
     * crecimiento.
     */
    public TablaNumeros(int n, double c) {
        this.arbol = new NodoNumero[n];
        this.longitud = n; this.ocupado = 0;
        this.crecimiento = c;
    }

    /**
     * Inserta un nuevo nodo al final de la tabla.
     * @param nodo nodo a insertar
     */
    void insertarFinal(NodoNumero nodo) {
        if(ocupado == longitud) {
            this.longitud = (int)(longitud * (1 + crecimiento));
            NodoNumero[] temp = new NodoNumero[longitud];
            System.arraycopy(arbol, 0, temp, 0, ocupado);
            arbol = temp;
        }
        arbol[ocupado ++] = nodo;
    }

    /**
     * Procedimiento público para llamar a insertarP. Implementea el mismo método
     * de la superclase abstracta.
     *
     */
    public void insertar(NodoNumero nodo) {
        this.insertarP(nodo);
    }

    public NodoNumero extraerMejor() {
        NodoNumero n = arbol[ocupado - 1];
        this.arbol[-- ocupado] = null;
        return n;
    }

    public NodoNumero extraer(int indice) {
        NodoNumero n = arbol[indice];
        eliminarPos(indice);
        return n;
    }

     /**
     * Procedimiento público que llama a eliminarP. Implementea el mismo método
     * de la superclase abstracta.
     *
     */
    public void eliminar(int indice) {
        this.eliminarPos(indice);
    }

    /**
     * Función que mediante una búsqueda dicotómica encuentra un nodo, da su posición y si no -1.
     */
    public int buscarNodo(NodoNumero nodo) {
        if((this.ocupado == 0) || (nodo.comparaCon(arbol[0]) > 0)
        || (nodo.comparaCon(arbol[ocupado - 1]) < 0)) {
            return -1; // no encontrado
        }
        if(nodo.comparaCon(arbol[0]) == 0) {return 0;}
        if(nodo.comparaCon(arbol[ocupado - 1]) == 0) {return ocupado - 1;}
        int i = 0, j = this.ocupado;
        do {
            int m = (i + j)/2;
            int c = nodo.comparaCon(arbol[m]);
            if(c < 0) {
                i = m;
            }
            else if(c > 0)  {
                j = m;
            }
            else {
                return m; // encontrado
            }
        } while(i < j - 1);
        return -j; // no encontrado, devolvemos la posición donde lo meteríamos porque mola más.
    }

    public void reemplazar(int pos, NodoNumero nodo) {
        int c = nodo.comparaCon(arbol[pos]);
        if(c == 0) {
            arbol[pos] = nodo;
        }
        else if(c < 0) {
            while((pos < ocupado - 1) && (nodo.comparaCon(arbol[pos + 1]) < 0))
            {
                this.arbol[pos] = this.arbol[pos + 1];
                pos ++;
            }
            arbol[pos] = nodo;
        }
        else {
           while((pos > 0) && (nodo.comparaCon(arbol[pos - 1]) > 0))
            {
                this.arbol[pos] = this.arbol[pos - 1];
                pos --;
            }
            arbol[pos] = nodo;
        }
    }



    //                CAMPOS Y MÉTODOS PRIVADOS O PROTEGIDOS
    //============================================================================

    /**
     * Función que mediante una búsqueda dicotómica encuentra el lugar correcto de
     * inserción de un nuevo nodo en la tabla.
     */
    private int buscarLugar(NodoNumero nodo) {
        if(nodo.comparaCon(arbol[0]) > 0) {
            return 0; // colocar al inicio de la tabla
        }
        int i = 0, j = this.ocupado;
        do {
            int m = (i + j)/2;
            int c = nodo.comparaCon(arbol[m]);
            if(c < 0) {
                i = m;
            }
            else {
                j = m;
            }
        } while(i < j-1);
        return j; // colocar entre i y j.
    }

    /**
     * Función que mediante una búsqueda dicotómica encuentra el lugar correcto de
     * inserción de un nuevo nodo en un trozo de la tabla.
     */
    private int buscarLugar(NodoNumero nodo, int i, int j) {
        if(nodo.comparaCon(arbol[i]) > 0) {
            return i; // colocar al inicio del trozo de tabla
        }
        do {
            int m = (i + j)/2;
            int c = nodo.comparaCon(arbol[m]);
            if(c < 0) {
                i = m;
            }
            else {
                j = m;
            }
        } while(i < j-1);
        return j; // colocar entre i y j.
    }

    /**
     * Procedimiento que inserta un nodo en una posición de la tabla, copiando
     * la tabla en otra mayor si se ha quedado pequeña, o desplazando los elementos
     * posteriores a dicha posición para hacer sitio al nuevo.
     * @param nodo nodo que queremos insertar
     */
    protected void insertarP(NodoNumero nodo) {
        if(this.ocupado == 0) {
            this.arbol[ocupado ++] = nodo;
            return;
        }
        int pos = this.buscarLugar(nodo);
        if(pos == this.ocupado) {
            this.insertarFinal(nodo);
            return;
        }
        else {
            if(this.ocupado < this.longitud) {
                int i = this.ocupado;
                do {
                    this.arbol[i] = this.arbol[i - 1];
                    i --;
                } while (i > pos);
                this.arbol[pos] = nodo;
            }
            else {
                this.longitud = (int)(longitud * (1 + this.crecimiento));
                NodoNumero[] ng = new NodoNumero[this.longitud];
                if(pos > 0) {
                    System.arraycopy(this.arbol, 0, ng, 0, pos);
                } 
                ng[pos] = nodo;
                System.arraycopy(this.arbol, pos, ng, pos + 1, this.ocupado - pos);
                this.arbol = ng;
            }
        }
        ocupado ++;
    }

    protected void eliminarPos(int pos) {
        if(pos == ocupado - 1) {
            this.arbol[pos] = null;
        }
        else {
            do {
                this.arbol[pos] = this.arbol[++ pos];
            } while(pos < ocupado - 1);
        }
        ocupado --;
    }
}
