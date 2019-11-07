package model;

import java.util.TreeMap;

public class Archivo {

	private String rutaArchivoActual;
	private String nombreArchivo;
	private String nombreEmpresa;
	private String metodoValoracion;
	private String periodo;
	private String articulo;
	private String unidad;
	private String proovedor;
	private Integer cantMinima;
	private Integer cantMaxima;
	private String comentario;
	private TreeMap<Integer,Elemento> datos;
	
	public Archivo() {
		datos = new TreeMap<Integer,Elemento>();
	}
	
	private boolean verificarDatos(String[] datos) {
		try {
			Integer.parseInt(datos[0]);
			Double.parseDouble(datos[2]);
			if (!datos[3].equalsIgnoreCase(""))
				Integer.parseInt(datos[3]);
			else if (!datos[5].equalsIgnoreCase(""))
				Integer.parseInt(datos[5]);
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean modificarFila(String[] datos) {
		if (datos[1].equalsIgnoreCase("D")) { //hago una copia de datos, calculo con los que no elimino. Si hay error, regreso datos como estaban
			TreeMap<Integer,Elemento> copy = (TreeMap<Integer, Elemento>) this.datos.clone();
			try {
				int dia = Integer.parseInt(datos[0]);
				this.datos.remove(dia);
				
			} catch (Exception e) {
				this.datos = copy;
				return false;
			}
			return true;
		}
		if (!verificarDatos(datos) && datos[5].equals(""))
			return false;
		boolean entry = !datos[3].equalsIgnoreCase("0");
		if (entry) //Compra. AGrego valor calculado si es de compra (valor unit*cant)
			datos[4] = (Integer.parseInt(datos[3])*Double.parseDouble(datos[2]))+"";
		this.datos.put(Integer.parseInt(datos[0]), new Elemento(datos[0],datos[1],Double.parseDouble(datos[2]),
				Integer.parseInt(datos[3]),Double.parseDouble(datos[4]),Integer.parseInt(datos[5]),Double.parseDouble(datos[6]),
				Integer.parseInt(datos[7]),Double.parseDouble(datos[8])));
		return true;
	}
	
	public void setDatos(String[] datos) {
		nombreEmpresa = datos[0];
		metodoValoracion = datos[1];
		periodo = datos[2];
		articulo = datos[3];
		unidad = datos[4];
		proovedor = datos[5];
		cantMinima = Integer.parseInt(datos[6]);
		cantMaxima = Integer.parseInt(datos[7]);
		comentario = datos.length>8 ? datos[8]:"";
	}
	
	public String[] getDatosCabeceraKardex() {
		return new String[] {nombreEmpresa,metodoValoracion,articulo, periodo, unidad, cantMinima.toString()};
	}
	
	public String[] getDatosCompletos() {
		return new String[] {nombreEmpresa,metodoValoracion,periodo,articulo,unidad,proovedor,cantMinima.toString(),cantMaxima.toString(),comentario};
	}
	
	public void setRutaArchivoActual(String texto) {
		rutaArchivoActual = texto;
	}
	
	public void setNombreArchivo(String texto) {
		nombreArchivo = texto;
	}
	
	public String getRutaArchivoActual() {
		return rutaArchivoActual;
	}
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	
	public TreeMap<Integer,Elemento> getDatos() {
		return datos;
	}
	
	public String getMetodoValoracion() {
		return metodoValoracion.split(" - ")[0];
	}
	
}
