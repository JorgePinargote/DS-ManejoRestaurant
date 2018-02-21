/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

/**
 *
 * @author Jorge Pinargote
 */
public class ConcretTrabajador implements FactoryTrabajador {
    @Override
    public Trabajador CreateTrabajador(String rol,boolean eliminado,String nombre, String apellido, String direccion, String cedula, String telefono, String username) {
        if(rol.equalsIgnoreCase("Mesero")){
            return new Mesero(rol,eliminado,nombre,apellido,direccion,cedula,telefono,username);
        }
        if(rol.equalsIgnoreCase("Cocinero")){
            return new Cocinero(rol,eliminado,nombre,apellido,direccion,cedula,telefono,username);
        }
        if(rol.equalsIgnoreCase("Cajero")){
           return new Cajero(rol,eliminado,nombre,apellido,direccion,cedula,telefono,username);
        }
        if(rol.equalsIgnoreCase("Administrador")){
            return new Administrador(rol,eliminado,nombre,apellido,direccion,cedula,telefono,username);  
        }
        return null;
    }
    
}
