/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import Main.Conexion;
import Main.ProyectoDS;
import Main.vistaControl.Cajero.Help.electronicoDataController;
import Main.vistaControl.Mesero.NuevoItemController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class pagoElectronico extends StrategyPago {
    StringProperty no_serial;

    public pagoElectronico(String no_serial,double cantidad) {
        this.no_serial = new SimpleStringProperty(no_serial);
        super.setCantidad(cantidad);
    }
    
    public pagoElectronico() {
         this.no_serial = new SimpleStringProperty("");
    }
    
    @Override
    public void Cobrar(Persona persona, int detallecuenta) {
        boolean isok = RecogerData();
        if(isok){
            Conexion conexion = Conexion.getInstance();
            Connection conectar = conexion.getConexion();
            try{
                    PreparedStatement pst = 
                        conectar.prepareStatement("insert into pago_electronico (id_detallecuenta,id_cliente,no_serial) VALUES(?,?,?)");
                        pst.setInt(1,detallecuenta);
                        pst.setInt(2,persona.getIdPersona());
                        pst.setString(3,this.no_serial.get());
                        
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
        return new SimpleStringProperty("Electronico");
    }

   public void setNo_serial(String no_serial) {
        this.no_serial.set(no_serial);
   }
   
   public boolean RecogerData(){
        Stage stage = new Stage();
        AnchorPane root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ProyectoDS.class.getResource("vistaControl/Cajero/Help/electronicoData.fxml"));
        
        try {
             root = (AnchorPane) loader.load();
             Scene scene = new Scene(root);
             
             electronicoDataController Controller = loader.getController();
             Controller.setPago(this);
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