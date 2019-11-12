package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PromedioPonderado {

//	private static double saldoTotal = 0;
//	private static int cantidadTotal = 0;
	ArrayList<Double> saldoT;
	ArrayList<Integer> cantidadTT;

	public PromedioPonderado() {
		saldoT = new ArrayList<>();
		cantidadTT = new ArrayList<>();
		
	}

	public String[][] lecturaDatos(ArrayList<Elemento> datos) {

		int i = 0;
		double saldoTotal = 0;
		int cantidadTotal = 0;
		Elemento actual = datos.get(i);

		while (i < datos.size() && actual != null) {

			
			
			if (actual.esCompra()) {
				String cad = compraPP(actual, saldoTotal, cantidadTotal);
				String[] datosA = cad.split("#");
				actual.setValorEntrada(Double.parseDouble(datosA[0]));
				actual.setCantidadSaldo(Integer.parseInt(datosA[1]));
				actual.setValorSaldo(Double.parseDouble(datosA[2]));
				cantidadTotal = Integer.parseInt(datosA[1]);
				saldoTotal = Double.parseDouble(datosA[2]);

			} else if (actual.esVenta()) {
				
				String cad = ventaPP(actual, saldoTotal, cantidadTotal);
				
				String[] datosA = cad.split("#");	
				actual.setValorUnitario(Double.parseDouble(datosA[0]));
				actual.setValorSalida(Double.parseDouble(datosA[1]));
				actual.setCantidadSaldo(Integer.parseInt(datosA[2]));
				actual.setValorSaldo(Double.parseDouble(datosA[3]));	
				cantidadTotal = Integer.parseInt(datosA[2]);
				saldoTotal = Double.parseDouble(datosA[3]);
				
			}
			else if (actual.esDevolucion()) {
                String cad = devolucion(actual, saldoTotal, cantidadTotal);
				
				String[] datosA = cad.split("#");	
				actual.setValorUnitario(Double.parseDouble(datosA[0]));
				actual.setValorSalida(Double.parseDouble(datosA[1]));
				actual.setCantidadSaldo(Integer.parseInt(datosA[2]));
				actual.setValorSaldo(Double.parseDouble(datosA[3]));	
				cantidadTotal = Integer.parseInt(datosA[2]);
				saldoTotal = Double.parseDouble(datosA[3]);
			}

			i++;
			if (i < datos.size()) {
				actual = datos.get(i);
			}

		}

		String[][] matriz = creacionMatriz(datos);
		return matriz;

	}
	
	public String compraPP(Elemento act,double saldoTotal, int cantidadTotal) {
		double entradaT = act.getCantidadEntrada() * act.getValorUnitario();
		int cantidadE = act.getCantidadEntrada();
		saldoTotal += entradaT;
		cantidadTotal += cantidadE;
		String datos = entradaT + "#" + cantidadTotal + "#" +  saldoTotal;
		
		return datos;
	}
	
	public String ventaPP(Elemento act, double saldoTotal, int cantidadTotal) {
		
		int cantidadS = act.getCantidadSalida();
		double valorU = saldoTotal / cantidadTotal;
		double valorS= valorU*cantidadS;
		saldoTotal -= valorS;
		cantidadTotal -= cantidadS;
		
		String datos = valorU + "#"+ valorS + "#" + cantidadTotal + "#" +  saldoTotal;
		//System.out.println(datos + "<<<<<<<<<<<< datos >>>>>>>>>>>");
		return datos;
	}
	
	public String devolucion(Elemento act, double saldoTotal, int cantidadTotal) {
		int cantidadS = act.getCantidadSalida();
		double valorU = saldoTotal / cantidadTotal;
		double valorS= valorU*cantidadS;
		
		saldoTotal += valorS;
		cantidadTotal += cantidadS;
		
		String datos = valorU + "#"+ valorS + "#" + cantidadTotal + "#" +  saldoTotal;
		System.out.println(datos + "<<<<<<<<<<<< datos >>>>>>>>>>>");
		return datos;
		
	}



	public String[][] creacionMatriz(ArrayList<Elemento> datos) {
		int col = datos.size();
		String[][] matriz = new String[col][9];

		for (int i = 0; i < matriz.length; i++) {
			Elemento act = datos.get(i);
			for (int j = 0; j < matriz[i].length; j++) {
				if (act != null) {
					String[] info = act.toString().split("#");
					matriz[i][j] = info[j];
				}
			}
		}

		return matriz;
	}

}
