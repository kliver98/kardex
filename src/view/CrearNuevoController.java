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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	private TextArea taComentario;
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
				taComentario.getText()};
		boolean seCreoArchivo = Main.getModel().crearRegistroKardex(datos);
		try {			
			if (seCreoArchivo)
				cambiarSceneTablaKardex(evento);
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
