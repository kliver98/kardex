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
import javafx.stage.Stage;

public class MainController implements Initializable {
	
	@FXML
	private ComboBox<String> cbKardex;
	
	public MainController() {
		cbKardex = new ComboBox<String>();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbKardex.getItems().removeAll(cbKardex.getItems());
		cbKardex.getItems().addAll(Main.getModel().nombresKardexCreados());
		cbKardex.getSelectionModel().select("Seleccione...");
	}
	
	public void cambiarSceneCrearNuevo(ActionEvent evento) throws IOException {
		
		Parent crearNuevoParent = FXMLLoader.load(getClass().getResource("CrearNuevo.fxml"));
		Scene crearNuevoScene = new Scene(crearNuevoParent);
		
		Stage window = (Stage)((Node)evento.getSource()).getScene().getWindow();
		
		window.setScene(crearNuevoScene);
		window.show();
		
	}
	
}