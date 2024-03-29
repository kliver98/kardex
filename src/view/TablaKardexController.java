package view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TablaKardexController implements Initializable {

	@FXML
	private TextField tfCantidad;
	@FXML
	private TextField tfValor;
	@FXML
	private TextField tfDescripcion;
	@FXML
	private TextField tfDia;
	@FXML
	private ComboBox<String> cbTipoModificacion;
	@FXML
	private Label lblEmpresa;
	@FXML
	private Label lblMetodo;
	@FXML
	private Label lblArticulo;
	@FXML
	private Label lblPeriodo;
	@FXML
	private Label lblUnidades;
	@FXML
	private Label lblMin;
	@FXML
	private Label lblValor;
	@FXML
	private Label lbImp;
	@FXML
	private MenuBar menuBar;
	@FXML
	private TableView<DatosModel> tabla;
	@FXML
    public TableColumn<DatosModel, String> dia;
	@FXML
    public TableColumn<DatosModel, String> descripcion;
	@FXML
    public TableColumn<DatosModel, String> valorUnitario;
	@FXML
    public TableColumn<DatosModel, String> cantEntrada;
	@FXML
    public TableColumn<DatosModel, String> valorEntrada;
	@FXML
    public TableColumn<DatosModel, String> cantSalidas;
	@FXML
    public TableColumn<DatosModel, String> valorSalidas;
	@FXML
    public TableColumn<DatosModel, String> cantSaldo;
	@FXML
    public TableColumn<DatosModel, String> valorSaldo;
	private String datosCabecera[];//empresa-metodo-periodo-articulo-unidad-proovedor-cantMin-cantMax-comentario
	
	public TablaKardexController() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		dia.setCellValueFactory(cellData -> cellData.getValue().getDia());
		descripcion.setCellValueFactory(cellData -> cellData.getValue().getDescripcion());
		valorUnitario.setCellValueFactory(cellData -> cellData.getValue().getValorUnitario());
		cantEntrada.setCellValueFactory(cellData -> cellData.getValue().getCantEntrada());
		valorEntrada.setCellValueFactory(cellData -> cellData.getValue().getValorEntrada());
		cantSalidas.setCellValueFactory(cellData -> cellData.getValue().getCantSalidas());
		valorSalidas.setCellValueFactory(cellData -> cellData.getValue().getValorSalidas());
		cantSaldo.setCellValueFactory(cellData -> cellData.getValue().getCantSaldo());
		valorSaldo.setCellValueFactory(cellData -> cellData.getValue().getValorSaldo());
		
		cbTipoModificacion.getItems().removeAll(cbTipoModificacion.getItems());
		cbTipoModificacion.getItems().addAll(Main.getModel().getTiposModificaciones());
		
		
		try {			
			datosCabecera = Main.getModel().getDatosCabeceraKardex();
		} catch(Exception e) {
			mensaje("Error","Ocurrio un error al cargar los datos de la cabecera de la tabla.",AlertType.ERROR);
			return;
		}
		datosCabecera[1] = datosCabecera[1].split(" - ")[0];
		if (datosCabecera[1].equalsIgnoreCase("PP"))
			mensaje("En desarrollo","Esta tipo de m�todo esta en desarrollo y puede contener errores.",AlertType.INFORMATION);
		lblEmpresa.setText("Nombre de la empresa: "+datosCabecera[0]);
		lblMetodo.setText("M�todo de valoraci�n: "+datosCabecera[1]);
		lblValor.setText(datosCabecera[1].equalsIgnoreCase("PEPS") ? "Valor unitario: ":lblValor.getText());
		lblArticulo.setText("Art�culo: "+datosCabecera[2]);
		lblPeriodo.setText("Per�odo: "+datosCabecera[3]);
		lblUnidades.setText("Unidad: "+datosCabecera[4]);
		lblMin.setText("Cantidad m�nima: "+datosCabecera[5]+" "+datosCabecera[4]);
		
		try {			
			String[][] inicial = Main.getModel().obtenerMatrizInicial();
			setDatosEnTabla(inicial);
		} catch (Exception e) { 
		}
		
	}
	
	public void setModificationsFields(ActionEvent evento) {
		String item = cbTipoModificacion.getSelectionModel().getSelectedItem();
		String[] tiposModificaciones = Main.getModel().getTiposModificaciones();
		if (item.equalsIgnoreCase(tiposModificaciones[0])) {
			tfValor.setDisable(false);
			tfDia.setDisable(false);
			tfCantidad.setDisable(false);
			tfDescripcion.setDisable(false);
		} else if (item.equalsIgnoreCase(tiposModificaciones[1])) {
			tfValor.setDisable(true);
			tfValor.setText("");
			tfDia.setDisable(false);
			tfCantidad.setDisable(false);
			tfDescripcion.setDisable(false);
		} else if (item.equalsIgnoreCase(tiposModificaciones[2])) {
			tfDia.setDisable(false);
			tfValor.setDisable(true);
			tfCantidad.setDisable(true);
			tfDescripcion.setDisable(true);
			tfValor.setText("");
			tfCantidad.setText("");
			tfDescripcion.setText("");
		}
	}
	
	public void btnModificarTabla(ActionEvent evento) { //Aqui se da compra-venta-devolucion
		if (cbTipoModificacion.getSelectionModel().getSelectedItem()==null) {
			mensaje("Error","Debe seleccionar una operaci�n [COMPRA-VENTA-DEVOLUCI�N].",AlertType.ERROR);
			return;
		} 
		boolean c = cbTipoModificacion.getSelectionModel().getSelectedItem().split(" ")[1].equals("C");
		String[] datos = new String[] {tfDia.getText(),tfDescripcion.getText(),tfValor.getText(),tfCantidad.getText(),"0","0",
				"0","0","0"}; //Estoy comprando
		datos = c ? datos:new String[] {tfDia.getText(),tfDescripcion.getText(),"0","0","0",tfCantidad.getText(),
				"0","0","0"}; //Estoy vendiendo
		datos = !cbTipoModificacion.getSelectionModel().getSelectedItem().split(" ")[1].equalsIgnoreCase("D") ? datos : new String[] {tfDia.getText(),"","D","0","0","0","0","0","0"};
		String[][] matriz = Main.getModel().modificarFila(datos);
		if (matriz==null) { //Este mesaje se muestra cuando los datos no estan correctamente llenados
			mensaje("Error de entradas","Ocurrio un error, verifique que ingreso todos los datos necesarios en su formato correcto.",AlertType.ERROR);
			return;
		} else if (matriz.length==0) { //Este mensaje se meustra cuando no hay inventario suficiente para hacer la compra
			mensaje("Error de Inventario","No hay suficiente productos en el inventario para realizar la venta.",AlertType.WARNING);
			return;
		}
		setDatosEnTabla(matriz);
	}
	
	public void setDatosEnTabla(String[][] datos) {
		tabla.getItems().clear();
		for (int i = 0; i < datos.length; i++) {
			String[] aux = new String[9];
			for (int j = 0; j < datos[i].length; j++) {
				aux[j] = datos[i][j];
			}			
			tabla.getItems().add(new DatosModel(aux[0],aux[1],aux[2],aux[3],aux[4],aux[5],aux[6],aux[7],aux[8]));
		}
		if (Integer.parseInt(datos[datos.length-1][7])<Integer.parseInt(lblMin.getText().split(" ")[2]))
			mensaje("Reabastecer","Cuidado! tiene menos del inventario m�nimo en Stock.",AlertType.WARNING);
	}
	
	public void eliminarRegistro(ActionEvent evento) {
		String id = getRespuestaPopUp("Eliminar Registro","Introduzca el identificador [D�a] del registro del kardex.").trim();
		try {
			Integer.parseInt(id);
			if (!id.equals(""))
				System.out.println("Debo hacer funcionalidad para eliminar registro.");
		} catch (Exception e) {
			mensaje("Error","El identificador no es v�lido.",AlertType.ERROR);
		}
	}
	
	private String getRespuestaPopUp(String title, String message) {
		String r = null;
		try {
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle(title);
			dialog.setHeaderText(null);
			dialog.setContentText(message);

			Optional<String> result = dialog.showAndWait();
			r = result.get();
		} catch(Exception e) {
			return null;
		}
		return r;
	}
	
	public void mensajeCreditos() {
		mensaje("Cr�dtios...","Este software ha sido desarrollado por Fanny & Kliver - Sugerencias o inquietudes: kliver1998@hotmail.com",AlertType.INFORMATION);
	}
	
	public void mensajeAyuda() {
		mensaje("Ventana de ayuda","En la parte superior tiene una barra horizontal. "
				+ "Ah� podra encontrar Archivo,Editar y Ayuda. "
				+ "Si pasa el mouse por Archivo podra encontrar todo lo que puede hacer relacionado "
				+ "al archivo kardex abierto."
				+ "En Editar podra encontrar las modificaciones que puede hacer en el archivo kardex"
				+ ", tenga presente que las correciones o adiciones deben ser congruentes. "
				+ "Por �ltimo, "
				+ "en Ayuda, podra encontrar informaci�n relevante que le ayude o de informaci�n acerca"
				+ "del software.",AlertType.INFORMATION);
	}
	
	private void mensaje(String titulo, String mensaje, AlertType tipoAlerta) {
		Alert alert = new Alert(tipoAlerta);
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(Main.getModel().getIconSource()));
		
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}
	
	private void cambiarSceneMain(ActionEvent evento) throws IOException {
		cambiarScene(evento,"Main.fxml");
	}
	
	public void cambiarSceneModificarDatosBasicos(ActionEvent evento) throws IOException {
		cambiarScene(evento,"ModificarDatosBasicos.fxml");
	}

	public void cambiarSceneVistaDatosBasicos(ActionEvent evento) throws IOException {
		cambiarScene(evento,"VistaDatosBasicos.fxml");
	}
	
	private void cambiarScene(ActionEvent evento, String nombre) throws IOException {
		
		Parent mainParent = FXMLLoader.load(getClass().getResource(nombre));
		Scene mainScene = new Scene(mainParent);
		
		Stage window = (Stage) menuBar.getScene().getWindow();
		
		window.setScene(mainScene);
		window.show();
		
	}
	
	public void borrarArchivo(ActionEvent evento) throws Exception {
		String res = getRespuestaPopUp("Eliminar el archivo ","Escriba \"si\" para eliminar. Sin comillas.");
		if (res==null || !res.equalsIgnoreCase("si"))
			return;
		boolean borrado = Main.getModel().borrarArchivo("");//Vacio significa que borre el archivo con el que se esta trabajando
		if (!borrado)
			mensaje("Error al borrar","No se pudo borrar el archivo.",AlertType.ERROR);
		else {
			mensaje("Confirmaci�n","El archivo fue eliminado exitosamente.",AlertType.CONFIRMATION);
			cambiarSceneMain(evento);
		}
	}
	
	public void cerrarArchivo(ActionEvent evento) {
		
		Main.getModel().cerrarArchivo();
		try {
			cambiarSceneMain(evento);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void editarFila(MouseEvent e) { //Funcionalidad para implementar, mostrar ventana para editar info y ver completa la info
//		int c = e.getClickCount(); //Si es 2 mostrar pantalla editar info
//		System.out.println("Click count: "+c);
//		DatosModel item = tabla.getSelectionModel().getSelectedItem();
//		if (item==null)
//			return;
//		System.out.println(item.toString());
	}

	public void guardarCambios(ActionEvent evento) {
		boolean seGuardo = Main.getModel().guardarCambios();
		String mensaje = "Se guardaron los cambios.";
		if (!seGuardo)
			mensaje = "No se guardaron los cambios.";
		mensaje("Confirmaci�n",mensaje,AlertType.CONFIRMATION);
	}
	
	public void exit() {
		System.exit(0);
	}
	
}
