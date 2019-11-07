package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Kardex {

	private static final String APP_NAME = "Sistema de registro de Inventario - Kardex";
	private static final String CARPETA = System.getProperty("user.dir");
	private static final String APP_ICON_SOURCE = "img/icon.png";
	private static final String[] TIPOS_METODOS = 
		{"PEPS - Primero en entrar, primero en salir","PP - Promedio Ponderado"};
	private static final String[] TIPOS_MODIFICACIONES = {"COMPRA C","VENTA V","DEVOLUCIÓN D"};
	private static final String[] MESES = {"ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
	private static final String[] ANIOS = {"2016","2017","2018","2019","2020","2021","2022","2023","2024","2025"};
	private static final String EXTENCION_KARDEX = ".kardex";
	public static final String SEPARADOR = "#";
	private PEPS peps;
	private Archivo archivo;
	private ArrayList<Elemento> elementos;
	private PromedioPonderado pp;
	
	public Kardex() {
		archivo = new Archivo();
		peps = new PEPS();
		elementos = new ArrayList<>();
		pp = new PromedioPonderado();
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
		String[] datos = new String[archivos.length];
		for (int i = 0; i < archivos.length; i++) {
			String nombre = archivos[i].getName();
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
		setDatosArchivo(datos);
		File file = new File(archivo.getRutaArchivoActual());
		BufferedWriter bw = null;
		try {
	        if (!file.exists())
	        	bw = new BufferedWriter(new FileWriter(file));
	        	bw.close();
	        guardarDatosRegistroKardex(datos);
		} catch(Exception e) { //No lo pudo crear
			cerrarArchivo();
			return false;
		}
		return true;
	}
	
	public String[][] modificarFila(String[] datos) { //ESTE ES EL QUE MODIFICA Y RECALCULA, devuelve datos para tabla
		boolean seModifico = archivo.modificarFila(datos);
		String[][] nDatos = null;
		if (seModifico) {
			//actualizo/recalculo
			String metodo = archivo.getMetodoValoracion();
			if (metodo.equalsIgnoreCase("PEPS")) {
				nDatos = peps.calcular();
			}else if (metodo.equalsIgnoreCase("PP")) {
				nDatos = this.matrizActualizada();
			}
		}
		return nDatos;
	}
	
	public boolean guardarDatosRegistroKardex(String[] datos) {
		FileWriter file = null;
        PrintWriter pw = null;
        try {
        	file = new FileWriter(archivo.getRutaArchivoActual());
            pw = new PrintWriter(file);
            
            //Debe hacer calculos de nuevo por que puede agregar registros intermedios
            String guardar = obtenerString(datos);
            pw.print(guardar);

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
	
	public void leerArchivo(String ruta) {
		
		File file = null;
	    FileReader fr = null;
	    BufferedReader br = null;

	    try {
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
	    		modificarFila(datos);
	    		cont+=1;
	    	}
	    	
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    } finally {
	       try {                    
	          if( null != fr ){   
	        	  fr.close();     
	          }                  
	       } catch (Exception e2) { 
	    	   e2.printStackTrace();
	       }
	    }
		
	}
	
	public boolean modificarDatosBasicosArchivo(String[] nDatos) {
		boolean seModifico = false;
		String rutaAnt = archivo.getRutaArchivoActual();
		crearRegistroKardex(nDatos);
		File file = null;
	    FileReader fr = null;
	    BufferedReader br = null;

	    FileWriter fileW = null;
        PrintWriter pw = null;
	    
	    try {
	    	file = new File (rutaAnt);
	    	fr = new FileReader (file);
	    	br = new BufferedReader(fr);
	    	fileW = new FileWriter(archivo.getRutaArchivoActual());
            pw = new PrintWriter(fileW);
	         // Lectura del fichero
	    	String linea;
	    	int cont = 1;
	    	while((linea=br.readLine())!=null) {
	    		if (cont==1) {
	    			cont+=1;
	    			continue;
	    		}
	    		pw.print(linea);
	    		cont+=1;
	    	}
	    	
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    } finally {
	       try {                    
	          if( null != fr ){   
	        	  fr.close();     
	          }
	          if (null!=fileW)
	        	  fileW.close();
	       } catch (Exception e2) { 
	    	   e2.printStackTrace();
	       }
	    }
		return seModifico;
	}
	
	public boolean borrarArchivo() {
		File file = new File(archivo.getRutaArchivoActual());
		return file.delete();
	}
	
	private void setDatosArchivo(String[] datos) {
		archivo = new Archivo();
		archivo.setDatos(datos);
		String nombreEmpresa = datos[0];
		String metodo = datos[1].split(" - ")[0];
		if (metodo.equalsIgnoreCase("PEPS")) //Relaciono datos del archivo con metodo peps
			peps.setDatos(archivo.getDatos());
		else if (metodo.equalsIgnoreCase("PP")) //**Relacionar con PP
			System.out.println("Hacer por pp");
		String periodo = datos[2].split("/")[0]+"_"+datos[2].split("/")[1];
		String articulo = datos[3];
		archivo.setNombreArchivo(nombreEmpresa+"_"+metodo+"_"+articulo+"_"+periodo+EXTENCION_KARDEX);
        archivo.setRutaArchivoActual(CARPETA+"\\resources\\data\\"+archivo.getNombreArchivo());
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

	
	public ArrayList<Elemento> getElementos() {
		return elementos;
	}

	public void setElementos(ArrayList<Elemento> elementos) {
		this.elementos = elementos;
	}

	public PromedioPonderado getPp() {
		return pp;
	}

	public void setPp(PromedioPonderado pp) {
		this.pp = pp;
	}
	
	public void addDatos(String[] datos) {
		
		//String dia, String descripcion, int cantidad, double valorU
		String dia = datos[0];
		String descripcion = datos[1];
		double valorU = Double.parseDouble(datos[2]);
		int cantidad = Integer.parseInt(datos[3]);
		
		if(valorU != 0 && cantidad != 0){
			Elemento agregar  = new Elemento(dia, descripcion, valorU, cantidad, 0, 0, 0, 0,0);		
			elementos.add(agregar);
		}else if(valorU == 0 && cantidad != 0) {
			Elemento agregar  = new Elemento(dia, descripcion, valorU, 0, 0, cantidad, 0, 0,0);
			elementos.add(agregar);
		}else {
			Elemento agregar = search(dia);
			int cant = agregar.getCantidadSalida();
			agregar.setCantidadSalida(cant*-1);
			elementos.add(agregar);
		}
				
				
	}
	
	public Elemento search(String dia) {
		
		Elemento search = null;
		boolean encontrado = false;
		int i = 0;
		while(i < elementos.size() && encontrado == false) {
			if(elementos.get(i).getDia().equals(dia)) {
				search = elementos.get(i);
				encontrado = true;
			}
		}	
		return search;
	}
	
	public String[][] matrizActualizada(){
		
		String[][] matriz = pp.lecturaDatos(elementos);
		
		return matriz;
	}
	
	public boolean esPEPS() {
		return archivo.getMetodoValoracion().split(" - ")[0].equals("PEPS");
	}
}
