/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import Main.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jorge Pinargote
 */
public class PagoEfectivo extends StrategyPago {

    public PagoEfectivo(double cantidad) {
        super.setCantidad(cantidad);
    }
 
    public PagoEfectivo() {
        
    }
    
    
    @Override
    public void Cobrar(Persona persona,int idcuenta) {
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                    PreparedStatement pst = 
                        conectar.prepareStatement("insert into pago_efectivo (id_detallecuenta,id_cliente) VALUES(?,?)");
                        pst.setInt(1,idcuenta);
                        pst.setInt(2,persona.getIdPersona());
                        
                        
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

    @Override
    public StringProperty getDescripcion() {
        return new SimpleStringProperty("Efectivo");
    }
    
}
