package com.nacho.jugsgraphic.GarrafasEngine;

/**
 * Una tabla de nodos garrafas ampliable, ordenada por la cantidad de agua de sus garrafas.
 */
public class TablaGarrafas
{   
    //                       CAMPOS Y MÉTODOS PÚBLICOS
    //============================================================================
    NodoGarrafas[] arbol;
    int longitud, ocupado;
    double crecimiento;

    /**
     * Constructor: pide la capacidad inicial de la tabla y el porcentaje de
     * crecimiento.
     */
    public TablaGarrafas(int n, double c) {
        this.arbol = new NodoGarrafas[n];
        this.longitud = n; this.ocupado = 0;
        this.crecimiento = c;
    }

    /**
     * Implementa el mismo método de la superclase abstracta.
     * @param nodo nodo a insertar
     */
    void insertarFinal(NodoGarrafas nodo) {
        if(ocupado == longitud) {
            this.longitud = (int)(longitud * (1 + crecimiento));
            NodoGarrafas[] temp = new NodoGarrafas[longitud];
            System.arraycopy(arbol, 0, temp, 0, ocupado);
            arbol = temp;
        }
        arbol[ocupado ++] = nodo;
    }

    /**
     * Procedimiento público para llamar a insertarP. Implementea el mismo método
     * de la superclase abstracta.

     */
    public void insertar(NodoGarrafas nodo) {
        this.insertarP(nodo);
    }

    public NodoGarrafas extraerMejor() { //Puaj esto solo saca el primero nodo metido que sera
        NodoGarrafas n = arbol[ocupado - 1];
        this.arbol[-- ocupado] = null;
        return n;
    }

    public NodoGarrafas extraer(int indice) {
        NodoGarrafas n = arbol[indice];
        eliminarPos(indice);
        return n;
    }

     /**
     * Procedimiento público que llama a eliminarP. Implementea el mismo método
     */
    public void eliminar(int indice) {
        this.eliminarPos(indice);
    }

    /**
     * Función que mediante una búsqueda dicotómica encuentra un nodo, da su posición y si no -1.
     */
    public int buscarNodo(NodoGarrafas nodo) {
        if((this.ocupado == 0) || (nodo.comparaGarrafas(arbol[0]) > 0)
        || (nodo.comparaGarrafas(arbol[ocupado - 1]) < 0)) {
            return -1; // no encontrado
        }
        if(nodo.comparaGarrafas(arbol[0]) == 0) {return 0;}
        if(nodo.comparaGarrafas(arbol[ocupado - 1]) == 0) {return ocupado - 1;}
        int i = 0, j = this.ocupado;
        do {
            int m = (i + j)/2;
            int c = nodo.comparaGarrafas(arbol[m]);
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

    public void reemplazar(int pos, NodoGarrafas nodo) {
        int c = nodo.comparaGarrafas(arbol[pos]);
        if(c == 0) {
            arbol[pos] = nodo;
        }
        else if(c < 0) {
            while((pos < ocupado - 1) && (nodo.comparaGarrafas(arbol[pos + 1]) < 0))
            {
                this.arbol[pos] = this.arbol[pos + 1];
                pos ++;
            }
            arbol[pos] = nodo;
        }
        else {
           while((pos > 0) && (nodo.comparaGarrafas(arbol[pos - 1]) > 0))
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
    private int buscarLugar(NodoGarrafas nodo) {
        if(nodo.comparaGarrafas(arbol[0]) > 0) {
            return 0; // colocar al inicio de la tabla
        }
        int i = 0, j = this.ocupado;
        do {
            int m = (i + j)/2;
            int c = nodo.comparaGarrafas(arbol[m]);
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
    private int buscarLugar(NodoGarrafas nodo, int i, int j) {
        if(nodo.comparaGarrafas(arbol[i]) > 0) {
            return i; // colocar al inicio del trozo de tabla
        }
        do {
            int m = (i + j)/2;
            int c = nodo.comparaGarrafas(arbol[m]);
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
    protected void insertarP(NodoGarrafas nodo) {
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
                NodoGarrafas[] ng = new NodoGarrafas[this.longitud];
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
            } while(pos < ocupado);
        }
        ocupado --;
    }
}
