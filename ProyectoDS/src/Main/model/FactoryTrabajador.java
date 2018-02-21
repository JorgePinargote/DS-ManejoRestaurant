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
public interface FactoryTrabajador {
    public Trabajador CreateTrabajador(String rol, boolean eliminado,String nombre, String apellido, String direccion, String cedula, String telefono, String username);
}
