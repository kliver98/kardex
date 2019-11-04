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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TablaKardexController implements Initializable {

	@FXML
	private TextField tfCantidad;
	@FXML
	private TextField tfValorUnitario;
	@FXML
	private DatePicker dpFecha;
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
	private MenuBar menuBar;
	
	public TablaKardexController() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbTipoModificacion.getItems().removeAll(cbTipoModificacion.getItems());
		cbTipoModificacion.getItems().addAll(Main.getModel().getTiposModificaciones());
		
		String[] datosCabecera = Main.getModel().getDatosCabeceraKardex();
		lblEmpresa.setText("Nombre de la empresa: "+datosCabecera[0]);
		lblMetodo.setText("Método de valoración: "+datosCabecera[1].split(" - ")[0]);
		lblArticulo.setText("Artículo: "+datosCabecera[2]);
		lblPeriodo.setText("Período: "+datosCabecera[3]);
		lblUnidades.setText("Unidad: "+datosCabecera[4]);
		lblMin.setText("Cantidad mínima: "+datosCabecera[5]+" "+datosCabecera[4]);
	}
	
	public void btnModificarTabla(ActionEvent evento) { //Aqui se da compra-venta-devolucion
		
	}
	
	private void cambiarSceneMain(ActionEvent evento) throws IOException {
		
		Parent mainParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene mainScene = new Scene(mainParent);
		
		Stage window = (Stage) menuBar.getScene().getWindow();
		
		window.setScene(mainScene);
		window.show();
		
	}
	
	public void cerrarArchivo(ActionEvent evento) {
		
		Main.getModel().cerrarArchivo();
		try {
			cambiarSceneMain(evento);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void exit() {
		System.exit(0);
	}
	
}
