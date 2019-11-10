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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DatosBasicosController implements Initializable {

	@FXML
	private TextField tfEmpresa;
	@FXML
	private ComboBox<String> cbMetodo;
	@FXML
	private ComboBox<String> cbMes;
	@FXML
	private ComboBox<String> cbAnio;
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
	@FXML
	private Button btnRegresar;
	@FXML
	private Button btnModificar;
	
	@Override
	public void initialize(URL u, ResourceBundle r) {
		String[] datosBasicos = Main.getModel().getDatosBasicosArchivo();
		tfEmpresa.setText(datosBasicos[0]);
		cbMetodo.getItems().removeAll(cbMetodo.getItems());
		cbMetodo.getItems().addAll(Main.getModel().getTiposMetodos());
		cbMetodo.setValue(datosBasicos[1]);
		//
		String[] periodo = datosBasicos[2].split("/");
		String[][] datosPeriodos = Main.getModel().getDatosPeriodos();
		cbMes.getItems().removeAll(cbMes.getItems());
		cbMes.getItems().addAll(datosPeriodos[0]);
		cbMes.getSelectionModel().select("Mes...");
		cbAnio.getItems().removeAll(cbAnio.getItems());
		cbAnio.getItems().addAll(datosPeriodos[1]);
		cbAnio.getSelectionModel().select("Año...");
		cbMes.setValue(periodo[0]);
		cbAnio.setValue(periodo[1]);
		//
		tfArticulo.setText(datosBasicos[3]);
		tfUnidad.setText(datosBasicos[4]);
		tfProovedor.setText(datosBasicos[5]);
		tfMin.setText(datosBasicos[6]);
		tfMax.setText(datosBasicos[7]);
		tfComentario.setText(datosBasicos[8]);
	}
	
	public void btnModificar(ActionEvent evento) {
		String[] datos = new String[]{tfEmpresa.getText(),cbMetodo.getValue(),
				cbMes.getSelectionModel().getSelectedItem()+"/"+cbAnio.getSelectionModel().getSelectedItem(),
				tfArticulo.getText(), tfUnidad.getText(), tfProovedor.getText(), tfMin.getText(), tfMax.getText(),
				tfComentario.getText()};
		boolean seModifico = Main.getModel().modificarDatosBasicosArchivo(datos);
		if (!seModifico)
			mensajePopUp("Error","No se pudo modificar el archivo",AlertType.ERROR);
	}

	public void cambiarSceneTablaKardex(ActionEvent evento) throws Exception {
		cambiarScene(evento,"TablaKardex.fxml");
	}
	
	private void cambiarScene(ActionEvent evento, String nombre) throws IOException {
		Parent mainParent = FXMLLoader.load(getClass().getResource(nombre));
		Scene mainScene = new Scene(mainParent);
		
		Stage window = (Stage)((Node)evento.getSource()).getScene().getWindow();
		
		window.setScene(mainScene);
		window.show();
	}
	
	private void mensajePopUp(String titulo, String mensaje, AlertType tipoAlerta) {
		Alert alert = new Alert(tipoAlerta);
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(Main.getModel().getIconSource()));
		
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}
	
}
