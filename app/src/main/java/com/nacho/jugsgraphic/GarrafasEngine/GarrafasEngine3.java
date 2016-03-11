package com.nacho.jugsgraphic.GarrafasEngine;

import java.util.*;

/**
 * Created by nacho on 8/19/15.
 */
public class GarrafasEngine3 {
    NodoGNew raiz;
    static int cotaSuperiorInicial = 10;
	Scanner input = new Scanner(System.in);
	int[] caps;

    public GarrafasEngine3(int goal, int[] caps) {
        Funciones.meta = goal;
        raiz = new NodoGNew(caps);
		this.caps = caps;
    }

    public NodoGNew search() {
        return dfs();
    }

    NodoGNew dfs() {
        int cotaSuperior = cotaSuperiorInicial + 1;
        NodoGNew actual = raiz;
        while(actual != null) {
            System.out.println("prof: " + actual.prof);
            int prueba = actual.pruebaMeta(); // test goal
            if(prueba >= 0) {
                return actual;
            }
            NodoGNew firstChild = actual.nextChild(); // sacamos el siguiente hijo
            if((actual.prof >= cotaSuperior) || (firstChild == null)) { // nos hemos pasado de profundidad o bien no kedan hijos del nodo
                if(actual.parent != null) {
                    NodoGNew nextSibling = actual.parent.nextChild(); // buscamos al siguiente hermano del nodo
                        while (nextSibling == null) {
                            System.out.println("no tengo hermano");
                            if (actual.parent != null) { // si no lo encontramos, buscamos hermanos de sus predecesores
                                System.out.println("pero tengo padre");
                                actual = actual.parent;
                            } else {
                                // WHOLE GRAPH EXPLORED
                                return null;
                            }
                            nextSibling = actual.parent.nextChild();
                            //if(nextSibling)
                        }
                    actual = nextSibling;
                    System.out.println("Tiene hermano");
                }
            }

            else {
                System.out.println("Tiene hijo");
                actual = firstChild;
            }

        }

        return null;
    }

    NodoGNew dfs2(int cotaSuperior) {
        //int cotaSuperior = cotaSuperiorInicial + 1;
        NodoGNew actual = raiz;
		NodoGNew sol = null;
        while(actual != null) {
			//input.next();
            System.out.println("prof: " + actual.prof);
            System.out.println("lastorigin: " + actual.lastorigin);
            System.out.println(actual.nodoTexto());
            int prueba = actual.pruebaMeta(); // test goal
            if(prueba >= 0) {
                sol = actual;
				cotaSuperior = sol.prof;
				//return actual;
            }

            NodoGNew newChild = actual.nextChild(); // sacamos el siguiente hijo


            if((actual.prof >= cotaSuperior) || (newChild == null)) { // nos hemos pasado de profundidad o bien no kedan hijos del nodo

                if(actual.parent != null) {
                    NodoGNew nextSibling = actual.parent.nextChild(); // buscamos al siguiente hermano del nodo
                    System.out.println("inner lastorigin: " + actual.lastorigin);
                    if(nextSibling != null) {
                     // System.out.println(nextSibling.nodoTexto());
					  //System.out.println("parent:");
						//System.out.println(actual.parent.nodoTexto());
						//System.out.println(nextSibling.parent.nodoTexto());
					}
					else {
						System.out.println("NULL CHILD");
						//return null;
					}
                    while (nextSibling == null) {
                        System.out.println("no tengo hermano");
                        if (actual.parent != null) { // si no lo encontramos, buscamos hermanos de sus predecesores
                            System.out.println("pero tengo padre");
							nextSibling = actual.parent.nextChild();
                            actual = actual.parent;
                        } else {
                            // WHOLE GRAPH EXPLORED
                            return sol;
                        }
                        
                        //if(nextSibling)
                    }
                    actual = nextSibling;
                }
                else {
                    return sol;
                }
            }


            else {
                System.out.println("Tiene hijo");
                actual = newChild;
            }

        }

        return sol;
    }
	
	NodoGNew dfs3(int cotaSuperior) {
        //int cotaSuperior = cotaSuperiorInicial + 1;
		System.out.println("cota superior: " + cotaSuperior);
        NodoGNew actual = new NodoGNew(caps);
		//NodoGNew sol = null;
        while(actual != null) {
			//input.next();
            System.out.println("prof: " + actual.prof);
            System.out.println("lastorigin: " + actual.lastorigin);
            System.out.println(actual.nodoTexto());
            int prueba = actual.pruebaMeta(); // test goal
            if(prueba >= 0) {
                //sol = actual;
				//cotaSuperior = sol.prof;
				return actual;
            }

            NodoGNew newChild = actual.nextChild(); // sacamos el siguiente hijo


            if((actual.prof >= cotaSuperior) || (newChild == null)) { // nos hemos pasado de profundidad o bien no kedan hijos del nodo

                if(actual.parent != null) {
                    NodoGNew nextSibling = actual.parent.nextChild(); // buscamos al siguiente hermano del nodo
                    System.out.println("inner lastorigin: " + actual.lastorigin);
                    if(nextSibling != null) {
						// System.out.println(nextSibling.nodoTexto());
						//System.out.println("parent:");
						//System.out.println(actual.parent.nodoTexto());
						//System.out.println(nextSibling.parent.nodoTexto());
					}
					else {
						System.out.println("NULL CHILD");
						//return null;
					}
                    while (nextSibling == null) {
                        System.out.println("no tengo hermano");
                        if (actual.parent != null) { // si no lo encontramos, buscamos hermanos de sus predecesores
                            System.out.println("pero tengo padre");
							nextSibling = actual.parent.nextChild();
                            actual = actual.parent;
                        } else {
                            // WHOLE GRAPH EXPLORED
                            return null;
                        }

                        //if(nextSibling)
                    }
                    actual = nextSibling;
                }
                else {
                    return null;
                }
            }


            else {
                System.out.println("Tiene hijo");
                actual = newChild;
            }

        }

        return null;
    }
	
	public NodoGNew dfsi() {
		NodoGNew n = null;
		for(int i = 1; i <= cotaSuperiorInicial; i ++) {
			System.out.println("COTA: " + i);
			n = dfs3(i);
			if(n != null) {
				System.out.println("sollll");
				System.out.println(n.nodoTexto());
				return n;
			}
		}
		return null;
	}

    NodoG2 dfs4() {
        int cotaSuperior = cotaSuperiorInicial + NodoG2.cotaInicial();
        NodoG2 actual = new NodoG2(caps);
        NodoG2 sol = null;
        while(actual != null) {
            //input.next();
            System.out.println(actual.nodoTexto());
            int prueba = actual.pruebaMeta(); // test goal
            if(prueba >= 0) {
                sol = actual;
                cotaSuperior = sol.prof;
                //return actual;
            }

            NodoG2 newChild = null;
            if(!actual.remChildren) {
                newChild = actual.nextChild(); // sacamos el siguiente hijo
            }

            if((actual.cotaInferior() >= cotaSuperior) || (newChild == null)) { // nos hemos pasado de profundidad o bien no kedan hijos del nodo

                if(actual.parent != null) {
                    NodoG2 nextSibling = actual.parent.nextChild(); // buscamos al siguiente hermano del nodo
                    //System.out.println("inner lastorigin: " + actual.lastorigin);
                    if(nextSibling != null) {
                        // System.out.println(nextSibling.nodoTexto());
                        //System.out.println("parent:");
                        //System.out.println(actual.parent.nodoTexto());
                        //System.out.println(nextSibling.parent.nodoTexto());
                    }
                    else {
                        System.out.println("NULL CHILD");
                        //return null;
                    }
                    while (nextSibling == null) {
                        System.out.println("no tengo hermano");
                        if (actual.parent != null) { // si no lo encontramos, buscamos hermanos de sus predecesores
                            System.out.println("pero tengo padre");
                            nextSibling = actual.parent.nextChild();
                            actual = actual.parent;
                        } else {
                            // WHOLE GRAPH EXPLORED
                            return sol;
                        }

                        //if(nextSibling)
                    }
                    actual = nextSibling;
                }
                else {
                    return sol;
                }
            }


            else {
                System.out.println("Tiene hijo");
                actual = newChild;
            }

        }

        return sol;
    }
}
