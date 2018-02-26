/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import Main.Conexion;
import Main.Login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jorge Pinargote
 */
public class detalleCuenta {
    IntegerProperty iddetalle;
    DoubleProperty subtotal;
    DoubleProperty descuento;
    DoubleProperty total;
    Persona persona;
    StrategyPago pago;
    
    public Persona getPersona(){
        return persona;
    }
    
    public DoubleProperty getDescProperty(){
        return descuento;
    }
    
    public DoubleProperty getTotalProperty(){
        return total;
    }
    
    public DoubleProperty getSubTotalProperty(){
        return subtotal;
    }

    public detalleCuenta(int iddetalle,double subtotal,double descuento, double total) {
        this.iddetalle = new SimpleIntegerProperty(iddetalle);
        this.subtotal = new SimpleDoubleProperty(subtotal);
        this.descuento = new SimpleDoubleProperty(descuento);
        this.total = new SimpleDoubleProperty(total);
    }
    
    public detalleCuenta(Persona persona,double subtotal,double descuento, double total) {
        this.iddetalle = new SimpleIntegerProperty(0);
        this.subtotal = new SimpleDoubleProperty(subtotal);
        this.descuento = new SimpleDoubleProperty(descuento);
        this.total = new SimpleDoubleProperty(total);
        this.persona = persona;
    }
    
    public void cargarMyPago(){
        cargarElectronico();
        cargarEfectivo();
        cargarTarjeta();
    }
    
    public void cargarElectronico(){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from pago_electronico where id_detallecuenta = " + this.iddetalle.get());
                ResultSetMetaData rsMd = rst.getMetaData();
                
                if(rst.next()){
                    int idcliente = (int)rst.getObject(2);
                    String serial = (String)rst.getObject(3);
                    persona = Persona.getPersonById(idcliente);
                    pago = new pagoElectronico(serial,this.total.get());   
                }
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    }
    
    public void cargarEfectivo(){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from pago_efectivo where id_detallecuenta = " + this.iddetalle.get());
                ResultSetMetaData rsMd = rst.getMetaData();
                
                if(rst.next()){
                    int idcliente = (int)rst.getObject(2);
                    persona = Persona.getPersonById(idcliente);
                    pago = new PagoEfectivo(this.total.get());   
                }
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    
    
    }
    
    public void cargarTarjeta(){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from pago_tarjeta where id_detallecuenta = " + this.iddetalle.get());
                ResultSetMetaData rsMd = rst.getMetaData();
                
                if(rst.next()){
                    int idtarjeta = (int)rst.getObject(2);
                    persona = Persona.getPersonByCreditCardId(idtarjeta);
                    if(persona!=null)persona.getMyTarjetas();
                    Tarjeta tarjeta = persona.getTarjetaById(idtarjeta);
                    PagoTarjetas pago = new PagoTarjetas(tarjeta);
                    pago.setCantidad(this.total.get());
                    this.pago = pago;
                }
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    
    }
    
    public void Cobrar(StrategyPago tipopago){
        tipopago.Cobrar(persona,this.iddetalle.get());
        pago = tipopago;
    }
    

    public double getSubtotal() {
        return subtotal.get();
    }

    public double getDescuento() {
        return descuento.get();
    }

    public double getTotal() {
        return total.get();
    }


    public void setIddetalle(int iddetalle) {
        this.iddetalle.set(iddetalle);
    }

    public StrategyPago getPago() {
        return pago;
    }
      
    
}
