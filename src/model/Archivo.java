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
	private TreeMap<Integer,Registro> datos;
	
	public Archivo() {
		datos = new TreeMap<Integer,Registro>();
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
	
	public boolean eliminarRegistro(Integer key) {
		datos.remove(key);
		return !datos.containsKey(key);
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
	
	public TreeMap<Integer,Registro> getDatos() {
		return datos;
	}
	
	public String getMetodoValoracion() {
		return metodoValoracion.split(" - ")[0];
	}
	
	public boolean esMetodoPEPS() {
		return getMetodoValoracion().equalsIgnoreCase("PEPS");
	}
	
}
