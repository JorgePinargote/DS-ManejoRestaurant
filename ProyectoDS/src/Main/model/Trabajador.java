/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Israel
 */
public abstract class Trabajador extends Persona {
    private StringProperty rol;
    private BooleanProperty eliminado;
    private String userName;
    private String contrasenia;

    public Trabajador(String rol, boolean eliminado,String nombre, String apellido, String direccion, String cedula, String telefono, String username) {
        super(0,nombre, apellido, direccion, cedula, telefono);
        this.rol = new SimpleStringProperty(rol);
        this.eliminado = new SimpleBooleanProperty(eliminado);
        this.userName = username;
    }
    
    public Trabajador(){}


    public boolean isEliminado() {
        return eliminado.get();
    }
    
    public BooleanProperty isEliminadProperty(){
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado.set(eliminado);
    }

    public StringProperty getRolProperty() {
        return rol;
    }
    
    public String getRol() {
        return rol.get();
    }

    public void setRol(StringProperty rol) {
        this.rol = rol;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    
    
    
    
}
