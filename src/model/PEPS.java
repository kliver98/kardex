package model;

import java.util.Iterator;
import java.util.TreeMap;

public class PEPS implements Metodo {

	private TreeMap<Integer,Registro> datos;
	
	public PEPS() {
		datos = new TreeMap<Integer,Registro>();
	}
	
	public boolean agregarFila(String[] datos) {
		if (!verificarDatos(datos)) 
			return false;
		Registro r = null;
		if (datos[2].equalsIgnoreCase("D"))
			r = new Registro(datos[0],datos[1]);
		else
			r = new Registro(datos[0],datos[1],datos[2],datos[3],datos[4],datos[5],datos[6],datos[7],datos[8]);
		this.datos.put(r.getDia(),r);
		return true;
	}
	
	private String[][] revertirDatos(TreeMap<Integer,Registro> ant) {
		datos = ant;
		return null;
	}
	
	private boolean verificarDatos(String[] datos) {
		try {
			Integer.parseInt(datos[0]);
			if (!datos[2].equals("D")) {				
				Double.parseDouble(datos[2]);
				if (!datos[3].equalsIgnoreCase("0")) //Compra
					Integer.parseInt(datos[3]);
				else if (!datos[5].equalsIgnoreCase("0")) //Venta
					Integer.parseInt(datos[5]);
			}
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	private void registrarDevolucion(Registro r, InventarioPEPS inv, Registro copyD) {
		//En la devolucion solo cambio el atributo tipoRegistro y actualizo su descripcion
		if (!r.getDModificado())
			r.setDModificado(true);
		else {
			r.setCantidadSaldo(inv.getCantidadDisponible());
			r.setValorSaldo(inv.getSaldo());
			return;
		}
		char tr = copyD.getTipoRegistro();
		if (tr=='C') {
			r.setCantidadEntrada(copyD.getCantidadEntrada());
			r.setValorEntrada(copyD.getValorEntrada());
			if (datos.keySet().size()==1)  {
				inv.setCantidad(0);
				inv.setSaldo(0);
			}
		} else if (tr=='V') {
			r.setCantidadSalida(copyD.getCantidadSalida());
			r.setValorSalida(copyD.getValorSalida());
			if (datos.keySet().size()==1) {
				inv.setCantidad(0);
				inv.setSaldo(0);
			}
		} else
			inv.setCantidad(-1);
		if (datos.keySet().size()!=1) {
			r.setCantidadSaldo(inv.getCantidadDisponible());
			r.setValorSaldo(inv.getSaldo());
			r.setValorUnitario(copyD.getValorUnitario());
		}
		r.setDescripcion("D: "+copyD.getDescripcion());
	}
	
	public TreeMap<Integer,Registro> cloneTreeMap() {
		TreeMap<Integer,Registro> clone = new TreeMap<Integer,Registro>();
		for (Integer key : datos.keySet()) {
			Integer k = new Integer(key.toString());
			Registro copy = datos.get(key).clone();
			clone.put(k, copy);
		}
		return clone;
	}
	
	@Override
	public String[][] calcularKardex(String[] fila) {
		TreeMap<Integer,Registro> copy = cloneTreeMap();
		Registro copyD = null;
		try {
			if (fila[2].equals("D"))
				copyD = datos.get(Integer.parseInt(fila[0])).clone();
			if (copyD==null && fila[2].equals("D"))
				throw new Exception();
		}catch(Exception e) {
			return null;
		}
		boolean seAgrego = agregarFila(fila); //No verifica que sea coherente los datos respecto lo que ya habia, por eso copy para regresar al estado original si hay algun error, solo compra o venta
		if (!seAgrego)
			return null;
		String[][] result = new String[datos.keySet().size()][9];
		
		InventarioPEPS inv = new InventarioPEPS();
		int i = 0;
		
		for (Integer key:datos.keySet()) {
			Registro r = datos.get(key);
			char tipoRegistro = r.getTipoRegistro();
			if (tipoRegistro=='C') { //Son datos para una entrada
				inv.agregarProducto(r.getCantidadEntrada(),r.getValorUnitario());
				r.setCantidadSaldo(inv.getCantidadDisponible());
				r.setValorSaldo(inv.getSaldo());
				r.setValorEntrada(r.getValorUnitario()*r.getCantidadEntrada());
			} else if (tipoRegistro=='V') { // Son datos para una salida
				double precioC = inv.sacarProductos(r.getCantidadSalida());
				if (precioC==-1) {
					revertirDatos(copy);
					return new String[][] {};
				}
				else {
				r.setCantidadSaldo(inv.getCantidadDisponible());
				r.setValorSaldo(inv.getSaldo());
				r.setValorSalida(precioC);
				}
			} else if (tipoRegistro=='D') {
				registrarDevolucion(r,inv,copyD);
			}
			if (inv.getCantidadDisponible()<0 || inv.getSaldo()<0)
				return revertirDatos(copy);
		}
		Iterator<Integer> k = datos.keySet().iterator();
		while (k.hasNext()) {
			Integer key = k.next();
			Registro e = datos.get(key);
			String[] datos = e.toString().split(Kardex.SEPARADOR);
			result[i] = datos;
			i+=1;
		}
		return result;
	}
	
	@Override
	public void setDatos(TreeMap<Integer,Registro> datos) {
		this.datos = datos;
	}
	
}
