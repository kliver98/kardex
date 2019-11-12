package model;

public class Registro {

	private boolean dModificado;
	private Character tipoRegistro;
	private Integer dia;
	private String descripcion;
	private Double valorUnitario;
	private Integer cantidadEntrada;
	private Double valorEntrada;
	private Integer cantidadSalida;
	private Double valorSalida;
	private Integer cantidadSaldo;
	private Double valorSaldo;
	
	public Registro(String dia, String descripcion, String valorUnitario, String cantidadEntrada,String valorEntrada,String cantidadSalida, String valorSalida, String cantidadSaldo, String valorSaldo ) {
		this.dModificado = false;
		this.dia = new Integer(dia);
		this.descripcion = descripcion;
		this.tipoRegistro = !cantidadEntrada.equals("0") ? 'C' : 'V';
		this.valorUnitario = new Double(valorUnitario);
		this.cantidadEntrada = new Integer(cantidadEntrada);
		this.valorEntrada = new Double(valorEntrada);
		this.cantidadSalida = new Integer(cantidadSalida);
		this.valorSalida = new Double(valorSalida);
		this.cantidadSaldo = new Integer(cantidadSaldo);
		this.valorSaldo = new Double(valorSaldo);
	}
	
	public Registro(String dia, String descripcion) {
		this.dModificado = false;
		this.dia = new Integer(dia);
		this.descripcion = descripcion;
		this.tipoRegistro = 'D';
		this.valorUnitario = new Double("0");
		this.cantidadEntrada = new Integer("0");
		this.valorEntrada = new Double("0");
		this.cantidadSalida = new Integer("0");
		this.valorSalida = new Double("0");
		this.cantidadSaldo = new Integer("0");
		this.valorSaldo = new Double("0");
	}

	public Integer getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = new Integer(dia);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Integer getCantidadEntrada() {
		return cantidadEntrada;
	}

	public void setCantidadEntrada(Integer cantidadEntrada) {
		this.cantidadEntrada = cantidadEntrada;
	}

	public Double getValorEntrada() {
		return valorEntrada;
	}

	public void setValorEntrada(Double valorEntrada) {
		this.valorEntrada = valorEntrada;
	}

	public Integer getCantidadSalida() {
		return cantidadSalida;
	}

	public void setCantidadSalida(Integer cantidadSalida) {
		this.cantidadSalida = cantidadSalida;
	}

	public Double getValorSalida() {
		return valorSalida;
	}

	public void setValorSalida(Double valorSalida) {
		this.valorSalida = valorSalida;
	}

	public Integer getCantidadSaldo() {
		return cantidadSaldo;
	}

	public void setCantidadSaldo(Integer cantidadSaldo) {
		this.cantidadSaldo = cantidadSaldo;
	}

	public Double getValorSaldo() {
		return valorSaldo;
	}

	public void setValorSaldo(Double valorSaldo) {
		this.valorSaldo = valorSaldo;
	}
	
	public void setDModificado(boolean n) {
		dModificado = n;
	}
	
	public boolean getDModificado() {
		return dModificado;
	}
	
	public void setTipoRegistro(Character n) {
		tipoRegistro = n;
	}
	
	public Character getTipoRegistro() {
		return tipoRegistro;
	}
	
	@Override
	public String toString() {
		return dia + Kardex.SEPARADOR + descripcion + Kardex.SEPARADOR+ valorUnitario + 
				Kardex.SEPARADOR + cantidadEntrada + Kardex.SEPARADOR+ valorEntrada + 
				Kardex.SEPARADOR + cantidadSalida + Kardex.SEPARADOR + valorSalida + 
				Kardex.SEPARADOR + cantidadSaldo + Kardex.SEPARADOR + valorSaldo;
	}

	public Registro clone() {
		Registro r = null;
		r = new Registro(new String(dia.toString()),new String(descripcion),new String(valorUnitario.toString()),
				new String(cantidadEntrada.toString()),new String(valorEntrada.toString()),
				new String(cantidadSalida.toString()),new String(valorSalida.toString()),
				new String(cantidadSaldo.toString()),new String(valorSaldo.toString()));
		return r;
	}
	
	public boolean isCompra() {
		return cantidadEntrada != 0 && valorUnitario != 0;
	}
	
	public boolean isVenta() {
		return cantidadSalida >  0 && cantidadSalida != 0;
	}
	
	public boolean isDevolucion() {
		return cantidadSalida < 0;
	}
  
}
