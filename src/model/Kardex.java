package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

public class Kardex {
	
	private static final String APP_NAME = "Sistema de registro de Inventario - Kardex por Fanny & Kliver";
	private static final String CARPETA = System.getProperty("user.dir");
	private static final String APP_ICON_SOURCE = "img/icon.png";
	private static final String[] TIPOS_METODOS = 
		{"PEPS - Primero en entrar, primero en salir","PP - Promedio Ponderado"};
	private static final String[] TIPOS_MODIFICACIONES = {"COMPRA C","VENTA V","DEVOLUCIÓN D"};
	private static final String[] MESES = {"ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
	private static final String[] ANIOS = {"2016","2017","2018","2019","2020","2021","2022","2023","2024","2025"};
	private static final String EXTENCION_KARDEX = ".kardex";
	public static final String SEPARADOR = "#";
	private Metodo metodo;
	private Archivo archivo;
	
	public Kardex() {
		archivo = new Archivo();
	}
	
	public String[] nombresKardexCreados() { //Devuelve arreglo con los nombres de archivos Kardex creados
		File carpeta = new File(getRutaCarpetaData());
		FileFilter filtro = new FileFilter() {
			 @Override
			 public boolean accept(File arch) {
			   return arch.isFile();
			 }
		};
		if (!carpeta.exists())
			return null;
		File[] archivos = carpeta.listFiles(filtro);
		String[] datos = new String[archivos.length];
		for (int i = 0; i < archivos.length; i++) {
			String nombre = archivos[i].getName();
			nombre = nombre.length()<75 ? nombre : nombre.substring(0, 52)+"..."+nombre.substring(nombre.length()-20,nombre.length());
			datos[i] = nombre;
		}
		return datos;
	}
	
	private boolean esNumeroEntero(String min, String max) { //Verifica si String para cantidadMinima y cantidadMaxima son numeros
		try {
			int mi = Integer.parseInt(min);
			int ma = Integer.parseInt(max);
			if (mi<0 || ma<0 || ma<=mi)
				return false;
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	private String obtenerString(String[] datos) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < datos.length; i++) {
			sb.append(datos[i]);
			if (i!=(datos.length-1))
				sb.append(SEPARADOR);
		}
		return sb.toString();
	}
	
	private boolean hayCaracteresNoValidos(String[] datos) {
		for (String dato : datos) {
			if (!dato.equals(datos[2]) && (dato.contains("_") || dato.contains("/") || dato.contains(SEPARADOR) || dato.contains("\\")) )
				return true;
		}
		return false;
	}
	
	private boolean estanDatosCrearKardexBien(String[] datos) {
		if (hayCaracteresNoValidos(datos) || !esNumeroEntero(datos[6], datos[7]))
			return false;
        return true;
	}
	
	public boolean crearRegistroKardex(String[] datos) { //Este metodo es el que se llama cuando se da clic al boton de crear Kardex
		if ( !estanDatosCrearKardexBien(datos) )
			return false;
		setDatosArchivo(datos);
		File file = new File(archivo.getRutaArchivoActual());
		BufferedWriter bw = null;
		try {
	        if (!file.exists()) {
	        	bw = new BufferedWriter(new FileWriter(file)); //Se crea el .kardex
	        	bw.close();
	        	guardarDatosRegistroKardex(datos);
	        } else
	        	return false;	        	
		} catch(Exception e) { //No lo pudo crear
			cerrarArchivo();
			return false;
		}
		return true;
	}
	
	private void setDatosArchivo(String[] datos) {
		archivo = new Archivo();
		archivo.setDatos(datos);
		String nombreEmpresa = datos[0];
		String metodo = datos[1].split(" - ")[0];
		String periodo = datos[2].split("/")[0]+"_"+datos[2].split("/")[1];
		String articulo = datos[3];
		archivo.setNombreArchivo(nombreEmpresa+"_"+metodo+"_"+articulo+"_"+periodo+EXTENCION_KARDEX);
        archivo.setRutaArchivoActual(CARPETA+"\\resources\\data\\"+archivo.getNombreArchivo());
		boolean PEPS = archivo.esMetodoPEPS();
		if (PEPS) //Relaciono datos del archivo con metodo peps
			this.metodo = new PEPS();
		else //**Relacionar con PP
			this.metodo = new PP();
	}
	
	public String[][] modificarFila(String[] datos) { //ESTE ES EL QUE MODIFICA Y RECALCULA, devuelve datos para tabla
		return metodo.calcularKardex(datos);
	}
	
	public boolean guardarCambios() {
		FileWriter file = null;
        PrintWriter pw = null;
        try {
        	file = new FileWriter(archivo.getRutaArchivoActual());
            pw = new PrintWriter(file);
            
            pw.print(obtenerString(archivo.getDatosCompletos())+"\n");
            TreeMap<Integer,Registro>  d = archivo.getDatos();
            Iterator<Integer> it = d.keySet().iterator();
            while (it.hasNext()) {
            	String guardar = d.get(it.next()).toString();            	
            	pw.print(guardar);
            }
            pw.close();
        } catch (Exception e) {
            return false;
        } finally {
           try {
           if (null != file)
        	   file.close();
           } catch (Exception e2) {
              return false;
           }
        }
		return true;
	}
	
	public boolean guardarDatosRegistroKardex(String[] datos) {
		FileWriter file = null;
        PrintWriter pw = null;
        try {
        	file = new FileWriter(archivo.getRutaArchivoActual());
            pw = new PrintWriter(file);
            
            //Debe hacer calculos de nuevo por que puede agregar registros intermedios
            String guardar = obtenerString(datos);
            pw.print(guardar+"\n");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != file)
        	   file.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
		return true;
	}
	
	public String[][] obtenerMatrizInicial() {
		try {			
			String[] linea = archivo.getDatos().get(archivo.getDatos().keySet().iterator().next()).toString().split(SEPARADOR);
			return modificarFila(linea);
		} catch(Exception e ) {
			return null;
		}
	}
	
	public void leerArchivo(String ruta) throws Exception {
		
		File file = null;
	    FileReader fr = null;
	    BufferedReader br = null;
	    ruta = CARPETA+"\\resources\\data\\"+ruta;
	    file = new File (ruta);
	    fr = new FileReader (file);
	    br = new BufferedReader(fr);

	        // Lectura del fichero
	    String linea;
	    int cont = 1;
	    while((linea=br.readLine())!=null) {
	    	String[] datos = linea.split(SEPARADOR);
	    	if (cont==1) {	    			
	    		setDatosArchivo(datos);
	    		cont+=1;
	    		continue;
	    	}
	    	archivo.getDatos().put(Integer.parseInt(datos[0]),new Registro(datos[0],datos[1],datos[2],datos[3],datos[4],datos[5],datos[6],datos[7],datos[8]));
	    	cont+=1;
	    }
	    metodo.setDatos(archivo.getDatos());
	    br.close();	
	    fr.close();
	}
	
	private LinkedList<String> leerArchivoLista() throws Exception {
		File file = null;
	    FileReader fr = null;
	    BufferedReader br = null;
	    LinkedList<String> copy = new LinkedList<String>();
	    
	    file = new File (archivo.getRutaArchivoActual());
	    fr = new FileReader (file);
	    br = new BufferedReader(fr);
	    // Lectura del fichero
	    String linea;
	    while((linea=br.readLine())!=null)
	    	copy.add(linea);
	    copy.poll();
	    fr.close();
	    br.close();
	    return copy;
	}
	
	public void modificarDatosBasicosArchivo(String[] nDatos) throws Exception {
		String rutaAnt = archivo.getRutaArchivoActual();
		LinkedList<String> copy = leerArchivoLista();
		FileWriter file = null;
        PrintWriter pw = null;
        file = new FileWriter(archivo.getRutaArchivoActual(),false);
        pw = new PrintWriter(file);
        if (!crearRegistroKardex(nDatos)) { //Tengo que borrar los registro que hay
        	String aux = obtenerString(nDatos);
        	pw.print(aux);
        	pw.close();
        	file.close();
        } else {
        	file.close();
        	borrarArchivo(rutaAnt);
        }
        file = new FileWriter(archivo.getRutaArchivoActual(),true);
        pw = new PrintWriter(file);
        while (!copy.isEmpty()) {
        	pw.print(copy.poll());
        }
        pw.close();
        file.close();
	}
	
	public boolean borrarArchivo(String ruta) {
		File file = new File(ruta.equals("") ? archivo.getRutaArchivoActual():ruta);
		return file.delete();
	}
	
	public String getAppName() {
		return APP_NAME;
	}
	
	public String getRutaCarpetaData() {
		return CARPETA+"\\resources\\data";
	}
	
	public String[] getTiposMetodos() {
		return TIPOS_METODOS;
	}
	
	public String getIconSource() {
		return APP_ICON_SOURCE;
	}
	
	public String[] getTiposModificaciones() {
		return TIPOS_MODIFICACIONES;
	}
	
	public String[] getDatosCabeceraKardex() {
		return archivo.getDatosCabeceraKardex();
	}
	
	public String[] getDatosBasicosArchivo() {
		return archivo.getDatosCompletos();
	}
	
	public void cerrarArchivo() {
		archivo = new Archivo();
		metodo = null;
	}
	
	public String[][] getDatosPeriodos() {
//		String[] a = new String[100];
//		int n = 2019;
//		a[0] = n+"";
//		for (int i = 1; i < a.length; i++) {
//			a[i] = (n-i)+"";
//		}
		return new String[][]{MESES,ANIOS};
	}
}
