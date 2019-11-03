package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Kardex {

	private static final String APP_NAME = "Sistema de registro de Inventario - Kardex";
	private static final String CARPETA = System.getProperty("user.dir");
	private static final String APP_ICON_SOURCE = "img/icon.png";
	private static final String[] TIPOS_METODOS = 
		{"PEPS - Primero en entrar, primero en salir","PP - Promedio Ponderado"};
	private static final String[] MESES = {"ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
	private static final String[] ANIOS = {"2016","2017","2018","2019","2020","2021","2022","2023","2024","2025"};
	private static final String EXTENCION_KARDEX = ".kardex";
	public static final String SEPARADOR = "#";
	private String rutaArchivoActual;
	private String nombreArchivo;
	
	public Kardex() {
		rutaArchivoActual = "";
		nombreArchivo = "";
	}
	
	public String[] nombresKardexCreados() {
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
		String[] datos = new String[archivos.length+1];
		datos[0] = "Seleccione...";
		for (int i = 1; i < archivos.length+1; i++) {
			String nombre = archivos[i-1].getName();
			nombre = nombre.length()<75 ? nombre : nombre.substring(0, 52)+"..."+nombre.substring(nombre.length()-20,nombre.length());
			datos[i] = nombre;
		}
		return datos;
	}
	
	private boolean estaFormatoPeriodoBien(String texto) {
		boolean estaMesBien = false;
		boolean estaAnioBien = false;
		String[] datos = texto.split("_");
		for (String mes : MESES) {
			if (datos[0].equalsIgnoreCase(mes)) {
				estaMesBien = true;
				break;
			}
		}
		for (String anio : ANIOS) {
			if (datos[1].equalsIgnoreCase(anio)) {
				estaAnioBien = true;
				break;
			}
		}
		return estaMesBien==true && estaAnioBien==true;
	}
	
	private boolean hayBackSlash(String texto) {
		boolean cond1 = texto.contains("\\");
		boolean cond2 = texto.contains("/");
		return cond1 || cond2 ? true:false;
	}
	
	private boolean esNumeroEntero(String min, String max) {
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
	
	private boolean hayRayasPiso(String[] datos) {
		if (datos.length!=9) //Verificacion extra no necesaria, son 9 datos que el usuario digita al crear Kardex
			return true;
		for (String dato : datos) {
			if (dato.contains("_"))
				return true;
		}
		return false;
	}
	
	private boolean estanDatosCrearKardexBien(String[] datos) {
		if (hayRayasPiso(datos))
			return false;
		String nombreEmpresa = datos[0];
        String periodo = datos[2].split("/")[0]+"_"+datos[2].split("/")[1];
        String min = datos[6];
        String max = datos[7];
        if ( !estaFormatoPeriodoBien(periodo) || hayBackSlash(nombreEmpresa) || !esNumeroEntero(min, max) )
			return false;
        return true;
	}
	
	public boolean crearRegistroKardex(String[] datos) {
		if ( !estanDatosCrearKardexBien(datos) )
			return false;
		String nombreEmpresa = datos[0];
		String metodo = datos[1].split(" - ")[0];
		String periodo = datos[2].split("/")[0]+"_"+datos[2].split("/")[1];
		String articulo = datos[3];
		nombreArchivo = nombreEmpresa+"_"+metodo+"_"+articulo+"_"+periodo+EXTENCION_KARDEX;
        rutaArchivoActual = CARPETA+"\\resources\\data\\"+nombreArchivo;
		File archivo = new File(rutaArchivoActual);
		BufferedWriter bw = null;
		try {
	        if (!archivo.exists())
	        	bw = new BufferedWriter(new FileWriter(archivo));
	        	bw.close();
	        guardarDatosRegistroKardex(datos);
		} catch(Exception e) { //No lo pudo crear
			nombreArchivo = "";
			rutaArchivoActual = "";
			return false;
		}
		return true;
	}
	
	public boolean guardarDatosRegistroKardex(String[] datos) {
		FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(rutaArchivoActual);
            pw = new PrintWriter(fichero);
            
            //Debe hacer calculos de nuevo por que puede agregar registros intermedios
            String guardar = obtenerString(datos);
            pw.print(guardar);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
		return true;
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
	
	public String[][] getDatosPeriodos() {
		String[] a = new String[100];
		int n = 2019;
		a[0] = n+"";
		for (int i = 1; i < a.length; i++) {
			a[i] = (n-i)+"";
		}
		return new String[][]{MESES,a};
	}
	
	public String getRutaArchivoActual() {
		return rutaArchivoActual;
	}
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	
}
