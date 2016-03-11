package com.nacho.jugsgraphic.GarrafasEngine;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Esta clase contiene algunas funciones auxiliares para el funcionamiento del 
 * programa. Es pública, ya que algunas funciones pueden ser de utilidad en otros 
 * problemas.
 * @author (Ignacio García)
 */
public class Funciones { 
    //                       CAMPOS Y MÉTODOS PÚBLICOS
    //============================================================================ 
    public static void main(String[] args) {
        System.out.println(construyeLong(new int[]{2,4,5,9},3));
    }    
    public static String[] parte(String sr, String patron) {
        StringBuilder s = new StringBuilder();
        s.append(sr);
        return s.toString().split(patron);
    }
    public static boolean matchea(String sr, String patron) {
        return sr.matches(patron);
    }
    
    public static void ponMeta(int sol) {meta = sol;}
    /*public int pruebacomp2() {
        NodoGarrafas n1 = new NodoGarrafas(new int[] {3,4,8,5}),
        n2 = new NodoGarrafas(n1);
        n2.llenar(0);
        return n2.comparaGarrafas(n1);
    }*/
    
    /*public int pruebacomp3(NodoGarrafas n) {
        n2.llenar(0);
        return n2.comparaGarrafas(n1);
    }*/
    
    public static String prueba(String s, String a) {
        return s.substring(s.lastIndexOf(a, s.length()));
    }
    
    public static int abs(int a) {
        if(a < 0) {return -a;}
        else {return a;}
    }
    
    public static double abs(double a) {
        if(a < 0) {return -a;}
        else {return a;}
    }
    
    public static int ncifras(int n) {
        int i = 0;
        while(n > 0) {
            n = n/10;
            i ++;
        }
        return i;
    }
    
    public static long construyeLong(int[] tabla, int ncifras) {
        long largo = 0L;
        /*for(int i = tabla.length-1; i >= 0; i --) {
            largo += tabla[i]*Math.pow(10, (tabla.length-1-i)*ncifras);
        }*/
        for(int i = 0; i < tabla.length; i ++) {
            largo += tabla[i]*Math.pow(10, i*ncifras);
        }
        return largo;
    }
    
    public static int meta;
    
    public static void iniciaVectorEnteros(int[] vector, int valor) {
        for(int i = 0; i < vector.length; i ++) {
            vector[i] = valor;
        }
    }
    
    public static String numeroNcifras(int n, int cifras) {
        StringBuilder s = new StringBuilder();
        float n1 = n; int c = 0;
        if(n == 0) {c = 1;}
        else {
            while(n1 >= 1) {
                n1 = n1 / 10;
                c ++;
            }
        }
        for(int i = 0; i < cifras - c; i ++) {
            s.append(" ");
        }
        s.append(n);
        return s.toString();
    }
    
    private static double raizCubicaR(double n, double inf, double sup) { // llamada inicial con n/2
        double medio = (inf + sup)/2;
        double actual = medio*medio*medio;
        if(abs(abs(actual) - n) < 0.1) {return medio;}
        else if(abs(actual) < n) {return raizCubicaR(n, medio, sup);}
        else {return raizCubicaR(n, inf, medio);}
    }
    
    /*public static double raizCubica(double n) {
        return raizCubicaR(n, 0, n);
    }*/
    
    public static double raizCubica(double n) {
        double d = 1/2;
        return Math.pow(n, 0.333333);
    }
    
    public static void copiaTablaEnteros(int[] origen, int comienzoOrigen, int[] destino, 
    int comienzoDestino, int cuantos) {
        int io = comienzoOrigen, id = comienzoDestino;
        for(int count = 0; count < cuantos; count ++) {
            destino[id ++] = origen[io ++];
        }
    }
    
    /*public int pruebaComparacion(NodoGarrafas n1, NodoGarrafas n2) {
        return n1.comparaGarrafas(n2);
    }*/
    
    /**
     * Función que lee un carácter por teclado, y entra en un bucle hasta que el
     * carácter leído esté en una lista de permitidos. Usa la función "esta".
     * @param msjePrompt El mensaje inicial que se da pidiendo el carácter.
     * @param permitidos Un vector de char con los caracteres permitidos.
     * @return el carácter leído, una vez que haya cumplido las condiciones.
     */
    public static char leeCaracterCondicionado(
    String msjePrompt, char... permitidos) {
        char c = ' '; 
        StringBuilder s = new StringBuilder();
        do {
            try {
                System.out.println(msjePrompt);
                String sr = lector.readLine();
                if(sr != null) {
                    c = sr.charAt(0);
                }
            }
            catch(IOException e) {
                s.append("error de lectura de consola; vuelve a intentarlo");
                System.out.println(s);
                continue;
            }
            if (esta(c, permitidos)) {
                return c;
            }
            else {
                System.out.println("error: opción no valida, vuelve a intentarlo:");
            }
        } while (true);
    }
    
    /**
     * Función que lee un texto por teclado. El texto no puede ser vacío.
     * @param msjePrompt Mensaje inicial pidiendo el texto.
     * @return el texto una vez cumplida la condición.
     */
    public static String leeTexto(String msjePrompt) {
        String sr = null;
        StringBuilder s;
        do { 
            try {
                System.out.println(msjePrompt);
                sr = lector.readLine(); // leemos por consola la respuesta
            }
            catch(IOException e) {
                s = new StringBuilder();
                s.append("error de lectura de consola; vuelve a intentarlo:");
                System.out.println(s);
                continue;
            }
        }  while(sr == null);
        return sr;
    }
    
    /**
     * Función que lee un texto por teclado, haciendo un bucle hasta que el texto
     * leído cumpla una expresión regular dada como parámetro.
     * @param msjePrompt Mensaje inicial pidiendo el texto.
     * @param patron Expresión regular en forma String que debe cumplir el texto
     * introducido.
     * @return el texto cuando cumpla la expresión regular.
     */
    public static String leeTextoCondicionado (String msjePrompt, String patron) {
        String sr = null;
        StringBuilder s;
        do { 
            try {
                System.out.println(msjePrompt);
                sr = lector.readLine(); // leemos por consola la respuesta
            }
            catch(IOException e) {
                s = new StringBuilder();
                s.append("error de lectura de consola; vuelve a intentarlo:"); 
                System.out.println(s);
                continue;
            }
            if(sr.matches(patron)) {
                return sr;
            }
            else {
                s = new StringBuilder();
                s.append("error, el texto no cumple el patrón dado, vuelve a ");
                s.append("intentarlo:");
                System.out.println(s);
            }
        }  while(true);
    }
    
    /**
     * Función que lee un entero por teclado, haciendo un bucle hasta que el entero
     * leído esté entre los límites dados como parámetros.
     * @param msjePrompt El mensaje inicial pidiendo el número
     * @param min Límite inferior para el número
     * @param max Límite superior para el número
     * @return el número leído cuando cumpla las condiciones.
     */
    public static int leeEnteroCondicionado (String msjePrompt, int min, int max) {
        String sr = null;
        StringBuilder s; int n = 0;
        do { 
            try {
                System.out.println(msjePrompt);
                sr = lector.readLine(); // leemos por consola la respuesta
            }
            catch(IOException e) {
                s = new StringBuilder();
                s.append("error de lectura de consola; vuelve a intentarlo:"); 
                System.out.println(s);
                continue;
            }
            try {
                if(((n = Integer.parseInt(sr)) < min) && (n > max)) {
                    System.out.println("error, número fuera de rango");
                    continue;
                }
                else {
                    return n;
                   }
            }               
            catch(NumberFormatException e) {
                s = new StringBuilder();
                s.append("error: eso no es un número válido, vuelve a ");
                s.append("intentarlo:"); 
                System.out.println(s);   
            }
        }  while(true);
    }
    
    /**
     * Función que lee un double por teclado, haciendo un bucle hasta que el número
     * leído esté entre los límites dados como parámetros.
     * @param msjePrompt El mensaje inicial pidiendo el número
     * @param min Límite inferior para el número
     * @param max Límite superior para el número
     * @return el número leído cuando cumpla las condiciones.
     */
    public static double leeDoubleCondicionado (
    String msjePrompt, int min, int max) {
        String sr = null;
        StringBuilder s; double n = 0;
        do { 
            try {
                System.out.println(msjePrompt);
                sr = lector.readLine(); // leemos por consola la respuesta
            }
            catch(IOException e) {
                s = new StringBuilder();
                s.append("error de lectura de consola; vuelve a intentarlo:"); 
                System.out.println(s);
                continue;
            }
            try {
                if(((n = Double.parseDouble(sr)) < min) && (n > max)) {
                    System.out.println("error, número fuera de rango");
                    continue;
                }
                else {
                    return n;
                   }
            }               
            catch(NumberFormatException e) {
                s = new StringBuilder();
                s.append("error: eso no es un número válido, vuelve a ");
                s.append("intentarlo:"); 
                System.out.println(s);   
            }
        }  while(true);
    }
    
    //                CAMPOS Y MÉTODOS PRIVADOS O PROTEGIDOS
    //============================================================================
    
    /**
     * Objeto para leer datos por teclado
     */
    private static BufferedReader lector = new BufferedReader (new InputStreamReader 
    (System.in));
    
    /**
     * Función que dice si un carácter está o no en un vector de caracteres.
     * @param c El carácter
     * @param permit El vector de caracteres
     * @return true si c está en permit, false en otro caso.
     */
    private static boolean esta(char c, char[] permit) {
        int i = 0;
        do {
            if(permit[i] == c) {
                return true;
            }
            i ++;
        } while(i < permit.length);
        return false;
    }

    public static int maxInVector(int[] vector) {
        int max = 0;
        for (int i = 0; i < vector.length; i++) {
            if(vector[i] >  max) {
                max = vector[i];
            }
        }
        return  max;
    }
}