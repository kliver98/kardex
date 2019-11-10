package model;

import java.util.LinkedList;

public class InventarioPEPS {

	class Par {
		private int cantidad;
		private double valor;
		public Par(int c, double v) {
			cantidad = c;
			valor = v;
		}
		public void setCantidad(int nueva) {
			cantidad = nueva;
		}
		public int getCantidad() {
			return cantidad;
		}
		public double getValor() {
			return valor;
		}
		@Override
		public String toString() {
			return cantidad+" c/u "+valor;
		}
	}
	
	private LinkedList<Par> inventario;
	private int cantidadTotal;
	private double saldoTotal;
	
	public InventarioPEPS() {
		inventario = new LinkedList<Par>();
		cantidadTotal = 0;
		saldoTotal = 0;
	}
	
	public void agregarProducto(int cantidad, double valor) {
		saldoTotal += valor*cantidad;
		cantidadTotal+=cantidad;
		inventario.add(new Par(cantidad,valor));
	}
	
	public double sacarProductos(int cantidad) {
		cantidadTotal-=cantidad;
		double precio = 0;
		Par aux = inventario.peek();
		int resta = aux.getCantidad()-cantidad;
		if (resta>0) {
			aux.setCantidad(resta);
			precio = cantidad*aux.getValor();
			saldoTotal-=precio;
			return precio;
		}
		while(resta<0) { //Es por que me faltan unidades y las de aux no me satisfacen
			precio += aux.getCantidad()*aux.getValor();
			inventario.poll();
			aux = inventario.peek();
			resta = aux.getCantidad()+resta;
			if (resta>=0) {
				precio += aux.getValor()*(+aux.getCantidad()-resta);
				aux.setCantidad(resta);
			}
		}
		saldoTotal-=precio;
		return precio;
	}
	
	public int getCantidadDisponible() {
		return cantidadTotal;
	}
	
	public double getSaldo(){
		return saldoTotal;
	}
	
	public void setSaldo(double n) {
		saldoTotal = n;
	}
	
	public void setCantidad(int n) {
		cantidadTotal = n;
	}
	
}
