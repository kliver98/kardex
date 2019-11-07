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
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class MainController implements Initializable {
	
	@FXML
	private ComboBox<String> cbKardex;
	
	public MainController() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbKardex.getItems().removeAll(cbKardex.getItems());
		cbKardex.getItems().addAll(Main.getModel().nombresKardexCreados());
	}
	
	public void cambiarSceneTablaKardex(ActionEvent evento) throws Exception {
		Main.getModel().leerArchivo(cbKardex.getSelectionModel().getSelectedItem());
		cambiarScene(evento,"TablaKardex.fxml");
	}
	
	public void cambiarSceneCrearNuevo(ActionEvent evento) throws Exception {
		cambiarScene(evento,"CrearNuevo.fxml");
	}
	
	private void cambiarScene(ActionEvent evento,String nombre) throws Exception{
		Parent crearNuevoParent = FXMLLoader.load(getClass().getResource(nombre));
		Scene crearNuevoScene = new Scene(crearNuevoParent);
		
		Stage window = (Stage)((Node)evento.getSource()).getScene().getWindow();
		
		window.setScene(crearNuevoScene);
		window.show();
	}
	
}