/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.login;

import Main.Interfaces.ConcretGrafica;
import Main.Interfaces.FactoryGrafica;
import Main.Interfaces.InterGraficas;
import Main.Login;
import Main.model.Trabajador;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Israel
 */
public class FXMLDocumentController implements Initializable {
    
    
    @FXML
    private TextField campoNombre;
    
    @FXML
    private Button LogIn;
    
    @FXML
    private TextField campoContrasenia;
    
    
    @FXML
    private void loggear(ActionEvent event){
        
        Login login;
        
        if(campoNombre.getText()!= null && campoContrasenia.getText()!= null && !campoNombre.getText().equals("") && !campoContrasenia.getText().equals("")){
            
            login = Login.getInstance(campoNombre.getText(),campoContrasenia.getText());
            if(login!=null){
                FactoryGrafica interfaz = new ConcretGrafica();
                InterGraficas concretInterfaz = interfaz.ShowInterface(login.getTrabajador());
                concretInterfaz.crear();
                ((Node) event.getSource()).getScene().getWindow().hide();
            }else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Login");
                alert.setHeaderText("Falla de auntenticaciòn");
                alert.setContentText("Por favor, Ingrese credenciales vàlidas");

                alert.showAndWait();
                campoNombre.setText("");
                campoContrasenia.setText("");
            }
            
            
            
            
        }
        
    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
