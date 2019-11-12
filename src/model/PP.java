package model;

import java.util.ArrayList;
import java.util.TreeMap;

public class PP implements Metodo {

	private TreeMap<Integer, Registro> datos;
	private ArrayList<Registro> registros;
	private InventarioPP promedioP;

	public PP() {
		datos = new TreeMap<Integer, Registro>();
	}

	@Override
	public String[][] calcularKardex(String[] fila) {
		addDatos(fila);
		String[][] matriz = promedioP.lecturaDatos(registros);
		return matriz;

		// Fanny este metodo es el debe devolver la matriz de Strings y con estos datos
		// se llenara la tabla de la vista
		// LO QUE LE DEVUELVA ESTE METODO; ESO SE PINTARA EN LA TABLA
		// EJEMPLO DE MATRIZ - LO HAGO COMO SI FUERA PEPS -
		// String[][] resultado = {
		// {"1","compra1","2.0","3","6.0","0","0","3","6.0"},
		// {"4","venta1","0.0","0","0","2","4.0","1","2.0"}
		// };

	}

	public void addDatos(String[] datos) {
		// String dia, String descripcion, int cantidad, double valorU
		String dia = datos[0];
		String descripcion = datos[1];
		double valorU = 0;
		if (datos[2] != "") {
			System.out.println(datos[2] + "Entro");
			valorU = Double.parseDouble(datos[2]);
		}
		int cantidadEntrada = 0;
		int cantidadS = 0;
		if (datos[5] != "" || datos[3] != "") {
			cantidadS = Integer.parseInt(datos[5]);
			cantidadEntrada = Integer.parseInt(datos[3]);
		}
		if (valorU != 0) {
			Registro agregar = new Registro(dia, descripcion, "" + valorU, "" + cantidadEntrada, "" + 0, "" + 0, "" + 0,
					"" + 0, "" + 0);
			registros.add(agregar);
		} else if (valorU == 0) {

			if (cantidadS != 0) {
				Registro agregar = new Registro(dia, descripcion, "" + 0, "" + 0, "" + 0, "" + cantidadS, "" + 0,
						"" + 0, "" + 0);
				registros.add(agregar);
			} else {
				Registro agregarL = search(dia);
				int cant = agregarL.getCantidadSalida() * -1;
				Registro agreg = new Registro("" + agregarL.getDia(), "" + agregarL.getDescripcion(), "" + 0, "" + 0,
						"" + 0, "" + cant, "" + 0, "" + 0, "" + 0);
				registros.add(agreg);
			}

		}
	}

	public Registro search(String dia) {

		Registro search = null;
		boolean encontrado = false;
		int i = 0;
		while (i < registros.size() && encontrado == false) {
			if (registros.get(i).getDia().equals(dia)) {
				search = registros.get(i);
				encontrado = true;
			}
			i++;
		}
		return search;
	}

	@Override
	public void setDatos(TreeMap<Integer, Registro> datos) {
		this.datos = datos;
	}

}
