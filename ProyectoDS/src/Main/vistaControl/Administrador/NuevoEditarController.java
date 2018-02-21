/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Administrador;

import Main.ProyectoDS;
import Main.model.Trabajador;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Pinargote
 */
public class NuevoEditarController implements Initializable {
    
     public static Trabajador temp ;
     private String nombre;
     private String apellido;
     private String cedula; 
     private String direccion;
     private String telefono;
     private String rol;
     
    @FXML
    private TextField txtnombre;
    @FXML
    private TextField txtapellido;
    @FXML
    private TextField txtcedula;
    @FXML
    private TextField txtdireccion;
    @FXML
    private TextField txttelefono;
    @FXML
    private ChoiceBox chrol;
    @FXML
    private Button btnok;
    @FXML
    private Button btncancelar;
    
    @FXML
    public void okClicked(ActionEvent event) throws IOException{
    
        Parent parent = FXMLLoader.load(getClass().getResource("PrincipalA.fxml"));
       ((Node) event.getSource()).getScene().getWindow().hide();
       Scene scene = new Scene(parent);
       ProyectoDS.window.setScene(scene);
       ProyectoDS.window.show();
    
    }
    
    public void  cargarCampo(Trabajador temp){
    
        if(temp != null ){
        
            this.txtnombre.setText("efren");
            this.txtapellido.setText(temp.getApellido());
            this.txttelefono.setText(temp.getTelefono());
            this.txtdireccion.setText(temp.getDireccion());
            this.txtcedula.setText(temp.getCedula());
            //this.roltemp.getRol();
        }
    
        
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //To change body of generated methods, choose Tools | Templates.
        this.cargarCampo(temp);
        
    }
    
}
