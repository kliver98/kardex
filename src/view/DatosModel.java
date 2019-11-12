package view;

import javafx.beans.property.SimpleStringProperty;

public class DatosModel {

	private SimpleStringProperty dia;
	private SimpleStringProperty descripcion;
	private SimpleStringProperty valorUnitario;
	private SimpleStringProperty cantEntrada;
	private SimpleStringProperty valorEntrada;
	private SimpleStringProperty cantSalidas;
	private SimpleStringProperty valorSalidas;
	private SimpleStringProperty cantSaldo;
	private SimpleStringProperty valorSaldo;
	
	public DatosModel(String dia, String descripcion, String valorUnitario,
			String cantEntrada, String valorEntrada, String cantSalidas,
			String valorSalidas, String cantSaldo, String valorSaldo) {
		super();
		this.dia = new SimpleStringProperty(dia);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.valorUnitario = new SimpleStringProperty(valorUnitario);
		this.cantEntrada = new SimpleStringProperty(cantEntrada);
		this.valorEntrada = new SimpleStringProperty(valorEntrada);
		this.cantSalidas = new SimpleStringProperty(cantSalidas);
		this.valorSalidas = new SimpleStringProperty(valorSalidas);
		this.cantSaldo = new SimpleStringProperty(cantSaldo);
		this.valorSaldo = new SimpleStringProperty(valorSaldo);
	}

	public SimpleStringProperty getDia() {
		return dia;
	}

	public void setDia(SimpleStringProperty dia) {
		this.dia = dia;
	}

	public SimpleStringProperty getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(SimpleStringProperty descripcion) {
		this.descripcion = descripcion;
	}

	public SimpleStringProperty getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(SimpleStringProperty valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public SimpleStringProperty getCantEntrada() {
		return cantEntrada;
	}

	public void setCantEntrada(SimpleStringProperty cantEntrada) {
		this.cantEntrada = cantEntrada;
	}

	public SimpleStringProperty getValorEntrada() {
		return valorEntrada;
	}

	public void setValorEntrada(SimpleStringProperty valorEntrada) {
		this.valorEntrada = valorEntrada;
	}

	public SimpleStringProperty getCantSalidas() {
		return cantSalidas;
	}

	public void setCantSalidas(SimpleStringProperty cantSalidas) {
		this.cantSalidas = cantSalidas;
	}

	public SimpleStringProperty getValorSalidas() {
		return valorSalidas;
	}

	public void setValorSalidas(SimpleStringProperty valorSalidas) {
		this.valorSalidas = valorSalidas;
	}

	public SimpleStringProperty getCantSaldo() {
		return cantSaldo;
	}

	public void setCantSaldo(SimpleStringProperty cantSaldo) {
		this.cantSaldo = cantSaldo;
	}

	public SimpleStringProperty getValorSaldo() {
		return valorSaldo;
	}

	public void setValorSaldo(SimpleStringProperty valorSaldo) {
		this.valorSaldo = valorSaldo;
	}
	
	@Override
	public String toString() {
		@SuppressWarnings("static-access")
		String sep = Main.getModel().SEPARADOR;
		return  dia.get() + sep + descripcion.get() + sep+ valorUnitario.get() + sep + cantEntrada.get() + sep + 
				valorEntrada.get() + sep + cantSalidas.get() + sep + valorSalidas.get() + sep + cantSaldo.get() + sep + 
				valorSaldo.get();
	}
	
}
