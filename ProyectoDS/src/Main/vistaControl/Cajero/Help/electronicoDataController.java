/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Cajero.Help;

import Main.model.pagoElectronico;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Pinargote
 */
public class electronicoDataController implements Initializable{
    pagoElectronico pago;
    public boolean isOkClicked = false;
    Stage myStage;
    
    @FXML
    private TextField txtserial;
    
    @FXML
    private Button btnok;
    
    @FXML
    private Button btncancelar;
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void setPago(pagoElectronico pago) {
        this.pago = pago;
    }
    
    public void handleOk(){
        if (txtserial.getText() != null || txtserial.getText().length() != 0) {
            this.pago.setNo_serial(txtserial.getText());
            this.isOkClicked = true;
            this.myStage.close();
        }
    }
    
    public void handleCancel(){
        this.myStage.close();
    }

    public boolean isIsOkClicked() {
        return isOkClicked;
    }

    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    
}
