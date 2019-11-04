package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CrearNuevoController implements Initializable {

	@FXML
	private ComboBox<String> cbMes;
	@FXML
	private ComboBox<String> cbAnio;
	@FXML
	private ComboBox<String> cbMetodo;
	@FXML
	private TextField tfEmpresa;
	@FXML
	private TextField tfArticulo;
	@FXML
	private TextField tfUnidad;
	@FXML
	private TextField tfProovedor;
	@FXML
	private TextField tfComentario;
	@FXML
	private TextField tfMin;
	@FXML
	private TextField tfMax;
	
	public CrearNuevoController() {
		cbMes = new ComboBox<String>();
		cbAnio = new ComboBox<String>();
		cbMetodo = new ComboBox<String>();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		String[][] datosPeriodos = Main.getModel().getDatosPeriodos();
		
		cbMes.getItems().removeAll(cbMes.getItems());
		cbMes.getItems().addAll(datosPeriodos[0]);
		cbMes.getSelectionModel().select("Mes...");
		
		cbAnio.getItems().removeAll(cbAnio.getItems());
		cbAnio.getItems().addAll(datosPeriodos[1]);
		cbAnio.getSelectionModel().select("Año...");
		
		cbMetodo.getItems().removeAll(cbMetodo.getItems());
		cbMetodo.getItems().addAll(Main.getModel().getTiposMetodos());
		cbMetodo.getSelectionModel().select("Seleccione...");
		
	}
	
	public void btnCrear(ActionEvent evento) {
		
		String[] datos = new String[]{tfEmpresa.getText(),cbMetodo.getSelectionModel().getSelectedItem(),
				cbMes.getSelectionModel().getSelectedItem()+"/"+cbAnio.getSelectionModel().getSelectedItem(),
				tfArticulo.getText(), tfUnidad.getText(), tfProovedor.getText(), tfMin.getText(), tfMax.getText(),
				tfComentario.getText()};
		boolean seCreoArchivo = Main.getModel().crearRegistroKardex(datos);
		try {			
			if (seCreoArchivo)
				cambiarSceneTablaKardex(evento);
			else
				mensajeAlerta("Ocurrio un error","Verifique los datos, recuerde que no puede poner el símbolo \"_\"");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void btnCancelar(ActionEvent evento) {
		
		try {
			cambiarSceneMain(evento);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void mensajeAlerta(String titulo, String mensaje) {
		Alert alert = new Alert(AlertType.WARNING);
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(Main.getModel().getIconSource()));
		
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}
	
	public void cambiarSceneTablaKardex(ActionEvent evento) throws IOException {
		
		Parent crearNuevoParent = FXMLLoader.load(getClass().getResource("TablaKardex.fxml"));
		Scene crearNuevoScene = new Scene(crearNuevoParent);
		
		Stage window = (Stage)((Node)evento.getSource()).getScene().getWindow();
		
		window.setScene(crearNuevoScene);
		window.show();
		
	}
	
	public void cambiarSceneMain(ActionEvent evento) throws IOException {
		
		Parent mainParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene mainScene = new Scene(mainParent);
		
		Stage window = (Stage)((Node)evento.getSource()).getScene().getWindow();
		
		window.setScene(mainScene);
		window.show();
		
	}
	
}
