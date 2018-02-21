/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Cajero;

import Main.Login;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Jorge Pinargote
 */
public class PrincipalCController implements Initializable {
    
    @FXML
    private Label lblnombre;
    
    //TABCOMENSALES
    @FXML
    private TableView tbclientes;
    @FXML
    private Button btnnuevo;
    @FXML
    private Button btneditar;
    
    //TABCOBRAR
    @FXML
    private ChoiceBox chmnesa;
    @FXML
    private TableView tbcuentas;
    @FXML
    private TextField txtdescuento;
    @FXML 
    private ChoiceBox chmetodo;
    @FXML
    private Label lbliva;
    @FXML
    private TextField txtcantidad;
    @FXML
    private Label lbltotal;
    @FXML 
    private Button btncobrar;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Login login = Login.getInstance();
        lblnombre.setText(login.getTrabajador().getNombre() +" "+ login.getTrabajador().getApellido());
    }
    
}
