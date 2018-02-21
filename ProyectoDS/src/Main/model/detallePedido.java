/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import Main.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author Jorge Pinargote
 */
public class detallePedido {
    IntegerProperty iddetalle;
    MenuElemento item;
    StringProperty observaciones;
    IntegerProperty cantidad;
    DoubleProperty precio;

    public detallePedido(int iddetalle,MenuElemento item, String observaciones, int cantidad) {
        this.iddetalle= new SimpleIntegerProperty(iddetalle);
        this.item = item;
        this.observaciones =new SimpleStringProperty(observaciones);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.precio = new SimpleDoubleProperty(item.getPrecio()*cantidad);
    }
    
    public detallePedido(MenuElemento item, String observaciones, int cantidad) {
        this.iddetalle= new SimpleIntegerProperty(0);
        this.item = item;
        this.observaciones =new SimpleStringProperty(observaciones);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.precio = new SimpleDoubleProperty(item.getPrecio()*cantidad);
    }

    public MenuElemento getItem() {
        return item;
    }

    public StringProperty getObservacionesProperty() {
        return observaciones;
    }
    
    public String getObservaciones() {
        return observaciones.get();
    }


    public int getCantidad() {
        return cantidad.get();
    }
    
     public IntegerProperty getCantidadProperty() {
        return cantidad;
    }

    public double getPrecio() {
        return precio.get();
    }

    public int getIddetalle() {
        return iddetalle.get();
    }
     
    public static void deleteDetalles(ObservableList<detallePedido> itemsPedidos){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                for(detallePedido detalle : itemsPedidos){
                    PreparedStatement pst = 
                        conectar.prepareStatement("Delete from detallepedido Where id_detalle = ?");
                        pst.setInt(1,detalle.getIddetalle());
                        
                        int res = pst.executeUpdate();
                        
                        if(res>0){
                            System.out.println("detalle eliminado");
                        }else{
                            System.out.println("Error al eliminar detalle");
                        }
                }
                        
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    }

}
