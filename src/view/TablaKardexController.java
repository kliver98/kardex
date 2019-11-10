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
		lblEmpresa.setText("Nombre de la empresa: "+datosCabecera[0]);
		datosCabecera[1] = datosCabecera[1].split(" - ")[0];
		lblMetodo.setText("Método de valoración: "+datosCabecera[1]);
		lblValor.setText(datosCabecera[1].equalsIgnoreCase("PEPS") ? "Valor unitario: ":lblValor.getText());
		lblArticulo.setText("Artículo: "+datosCabecera[2]);
		lblPeriodo.setText("Período: "+datosCabecera[3]);
		lblUnidades.setText("Unidad: "+datosCabecera[4]);
		lblMin.setText("Cantidad mínima: "+datosCabecera[5]+" "+datosCabecera[4]);
	}
	
	public void setModificationsFields(ActionEvent evento) {
		String item = cbTipoModificacion.getSelectionModel().getSelectedItem();
		String[] tiposModificaciones = Main.getModel().getTiposModificaciones();
		if (item.equalsIgnoreCase(tiposModificaciones[0])) {
			tfValor.setEditable(true);
			tfDia.setEditable(true);
			tfCantidad.setEditable(true);
			tfDescripcion.setEditable(true);
		} else if (item.equalsIgnoreCase(tiposModificaciones[1])) {
			tfValor.setEditable(false);
			tfValor.setText("");
			tfDia.setEditable(true);
			tfCantidad.setEditable(true);
			tfDescripcion.setEditable(true);
		} else if (item.equalsIgnoreCase(tiposModificaciones[2])) {
			tfDia.setEditable(true);
			tfValor.setEditable(false);
			tfCantidad.setEditable(false);
			tfDescripcion.setEditable(false);
			tfValor.setText("");
			tfCantidad.setText("");
			tfDescripcion.setText("");
		}
	}
	
	public void btnModificarTabla(ActionEvent evento) { //Aqui se da compra-venta-devolucion
		if (cbTipoModificacion.getSelectionModel().getSelectedItem()==null) {
			mensaje("Error","Debe seleccionar una operación [COMPRA-VENTA-DEVOLUCIÓN].",AlertType.ERROR);
			return;
		} 
		boolean c = cbTipoModificacion.getSelectionModel().getSelectedItem().split(" ")[1].equals("C");
		String[] datos = new String[] {tfDia.getText(),tfDescripcion.getText(),tfValor.getText(),tfCantidad.getText(),"0","0",
				"0","0","0"}; //Estoy comprando
		datos = c ? datos:new String[] {tfDia.getText(),tfDescripcion.getText(),"0","0","0",tfCantidad.getText(),
				"0","0","0"}; //Estoy vendiendo
		datos = !cbTipoModificacion.getSelectionModel().getSelectedItem().split(" ")[1].equalsIgnoreCase("D") ? datos : new String[] {tfDia.getText(),"","D","0","0","0","0","0","0"};
		String[][] matriz = Main.getModel().modificarFila(datos);
		if (matriz==null) {
			mensaje("Error","Ocurrio un error y no hay datos para mostrar en la tabla. Intente de nuevo o vaya a la pestaña ayuda.",AlertType.ERROR);
			return;
		}
		tabla.getItems().clear();
		for (int i = 0; i < matriz.length; i++) {
			String[] aux = new String[9];
			for (int j = 0; j < matriz[i].length; j++) {
				aux[j] = matriz[i][j];
			}			
			tabla.getItems().add(new DatosModel(aux[0],aux[1],aux[2],aux[3],aux[4],aux[5],aux[6],aux[7],aux[8]));
		}
	}
	
	public void eliminarRegistro(ActionEvent evento) {
		String id = getRespuestaPopUp("Eliminar Registro","Introduzca el identificador [Día] del registro del kardex.").trim();
		try {
			Integer.parseInt(id);
			if (!id.equals(""))
				System.out.println("Debo hacer funcionalidad para eliminar registro.");
		} catch (Exception e) {
			mensaje("Error","El identificador no es válido.",AlertType.ERROR);
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
		mensaje("Crédtios...","Este software ha sido desarrollado por Fanny & Kliver - Sugerencias o inquietudes: kliver1998@hotmail.com",AlertType.INFORMATION);
	}
	
	public void mensajeAyuda() {
		mensaje("Ventana de ayuda","En la parte superior tiene una barra horizontal. "
				+ "Ahí podra encontrar Archivo,Editar y Ayuda. "
				+ "Si pasa el mouse por Archivo podra encontrar todo lo que puede hacer relacionado "
				+ "al archivo kardex abierto."
				+ "En Editar podra encontrar las modificaciones que puede hacer en el archivo kardex"
				+ ", tenga presente que las correciones o adiciones deben ser congruentes. "
				+ "Por último, "
				+ "en Ayuda, podra encontrar información relevante que le ayude o de información acerca"
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
			mensaje("Confirmación","El archivo fue eliminado exitosamente.",AlertType.CONFIRMATION);
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
		
	}
	
	public void exit() {
		System.exit(0);
	}
	
}
