package model;

import java.util.ArrayList;

public class PromedioPonderado {

	public PromedioPonderado() {

	}

	public String[][] lecturaDatos(ArrayList<Elemento> datos) {
		double saldoTotal = 0;
		int cantidadTotal = 0;

		for (int i = 0; i < datos.size(); i++) {

			Elemento actual = datos.get(i);
			if (actual != null) {
				if (actual.noEsVenta()) {
					compraPP(actual, cantidadTotal, saldoTotal);
				} else {
					ventaPP(actual, cantidadTotal, saldoTotal);
				}
			}		
		}
		
		String[][] matriz = creacionMatriz(datos);
		return matriz;

	}

	public void compraPP(Elemento act, int cantidadTotal, double saldoTotal) {

		if (act.getValorUnitario() != 0 && act.getCantidadEntrada() != 0) {

			cantidadTotal = cantidadTotal + act.getCantidadEntrada();
			double entradaT = act.getCantidadEntrada() * act.getValorUnitario();
			act.setValorEntrada(entradaT);
			saldoTotal = saldoTotal + entradaT;
			act.setCantidadSaldo(cantidadTotal);
			act.setValorSaldo(cantidadTotal);
		}

	}

	public void ventaPP(Elemento act, int cantidadTotal, double saldoTotal) {

		if (cantidadTotal != 0) {
			double valorUnitario = saldoTotal / cantidadTotal;
			int cantidadS = act.getCantidadSalida();
			double saldoS = valorUnitario * cantidadS;
			cantidadTotal = cantidadTotal - cantidadS;
			saldoTotal = saldoTotal - saldoS;
			act.setValorUnitario(valorUnitario);
			act.setValorSaldo(saldoTotal);
			act.setCantidadSaldo(cantidadTotal);
			act.setValorSalida(saldoS);
		}
	}

	public String[][] creacionMatriz(ArrayList<Elemento> datos) {
		int col = datos.size();
		String[][] matriz = new String[col][10];
		
		for (int i = 0; i <datos.size(); i++) {
			
			Elemento act = datos.get(i);
			
			if (act != null) {
				
				String[] info = act.toString().split("#");
				
				if(info.length == 10) {
					
					for (int j = 0; j < matriz.length; j++) {
						for (int k = 0; k < matriz[j].length; k++) {
							matriz[j][k] = info[k];
						}
					}
					
				}
			}
		}
		
		return matriz;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
