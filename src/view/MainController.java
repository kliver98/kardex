package view;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainController implements Initializable {
	
	@FXML
	private ComboBox<String> cbKardex;
	
	public MainController() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbKardex.getItems().removeAll(cbKardex.getItems());
		String[] creados = Main.getModel().nombresKardexCreados();
		if (creados!=null)
			cbKardex.getItems().addAll(creados);
	}
	
	public void cambiarSceneTablaKardex(ActionEvent evento) {
		try {
			Main.getModel().leerArchivo(cbKardex.getSelectionModel().getSelectedItem());
			cambiarScene(evento,"TablaKardex.fxml");
		} catch(Exception e) {
			mensaje("Error","Ocurrio un problema al leer el archivo. Intente seleccionar otro archivo para cargar o cree uno nuevo.",AlertType.ERROR);
		}
	}
	
	public void cambiarSceneCrearNuevo(ActionEvent evento) {
		try {
			cambiarScene(evento,"CrearNuevo.fxml");
		} catch (Exception e) {
			mensaje("Error","No se pudo cargar la ventana :( Intente de nuevo",AlertType.ERROR);
		}
	}
	
	private void cambiarScene(ActionEvent evento,String nombre) throws Exception{
		Parent crearNuevoParent = FXMLLoader.load(getClass().getResource(nombre));
		Scene crearNuevoScene = new Scene(crearNuevoParent);
		
		Stage window = (Stage)((Node)evento.getSource()).getScene().getWindow();
		
		window.setScene(crearNuevoScene);
		window.show();
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
	
}