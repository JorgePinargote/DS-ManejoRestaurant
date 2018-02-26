/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import Main.Conexion;
import Main.ProyectoDS;
import Main.vistaControl.Cajero.Help.electronicoDataController;
import Main.vistaControl.Cajero.Help.tarjetaDataController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Pinargote
 */
public class PagoTarjetas extends StrategyPago {
    Tarjeta tarjeta;

    public PagoTarjetas(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }
    
    public PagoTarjetas(){
    }
    
    @Override
    public void Cobrar(Persona persona, int iddetallecuenta){
        boolean isok = RecogerData(persona);
        if(isok){
            Conexion conexion = Conexion.getInstance();
            Connection conectar = conexion.getConexion();
            try{
                    PreparedStatement pst = 
                        conectar.prepareStatement("insert into pago_tarjeta (id_detallecuenta,id_tarjeta) VALUES(?,?)");
                        pst.setInt(1,iddetallecuenta);
                        pst.setInt(2,this.tarjeta.getIdtarjeta());
                        
                        
                        int res = pst.executeUpdate();
                        
                        if(res>0){
                            System.out.println("cobrado");
                        }else{
                            System.out.println("Error al cobrar");
                        }         
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
        }
        
    }
    
    
    @Override
    public StringProperty getDescripcion() {
        return new SimpleStringProperty("Tarjeta de credito");
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }
    
    public boolean RecogerData(Persona persona){
        Stage stage = new Stage();
        AnchorPane root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ProyectoDS.class.getResource("vistaControl/Cajero/Help/tarjetaData.fxml"));
        
        try {
             root = (AnchorPane) loader.load();
             Scene scene = new Scene(root);
             
             tarjetaDataController Controller = loader.getController();
             Controller.setPago(this);
             Controller.setTarjetasOfPersona(persona);
             Controller.setMyStage(stage);
             
             stage.setScene(scene);
             stage.showAndWait();
             
             return Controller.isOkClicked;
             
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return false;
   }
    
    
    
    
    
    
    
}
