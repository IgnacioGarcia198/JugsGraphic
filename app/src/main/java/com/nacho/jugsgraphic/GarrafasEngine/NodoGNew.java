package com.nacho.jugsgraphic.GarrafasEngine;

/**
 * Created by nacho on 8/19/15.
 */
public class NodoGNew implements Comparable<NodoGNew>
{

	@Override
	public int compareTo(NodoGNew n)
	{
		// TODO: Implement this method
		//NodoGNew n = (NodoGNew) p1;
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
    StringBuilder ruta;
    static int ngarrafas;
    static int[] capacidad;
    int[] contenido;
    //StringBuilder ruta;
    int prof;
    int lastorigin, lastDest, lastOp;
    NodoGNew parent;
	

    public NodoGNew(int[] tabl) {
        ngarrafas = tabl.length;
        capacidad = tabl;
        contenido = new int[ngarrafas];
        ruta = new StringBuilder();
        prof = 0;
        lastDest = 0;
        lastorigin = 0;
        lastOp = FILL;
    }

    private NodoGNew() {};

    public void copiaGarrafas(NodoGNew origen) {
        for(int i = 0; i < NodoGNew.ngarrafas; i ++) {
            contenido[i] = origen.contenido[i];
        }
    }

    public NodoGNew(NodoGNew n) {
        this.contenido = new int[ngarrafas];
        this.copiaGarrafas(n);
        this.ruta = new StringBuilder(n.ruta);
        this.prof = n.prof + 1;
        lastDest = 0;
        lastorigin = 0;
        lastOp = POUR;
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

    public NodoGNew nextChild() {
		
        
        boolean sib = false;
        while(true) {
			if(lastorigin >= ngarrafas) {
				return null;
			}
			NodoGNew n = new NodoGNew(this);
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
				/*else if(repe) {
					System.out.println("repe: ");
					System.out.println(n.nodoTexto());
				}*/
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
				/*else if(repe) {
					System.out.println("repe: ");
					System.out.println(n.nodoTexto());
				}*/
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
				/*else if(repe) {
					System.out.println("repe: ");
					System.out.println(n.nodoTexto());
				}*/
				
                // else
                //continue;

            }
        }
        //return null;
    }

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
		NodoGNew current = this;
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
	

}
