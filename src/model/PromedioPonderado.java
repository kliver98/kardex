package model;

import java.util.ArrayList;

public class PromedioPonderado {

	private static double saldoTotal = 0;
	private static int cantidadTotal = 0;
	public PromedioPonderado() {

	}

	public String[][] lecturaDatos(ArrayList<Elemento> datos) {
		
		int i = 0;
		Elemento actual = datos.get(i);
		

		while( i < datos.size() && actual != null) {	
		
				if (actual.noEsVenta()) {
					compraPP(actual);
					
					
				} else {
					ventaPP(actual);
				}
				
				System.out.println(saldoTotal +"saldo total general");
				i++;
				if(i < datos.size()) {
					actual = datos.get(i);
				}
				
				
			
		}
		
		String[][] matriz = creacionMatriz(datos);
		return matriz;

	}

	public void compraPP(Elemento act) {

		//if (act.getValorUnitario() != 0 && act.getCantidadEntrada() != 0) {]
			

			cantidadTotal = cantidadTotal + act.getCantidadEntrada();
			double entradaT = act.getCantidadEntrada() * act.getValorUnitario();
			act.setValorEntrada(entradaT);
			saldoTotal = saldoTotal + entradaT;
			act.setCantidadSaldo(cantidadTotal);
			act.setValorSaldo(saldoTotal);
			System.out.println(act.getDia()+ "dia" + "saldo total:" + saldoTotal);
		
			

	}

	public void ventaPP(Elemento act) {

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
