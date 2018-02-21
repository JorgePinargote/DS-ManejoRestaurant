/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import Main.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Israel
 */
public class Mesa {
    
    private static ObservableList<Mesa> mesaData = FXCollections.observableArrayList();
    
    private BooleanProperty disponible;
    private StringProperty  numeroDeMesa;
    private StringProperty  numeroDeSillas;
    private StringProperty  ambiente;
    
    private ObservableList<Cuenta> Cuentas;

    public Mesa(StringProperty  numeroDeMesas, StringProperty  numeroDeSillas,StringProperty ambiente) {
        this.numeroDeMesa = numeroDeMesas;
        this.numeroDeSillas = numeroDeSillas;
        this.ambiente = ambiente;
        this.Cuentas = FXCollections.observableArrayList();
    }
    
    
    public Mesa(String  numeroDeMesas, String numeroDeSillas,String ambiente, boolean disponible) {
        this.disponible= new SimpleBooleanProperty(disponible);
        this.numeroDeMesa = new SimpleStringProperty(numeroDeMesas);
        this.numeroDeSillas = new SimpleStringProperty(numeroDeSillas);
        this.ambiente = new SimpleStringProperty(ambiente);
        this.Cuentas = FXCollections.observableArrayList();
    }
    
    public boolean isAvailable(){
    
        return true;
    }

    public String getNumeroDeMesa() {
        return numeroDeMesa.get();
    }

    public void setNumeroDeMesas(StringProperty  numeroDeMesas) {
        this.numeroDeMesa = numeroDeMesas;
    }

    public String getNumeroDeSillas() {
        return numeroDeSillas.get();
    }

    public void setNumeroDeSillas(StringProperty  numeroDeSillas) {
        this.numeroDeSillas = numeroDeSillas;
    }

    public String getAmbiente() {
        return ambiente.get();
    }

    public void setAmbiente(StringProperty ambiente) {
        this.ambiente = ambiente;
    }
    
    public void agregarSilla(){
    
        this.numeroDeSillas = new SimpleStringProperty(String.valueOf(Integer.parseInt(this.numeroDeSillas.get())+1));
    
    }
    
    public void quitarSilla(){
        if(Integer.parseInt(this.numeroDeSillas.get())>0){
            this.numeroDeSillas = new SimpleStringProperty(String.valueOf(Integer.parseInt(this.numeroDeSillas.get())-1));
        }
    }

    
    public static ObservableList<Mesa> getMesaData() {
        return mesaData;
    }
    
    public static void cargarMesaAgain(){
        mesaData.clear();
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
        
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from mesa");
                ResultSetMetaData rsMd = rst.getMetaData();
                
                while(rst.next()){
                    
                    String numeroMesa = Integer.toString((int)rst.getObject(1));
                    String numeroSilla = Integer.toString((int)rst.getObject(2));
                    String ambiente = (String)rst.getObject(3);
                    boolean mdisponible = (boolean)rst.getObject(4);
                    
                    Mesa temp = new Mesa(numeroMesa,numeroSilla,ambiente,mdisponible);
                    mesaData.add(temp);
                    
                }
                

            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
                
            }
    }
   
    public void getMyCuentas(){
        this.Cuentas.clear();
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from cuenta where id_mesa = " + this.numeroDeMesa.get() + " and saldo > 0" );
                ResultSetMetaData rsMd = rst.getMetaData();
                
                while(rst.next()){
                    int idcuenta = (int)rst.getObject(1);
                    double saldo = (double)rst.getObject(2);
                    Date fecha = rst.getDate("fecha");
                    double valortotal = (double)rst.getObject(5);
                    this.Cuentas.add(new Cuenta(idcuenta,saldo,valortotal,fecha));   
                }
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    }
    
    
    public int createNewCuenta(){
        int clave = 0;
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
         try{
                    PreparedStatement pst = 
                        conectar.prepareStatement("insert into cuenta(fecha,id_mesa) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
                        java.sql.Date fecha = new java.sql.Date(System.currentTimeMillis());
                        pst.setDate(1,fecha);
                        pst.setInt(2,Integer.parseInt(this.numeroDeMesa.get()));
                        int res = pst.executeUpdate();
                        
                        if(res>0){
                            System.out.println("detalle Enviado");
                        }else{
                            System.out.println("Error al Enviar detalle");
                        }
                        
                        
                        ResultSet rs = pst.getGeneratedKeys(); 
                        
                        
                        if(rs.next()){ 
                            clave = rs.getInt(1);
                        }else{
                            System.out.println("error obteniendo id");
                            return -1;
                        }
                        
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
         
         return clave;
         
    
    } 

    public ObservableList<Cuenta> getCuentas() {
        return Cuentas;
    }
    
    public static void reinitCuentas(Mesa mesa,ObservableList<Cuenta> cuentas){
        cuentas.clear();
        cuentas.add(new Cuenta());
        mesa.getMyCuentas();
        cuentas.addAll(mesa.getCuentas());
    }
    
    public static void changeCuentas(Mesa mesa,ObservableList<Cuenta> cuentas){
        
        cuentas.clear();
        mesa.getMyCuentas();
        cuentas.addAll(mesa.getCuentas());
    }
    
    public Cuenta getCuentaByid(int id){
        for(Cuenta cuenta : this.Cuentas){
            if(cuenta.getIdcuenta()== id)return cuenta;
        }
        return null;
    }
    
    
}
