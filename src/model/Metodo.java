package model;

import java.util.TreeMap;

public interface Metodo {

	public String[][] calcularKardex(String[] fila);
	
	public void setDatos(TreeMap<Integer,Registro> datos);
	
}
