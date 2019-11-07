package model;

public class Elemento {

	private String dia;
	private String descripcion;
	private double valorUnitario;
	private int cantidadEntrada;
	private double valorEntrada;
	private int cantidadSalida;
	private double valorSalida;
	private int cantidadSaldo;
	private double valorSaldo;
	
	public Elemento(String dia, String descripcion, double valorUnitario, int cantidadEntrada,double valorEntrada,int cantidadSalida, double valorSalida, int cantidadSaldo, double valorSaldo ) {
		this.dia = dia;
		this.descripcion = descripcion;
		this.valorUnitario = valorUnitario;
		this.cantidadEntrada = cantidadEntrada;
		this.valorEntrada = valorEntrada;
		this.cantidadSalida = cantidadSalida;
		this.valorSalida = valorSalida;
		this.cantidadSaldo = cantidadSaldo;
		this.valorSaldo = valorSaldo;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public int getCantidadEntrada() {
		return cantidadEntrada;
	}

	public void setCantidadEntrada(int cantidadEntrada) {
		this.cantidadEntrada = cantidadEntrada;
	}

	public double getValorEntrada() {
		return valorEntrada;
	}

	public void setValorEntrada(double valorEntrada) {
		this.valorEntrada = valorEntrada;
	}

	public int getCantidadSalida() {
		return cantidadSalida;
	}

	public void setCantidadSalida(int cantidadSalida) {
		this.cantidadSalida = cantidadSalida;
	}

	public double getValorSalida() {
		return valorSalida;
	}

	public void setValorSalida(double valorSalida) {
		this.valorSalida = valorSalida;
	}

	public int getCantidadSaldo() {
		return cantidadSaldo;
	}

	public void setCantidadSaldo(int cantidadSaldo) {
		this.cantidadSaldo = cantidadSaldo;
	}

	public double getValorSaldo() {
		return valorSaldo;
	}

	public void setValorSaldo(double valorSaldo) {
		this.valorSaldo = valorSaldo;
	}
	
	public boolean esVenta() {		
		return cantidadSalida != 0 && cantidadEntrada == 0 ;
	}
	
	public boolean esCompra() {		
		return valorUnitario != 0 && cantidadEntrada != 0 ;
	}
	
	public boolean esDevolucion() {		
		return cantidadSalida < 0;
	}
	
	
	public String toString() {
	
		return dia + Kardex.SEPARADOR + descripcion + Kardex.SEPARADOR+ valorUnitario + Kardex.SEPARADOR + cantidadEntrada + Kardex.SEPARADOR+ valorEntrada + Kardex.SEPARADOR + cantidadSalida + Kardex.SEPARADOR + valorSalida + Kardex.SEPARADOR + cantidadSaldo + Kardex.SEPARADOR + valorSaldo;
		
	}

	
  
}
