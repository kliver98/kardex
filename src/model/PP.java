package model;

import java.util.TreeMap;

public class PP implements Metodo {

	private TreeMap<Integer,Registro> datos;
	
	public PP() {
		datos = new TreeMap<Integer,Registro>();
	}

	@Override
	public String[][] calcularKardex(String[] fila) {
		//Fanny este metodo es el debe devolver la matriz de Strings y con estos datos se llenara la tabla de la vista
		//LO QUE LE DEVUELVA ESTE METODO; ESO SE PINTARA EN LA TABLA
		//EJEMPLO DE MATRIZ - LO HAGO COMO SI FUERA PEPS -
		//String[][] resultado = {
		//{"1","compra1","2.0","3","6.0","0","0","3","6.0"},
		//{"4","venta1","0.0","0","0","2","4.0","1","2.0"}
		//};
		return null;
	}

	@Override
	public void setDatos(TreeMap<Integer, Registro> datos) {
		this.datos = datos;
	}
	
}
