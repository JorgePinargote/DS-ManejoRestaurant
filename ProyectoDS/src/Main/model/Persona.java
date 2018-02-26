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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Israel
 */
public class Persona{
    
    private static ObservableList<Persona> personData = FXCollections.observableArrayList();
    
    protected IntegerProperty idpersona;
    protected StringProperty nombre;
    protected StringProperty apellido;
    protected StringProperty direccion;
    protected StringProperty cedula;
    protected StringProperty telefono;
    private ObservableList<Tarjeta> tarjetas;
     

    public Persona(int id,String nombre, String apellido, String direccion, String cedula, String telefono) {
        this.idpersona=new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.direccion =  new SimpleStringProperty(direccion);
        this.cedula =  new SimpleStringProperty(cedula);
        this.telefono =  new SimpleStringProperty(telefono);
        this.tarjetas  = FXCollections.observableArrayList(); 
    }
    
    public Persona(String nombre, String apellido, String direccion, String cedula, String telefono) {
        this.idpersona=new SimpleIntegerProperty(0);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.direccion =  new SimpleStringProperty(direccion);
        this.cedula =  new SimpleStringProperty(cedula);
        this.telefono =  new SimpleStringProperty(telefono);
        this.tarjetas =  FXCollections.observableArrayList(); 
    }
    
    public Persona(){}

    public StringProperty getNombreProperty() {
        return nombre;
    }
    
    public String getNombre() {
        return nombre.get();
    }
    

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty getApellidoProperty() {
        return apellido;
    }
     
    public String getApellido() {
        return apellido.get();
    }
    

    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }

    public StringProperty getDireccionProperty() {
        return direccion;
    }
    
    public String getDireccion() {
        return direccion.get();
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public StringProperty getCedulaProperty() {
        return cedula;
    }
    
    public String getCedula() {
        return cedula.get();
    }

    public void setCedula(String cedula) {
        this.cedula.set(cedula);
    }

    public StringProperty getTelefonoProperty() {
        return telefono;
    }
    
    public String getTelefono() {
        return telefono.get();
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }
    
    public IntegerProperty getidProperty() {
        return idpersona;
    }
    
     public int getIdPersona() {
        return idpersona.get();
    }

    public void setId(int idpersona) {
        this.idpersona.set(idpersona);
    }
    
    
    
    public static ObservableList<Persona> getPersonData() {
        return personData;
    }

    @Override
    public String toString() {
        return "Persona{" + "nombre=" + nombre.get() + ", apellido=" + apellido.get() + ", direccion=" + direccion.get() + ", cedula=" + cedula.get() + ", telefono=" + telefono.get() + '}';
    }
    
    
    
    public static void CargarPersonAgain(){
        personData.clear();
        
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
        
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from cliente");
                ResultSetMetaData rsMd = rst.getMetaData();
                
                while(rst.next()){
                    int id = (int)rst.getObject(1);
                    String nombre = (String)rst.getObject(2);
                    String apellido = (String)rst.getObject(3);
                    String cedula = (String)rst.getObject(4);
                    String direccion = (String)rst.getObject(5);
                    String telefono = (String)rst.getObject(6);
                    
                    Persona temp = new Persona(id,nombre,apellido,direccion,cedula,telefono);
                    personData.add(temp);
                    
                }
                

            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    }
    
    
    
    public void updateInfo(){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                    PreparedStatement pst = 
                        conectar.prepareStatement("Update cliente set nombre = ? , apellido = ? , cedulA = ? , direccion = ? , telefono = ? "
                                + "where id_cliente = ?");
                    
                        pst.setString(1,this.getNombre());
                        pst.setString(2,this.getApellido());
                        pst.setString(3, this.getCedula());
                        pst.setString(4, this.getDireccion());
                        pst.setString(5,this.getTelefono());
                        
                        pst.setInt(6,this.getIdPersona());
                        
                        int res = pst.executeUpdate();
                        
                        if(res>0){
                            System.out.println("persona actualizado");
                        }else{
                            System.out.println("Error al actualizar");
                        }
                
                        
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    }
    
    public static void Agregar(Persona persona){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                
                    PreparedStatement pst = 
                    conectar.prepareStatement("insert into cliente(nombre,apellido,cedulA,direccion,telefono) VALUES(?,?,?,?,?)");
                        pst.setString(1,persona.getNombre());
                        pst.setString(2,persona.getApellido());
                        pst.setString(3,persona.getCedula());
                        pst.setString(4,persona.getDireccion());
                        pst.setString(5,persona.getTelefono());
                        
                        int res = pst.executeUpdate();
                        
                        if(res>0){
                            System.out.println("Persona Agregada");
                        }else{
                            System.out.println("Error al agregar persona");
                        }
                
                        
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    
            Persona.CargarPersonAgain();
    }
    
    
    public void getMyTarjetas(){
        this.tarjetas.clear();
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
        
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from tarjeta_credito where id_cliente = " + this.idpersona.get());
                ResultSetMetaData rsMd = rst.getMetaData();
                
                while(rst.next()){
                    int idtarjeta = (int)rst.getObject(1);
                    String nombre = (String)rst.getObject(2);
                    String numero = (String)rst.getObject(3);
                    String cvv = (String)rst.getObject(4);
                    Date exp = rst.getDate("fechaExp");
                    this.tarjetas.add(new Tarjeta(idtarjeta,nombre,numero,cvv,exp));
                    
                }
                

            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    
    }
    
    public static Persona getPersonById(int id){
        for(Persona persona : personData){
            if(persona.getIdPersona()== id )return persona;
        }
        return null;
    }
    
    public static Persona getPersonByCreditCardId(int id){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
        
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from tarjeta_credito where idtarjeta = " + id);
                ResultSetMetaData rsMd = rst.getMetaData();
                
                if(rst.next()){
                    int idcliente = (int)rst.getObject(6);
                    return getPersonById(idcliente);                     
                }
                

            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
            
            return null;
    }
    
    
    public Tarjeta getTarjetaById(int id){
        this.getMyTarjetas();
        for(Tarjeta tarjeta : tarjetas){
            if(tarjeta.getIdtarjeta() == id )return tarjeta;
        }
        return null;
    }
    
    public void addTarjeta(Tarjeta tarjeta){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                    PreparedStatement pst = 
                        conectar.prepareStatement("insert into tarjeta_credito(nombre,numero,cvv,fechaExp,id_cliente) VALUES(?,?,?,?,?)");
                        pst.setString(1,tarjeta.getNombre());
                        pst.setString(2, tarjeta.getNumero());
                        pst.setString(3,tarjeta.getCvv());
                        java.sql.Date date = new java.sql.Date(tarjeta.getExpira().getTime());
                        pst.setDate(4,date);
                        pst.setInt(5,this.getIdPersona());
                        
                        int res = pst.executeUpdate();
                        
                        if(res>0){
                            System.out.println("detalle Enviado");
                        }else{
                            System.out.println("Error al Enviar detalle");
                        }         
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    
    }

    public ObservableList<Tarjeta> getTarjetas() {
        return tarjetas;
    }
    
    
    
    
    
}
