/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import Main.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
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
public class Persona {
    
    private static ObservableList<Persona> personData = FXCollections.observableArrayList();
    
    protected IntegerProperty idpersona;
    protected StringProperty nombre;
    protected StringProperty apellido;
    protected StringProperty direccion;
    protected StringProperty cedula;
    protected StringProperty telefono;

    public Persona(int id,String nombre, String apellido, String direccion, String cedula, String telefono) {
        this.idpersona=new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.direccion =  new SimpleStringProperty(direccion);
        this.cedula =  new SimpleStringProperty(cedula);
        this.telefono =  new SimpleStringProperty(telefono);
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
    
    
    
    
    
    
}
