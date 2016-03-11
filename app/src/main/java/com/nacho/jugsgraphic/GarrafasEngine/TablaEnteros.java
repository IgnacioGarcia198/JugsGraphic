package com.nacho.jugsgraphic.GarrafasEngine;

/**
 * Una tabla de nodos garrafas ampliable, ordenada por la cantidad de agua de sus garrafas.
 */
public class TablaEnteros
{   
    //                       CAMPOS Y MÉTODOS PÚBLICOS
    //============================================================================
    int[] arbol;
    int longitud, ocupado; 
    double crecimiento; 
    
    /**
     * Constructor: pide la capacidad inicial de la tabla y el porcentaje de 
     * crecimiento.
     */
    public TablaEnteros(int n, double c) {
        this.arbol = new int[n];
        this.longitud = n; this.ocupado = 0;
        this.crecimiento = c;
    }   
    
    /**
     * Implementa el mismo método de la superclase abstracta.
     * @param nodo nodo a insertar
     */
    void insertarFinal(int nodo) {
        if(ocupado == longitud) {
            this.longitud = (int)(longitud * (1 + crecimiento));
            int[] temp = new int[longitud];
            System.arraycopy(arbol, 0, temp, 0, ocupado);
            arbol = temp;
        }
        arbol[ocupado ++] = nodo;
    }
    
    /**
     * Procedimiento público para llamar a insertarP. Implementea el mismo método
     * de la superclase abstracta.
     * @param a Anotación a insertar
     */
    public void insertar(int nodo) {
        this.insertarP(nodo);
    }
    
    public int extraerMejor() {
        int n = arbol[ocupado - 1];
        this.arbol[-- ocupado] = 0;
        return n;
    }
    
    public int extraer(int indice) {
        int n = arbol[indice];
        eliminarPos(indice);
        return n;
    }
    
     /**
     * Procedimiento público que llama a eliminarP. Implementea el mismo método
     * de la superclase abstracta.
     * @param tit Título de la anotación a eliminar
     * @param tipo Tipo de elemento
     */
    public void eliminar(int indice) {
        this.eliminarPos(indice);
    }
    
    /**
     * Función que mediante una búsqueda dicotómica encuentra un nodo, da su posición y si no -1.
     */
    public int buscarNodo(int nodo) {
        if((this.ocupado == 0) || (nodo > arbol[0])
        || (nodo < arbol[ocupado - 1])) {
            return -1; // no encontrado
        }
        if(nodo == arbol[0]) {return 0;}
        if(nodo == arbol[ocupado - 1]) {return ocupado - 1;}
        int i = 0, j = this.ocupado;
        do {
            int m = (i + j)/2;
            if(nodo < arbol[m]) {
                i = m;
            }
            else if(nodo > arbol[m])  {
                j = m;
            }
            else {
                return m; // encontrado
            }
        } while(i < j - 1);
        return -j; // no encontrado, devolvemos la posición donde lo meteríamos porque mola más.
    }
    
    public void reemplazar(int pos, int nodo) {
        //int c = nodo.comparaGarrafas(arbol[pos]);
        int p;
        if(nodo < arbol[pos]) {
            //p = buscarLugar(nodo, pos, ocupado - 1);
            while((pos < ocupado - 1) && (nodo < arbol[pos + 1])) 
            {
                this.arbol[pos] = this.arbol[pos + 1];
                pos ++;
            }
            arbol[pos] = nodo;
        }
        else {
            //p = buscarLugar(nodo, 0, pos);
           while((pos > 0) && (nodo > arbol[pos - 1])) 
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
    private int buscarLugar(int nodo) {
        if(nodo > arbol[0]) {
            return 0; // colocar al inicio de la tabla
        }
        int i = 0, j = this.ocupado;
        do {
            int m = (i + j)/2;
            if(nodo < arbol[m]) {
                i = m;
            }
            else if(nodo > arbol[m])  {
                j = m;
            }
        } while(i < j-1);
        return j; // colocar entre i y j.
    }
    
    /**
     * Función que mediante una búsqueda dicotómica encuentra el lugar correcto de 
     * inserción de un nuevo nodo en un trozo de la tabla.
     */
    private int buscarLugar(int nodo, int i, int j) {
        if(nodo > arbol[i]) {
            return i; // colocar al inicio del trozo de tabla
        }
        //j ++;
        do {
            int m = (i + j)/2;
            if(nodo < arbol[m]) {
                i = m;
            }
            else if(nodo > arbol[m])  {
                j = m;
            }
        } while(i < j-1);
        return j; // colocar entre i y j.
    }
    
    /**
     * Procedimiento que inserta un nodo en una posición de la tabla, copiando
     * la tabla en otra mayor si se ha quedado pequeña, o desplazando los elementos
     * posteriores a dicha posición para hacer sitio al nuevo.
     * @param a Anotación que queremos insertar
     */
    protected void insertarP(int nodo) {
        if(this.ocupado == 0) {
            this.arbol[0] = nodo; 
            this.ocupado ++;
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
                this.longitud = (int)(longitud * (1 + crecimiento));
                int[] ng = new int[this.longitud];
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
            this.arbol[pos] = 0;
        }
        else {
            do {
                this.arbol[pos] = this.arbol[pos + 1];
                pos ++;
            } while(pos < ocupado);
        }
    }
}
