/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Cajero;

import Main.model.Persona;
import Main.model.Tarjeta;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Pinargote
 */
public class MetVisaController implements Initializable{
    Persona persona;
    Stage stage;
    
    @FXML 
    private TextField txtnombre;
    @FXML 
    private TextField txtcardnumber;
    @FXML 
    private TextField txtcvv;
    @FXML 
    private DatePicker dpdateexpire;
    @FXML
    private Button btnok;
    @FXML
    private Button btncancelar;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //To change body of generated methods, choose Tools | Templates.
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    public void handleok(){
        if(isInputValid()){
            Date date = Date.from(dpdateexpire.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            Tarjeta tarjeta = new Tarjeta(txtnombre.getText(),txtcardnumber.getText(),txtcvv.getText(),date);
            persona.addTarjeta(tarjeta);
            this.stage.close();
        }
    
    
    }
    
    public boolean isInputValid(){
        if (txtnombre.getText() == null || txtnombre.getText().length() == 0) {
            return false;
        }
        if (txtcardnumber.getText() == null || txtcardnumber.getText().length() == 0) {
            return false;
        }
        if (txtcvv.getText() == null || txtcvv.getText().length() == 0) {
            return false;
        }
        if(dpdateexpire.getValue()==null){
            return false;
        }
        if(persona == null){
            return false;
        }
        
        return true;
    
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    public void handleCancelar(){
        this.stage.close();
    }
    
}
