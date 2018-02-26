/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Cajero.Help;

import Main.Help.TarjetaConverter;
import Main.model.Mesa;
import Main.model.PagoTarjetas;
import Main.model.Persona;
import Main.model.Tarjeta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Pinargote
 */
public class tarjetaDataController implements Initializable {

    PagoTarjetas pago;
    public boolean isOkClicked = false;
    Stage myStage;
    
    @FXML
    private ChoiceBox<Tarjeta> chtarjeta;
    
    @FXML
    private Button btnok;
    
    @FXML
    private Button btncancelar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chtarjeta.setConverter(new TarjetaConverter());
        
    }
    
     public void handleOk(){
        int index = chtarjeta.getSelectionModel().getSelectedIndex();
        if (index>=0) {
            this.pago.setTarjeta(chtarjeta.getSelectionModel().getSelectedItem());
            this.isOkClicked = true;
            this.myStage.close();
        }
    }
    
    public boolean isIsOkClicked() {
        return isOkClicked;
    }

    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    public void setPago(PagoTarjetas pago) {
        this.pago = pago;
    }
    
    public void setTarjetasOfPersona(Persona persona){
        persona.getMyTarjetas();
        chtarjeta.setItems(persona.getTarjetas());
    }
    
    public void handleCancelar(){
        this.myStage.close();
    }

}
