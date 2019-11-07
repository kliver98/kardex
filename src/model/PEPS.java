package model;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class PEPS {

	private TreeMap<Integer,Elemento> datos;
	
	public PEPS() {
		
	}
	
	public String[][] calcular() {
		String[][] result = new String[datos.keySet().size()][9];
		
		InventarioPEPS inv = new InventarioPEPS();
		int i = 0;
		for (Integer key : datos.keySet()) {
			Elemento e = datos.get(key);
			boolean entry = !(e.getCantidadEntrada()+"").equalsIgnoreCase("0");
			if (entry) { //Son datos para una entrada
				inv.agregarProducto(e.getCantidadEntrada(),e.getValorUnitario());
				e.setCantidadSaldo(inv.getCantidadDisponible());
				e.setValorSaldo(inv.getSaldo());
			} else { // Son datos para una salida. Devolucion es con otro metodo antes
				double precioC = inv.sacarProductos(e.getCantidadSalida());
				e.setCantidadSaldo(e.getCantidadSalida()); //Cuantas unidades vendio
				e.setValorSaldo(precioC); //Precio de sacar todas esas unidades
			}
		}
		Iterator<Integer> k = datos.keySet().iterator();
		while (k.hasNext()) {
			int key = k.next();
			Elemento e = datos.get(key);
			String[] datos = e.toString().split(Kardex.SEPARADOR);
			result[i] = datos;
			i+=1;
		}
		for (int j = 0; j < result.length; j++) {
			for (int j2 = 0; j2 < result[j].length; j2++) {
				System.out.print(result[j][j2]+" ");
			}
			System.out.println();
		}
		System.out.println("-----------");
		return result;
	}
	
	public void setDatos(TreeMap<Integer,Elemento> datos) {
		this.datos = datos;
	}
	
}
