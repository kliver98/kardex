package model;

import java.util.ArrayList;

public class InventarioPP {
	
	public InventarioPP() {
		
	}

	public String[][] lecturaDatos(ArrayList<Registro> datos) {

		int i = 0;
		double saldoTotal = 0;
		int cantidadTotal = 0;
		Registro actual = datos.get(i);

		while (i < datos.size() && actual != null) {

			
			
			if (actual.isCompra()) {
				String cad = compraPP(actual, saldoTotal, cantidadTotal);
				String[] datosA = cad.split("#");
				actual.setValorEntrada(Double.parseDouble(datosA[0]));
				actual.setCantidadSaldo(Integer.parseInt(datosA[1]));
				actual.setValorSaldo(Double.parseDouble(datosA[2]));
				cantidadTotal = Integer.parseInt(datosA[1]);
				saldoTotal = Double.parseDouble(datosA[2]);

			} else if (actual.isVenta()) {
				
				String cad = ventaPP(actual, saldoTotal, cantidadTotal);
				
				String[] datosA = cad.split("#");	
				actual.setValorUnitario(Double.parseDouble(datosA[0]));
				actual.setValorSalida(Double.parseDouble(datosA[1]));
				actual.setCantidadSaldo(Integer.parseInt(datosA[2]));
				actual.setValorSaldo(Double.parseDouble(datosA[3]));	
				cantidadTotal = Integer.parseInt(datosA[2]);
				saldoTotal = Double.parseDouble(datosA[3]);
				
			}
			else if (actual.isDevolucion()) {
                String cad = devolucion(actual, saldoTotal, cantidadTotal);
				
				String[] datosA = cad.split("#");	
				actual.setValorUnitario(Double.parseDouble(datosA[0]));
				actual.setValorSalida(Double.parseDouble(datosA[1]));
				actual.setCantidadSaldo(Integer.parseInt(datosA[2]));
				actual.setValorSaldo(Double.parseDouble(datosA[3]));	
				cantidadTotal = Integer.parseInt(datosA[2]);
				saldoTotal = Double.parseDouble(datosA[3]);
			}
			else if (actual.isDevolucionC()) {
				String cad = devolucionC(actual, saldoTotal, cantidadTotal);
				String[] datosA = cad.split("#");	
				//String datos = valorU + "#"+ valorE + "#" + cantidadTotal + "#" +  saldoTotal;
				
				actual.setValorUnitario(Double.parseDouble(datosA[0]));
				actual.setValorEntrada(Double.parseDouble(datosA[1]));
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
	
	public String compraPP(Registro act,double saldoTotal, int cantidadTotal) {
		double entradaT = act.getCantidadEntrada() * act.getValorUnitario();
		int cantidadE = act.getCantidadEntrada();
		saldoTotal += entradaT;
		cantidadTotal += cantidadE;
		String datos = entradaT + "#" + cantidadTotal + "#" +  saldoTotal;
		
		return datos;
	}
	
	public String ventaPP(Registro act, double saldoTotal, int cantidadTotal) {
		
		int cantidadS = act.getCantidadSalida();
		double valorU = saldoTotal / cantidadTotal;
		double valorS= valorU*cantidadS;
		if(cantidadTotal > cantidadS) {
			saldoTotal -= valorS;
			cantidadTotal -= cantidadS;
		}
		else {
			valorS = 0;
			act.setCantidadSalida(0);
		}
		
		
		String datos = valorU + "#"+ valorS + "#" + cantidadTotal + "#" +  saldoTotal;
		
		return datos;
	}
	
	public String devolucion(Registro act, double saldoTotal, int cantidadTotal) {
		int cantidadS = act.getCantidadSalida();
		double valorU = act.getValorUnitario();
		double valorS = act.getValorSalida();;
		
		saldoTotal -= valorS;
		cantidadTotal -= cantidadS;
		
		String datos = valorU + "#"+ valorS + "#" + cantidadTotal + "#" +  saldoTotal;
		return datos;
		
	}
	
	public String devolucionC (Registro act, double saldoTotal, int cantidadTotal) {
		
		int cantidadE = act.getCantidadEntrada();
		double valorU = act.getValorUnitario();	
		double valorE = act.getValorEntrada();
		
		System.out.println(valorE + "valorE >>>>>> ");
		saldoTotal += valorE;
		cantidadTotal += cantidadE;
		
		String datos = valorU + "#"+ valorE + "#" + cantidadTotal + "#" +  saldoTotal;
		return datos;
		
	}



	public String[][] creacionMatriz(ArrayList<Registro> datos) {
		int col = datos.size();
		String[][] matriz = new String[col][9];

		for (int i = 0; i < matriz.length; i++) {
			Registro act = datos.get(i);
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
