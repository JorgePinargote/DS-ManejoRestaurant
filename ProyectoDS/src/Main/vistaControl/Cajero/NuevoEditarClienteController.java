/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Cajero;

import Main.model.Persona;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Pinargote
 */
public class NuevoEditarClienteController implements Initializable{
    
    Stage myStage;
    Persona persona; 
    
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
    private Button btnok;
    @FXML
    private Button btncancelar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setPerson(Persona persona){
        if(persona != null){
            this.persona = persona;
            
            this.txtnombre.setText(persona.getNombre());
            this.txtapellido.setText(persona.getApellido());
            this.txtcedula.setText(persona.getCedula());
            this.txtdireccion.setText(persona.getDireccion());
            this.txttelefono.setText(persona.getTelefono());
        }
        
    }
    
    public void handleCancelar(){
        this.myStage.close();
    }

    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }
    
    public void handleOK(){
        if(validInput()){
            Editar();
            Agregar();
            this.myStage.close();
        }
    
    }
    
    public boolean validInput(){
        if (txtnombre.getText() == null || txtnombre.getText().length() == 0) {
            return false;
        }
        if (txtapellido.getText() == null || txtapellido.getText().length() == 0) {
            return false;
        }
        if (txtcedula.getText() == null || txtcedula.getText().length() == 0) {
            return false;
        }
        if (txttelefono.getText() == null || txttelefono.getText().length() == 0) {
            return false;
        }
        if (txtdireccion.getText() == null || txtdireccion.getText().length() == 0) {
            return false;
        }
        return true;
    
    }
    
    
    public void Editar(){
        if(persona != null){
            
            persona.setNombre( txtnombre.getText());
            persona.setApellido(txtapellido.getText());
            persona.setCedula(txtcedula.getText());
            persona.setDireccion(txtdireccion.getText());
            persona.setTelefono(txttelefono.getText());
            
            persona.updateInfo();
        }
        
    }
    
    public void Agregar(){
        if(persona == null){
            String nombre = txtnombre.getText();
            String apellido = txtapellido.getText();
            String cedula = txtcedula.getText();
            System.out.println(cedula);
            String direccion = txtdireccion.getText();
            String telefono = txttelefono.getText();
            persona = new Persona(nombre,apellido,cedula,direccion,telefono);
            Persona.Agregar(persona);
        }
    }
    
    
}
